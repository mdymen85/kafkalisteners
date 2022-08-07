provider "aws" {
  region = "us-east-1"
}

resource "aws_vpc" "vpc_prd" {
  cidr_block = "10.0.0.0/16"
  enable_dns_hostnames = true
}

resource "aws_security_group" "terraform_segurity_group" {
     vpc_id = aws_vpc.vpc_prd.id
     ingress {
       description      = "SSH"
       from_port        = 22
       to_port          = 22
       protocol         = "tcp"
       cidr_blocks      = ["0.0.0.0/0"]
     }   
     ingress {
       description      = "8081"
       from_port        = 8081
       to_port          = 8081
       protocol         = "tcp"
       cidr_blocks      = ["0.0.0.0/0"]
     }  
     ingress {
       description      = "8080"
       from_port        = 8080
       to_port          = 8080
       protocol         = "tcp"
       cidr_blocks      = ["0.0.0.0/0"]
     }  
     ingress {
       description      = "mysql"
       from_port        = 3306
       to_port          = 3306
       protocol         = "tcp"
       cidr_blocks      = ["0.0.0.0/0"]
     }             
     ingress {
       description      = "redis"
       from_port        = 6379
       to_port          = 6379
       protocol         = "tcp"
       cidr_blocks      = ["0.0.0.0/0"]
     }       
     egress {
       from_port        = 0
       to_port          = 0
       protocol         = "-1"
       cidr_blocks      = ["0.0.0.0/0"]
       ipv6_cidr_blocks = ["::/0"]
     }                   
     tags = {
        Name = "security_group_tf"
     }
}

resource "aws_subnet" "public_subnet_a" {
  vpc_id = aws_vpc.vpc_prd.id
  cidr_block = "10.0.0.0/24"
  availability_zone = "us-east-1a"
  map_public_ip_on_launch = true
}

resource "aws_subnet" "public_subnet_b" {
  vpc_id = aws_vpc.vpc_prd.id
  cidr_block = "10.0.1.0/24"
  availability_zone = "us-east-1b"
  map_public_ip_on_launch = true
}

resource "aws_subnet" "private_subnet_a" {
  vpc_id = aws_vpc.vpc_prd.id
  cidr_block = "10.0.3.0/24"
  availability_zone = "us-east-1a"
}

resource "aws_subnet" "private_subnet_b" {
  vpc_id = aws_vpc.vpc_prd.id
  cidr_block = "10.0.4.0/24"
  availability_zone = "us-east-1b"
}

resource "aws_internet_gateway" "ig" {
  vpc_id = aws_vpc.vpc_prd.id
}

resource "aws_route_table" "prod-public-crt" {
    vpc_id = aws_vpc.vpc_prd.id
    
    route {
        //associated subnet can reach everywhere
        cidr_block = "0.0.0.0/0" 
        //CRT uses this IGW to reach internet
        gateway_id = aws_internet_gateway.ig.id
    }
    
}

resource "aws_route_table_association" "prod-crta-public-subnet-1"{
    subnet_id = aws_subnet.public_subnet_a.id
    route_table_id = aws_route_table.prod-public-crt.id
}

resource "aws_db_instance" "relayer_database" {
  allocated_storage = 8
  engine = "mysql"
  engine_version = "5.7"
  instance_class = "db.t2.micro"
  db_name = "relayer_database"
  username = "root"
  password = "mdymen_pass"
  port = 3306
  skip_final_snapshot = true
  db_subnet_group_name = aws_db_subnet_group.db_subnet.id
  vpc_security_group_ids = [aws_security_group.terraform_segurity_group.id]
  publicly_accessible = true
}

resource "aws_db_subnet_group" "db_subnet" {
  name = "dbsubnet"
  subnet_ids = [aws_subnet.public_subnet_a.id, aws_subnet.public_subnet_b.id]
}


locals {

     ec2_instances = [
       {
            name = "relayerproducer",
            ip_address = "10.0.0.4",
            depends_on = [],
            script = <<-EOF
                        #!/bin/bash
                        sudo apt-get update 
                        wget https://kafkalisteners.s3.amazonaws.com/relayerproducer-0.0.1-SNAPSHOT.jar 
                        java -Dserver.port=8080 -jar relayerproducer-0.0.1-SNAPSHOT.jar                      
                     EOF      
       },
       {
            name = "relayerconsumer",
            ip_address = "10.0.0.5",
            depends_on = [],
            script = <<-EOF
                        #!/bin/bash
                        sudo apt-get update 
                        wget https://kafkalisteners.s3.amazonaws.com/relayerconsumer-0.0.1-SNAPSHOT.jar                     
                     EOF      
       },
       {
            name = "messagerelayer",
            ip_address = "10.0.0.6",
            depends_on = [],
            script = <<-EOF
                        #!/bin/bash
                        sudo apt-get update 
                        wget https://kafkalisteners.s3.amazonaws.com/messagerelayer-0.0.1-SNAPSHOT.jar                  
                     EOF      
       },         
       {
            name = "outboxconsumer",
            ip_address = "10.0.0.7",
            depends_on = [],
            script = <<-EOF
                        #!/bin/bash
                        sudo apt-get update 
                        wget https://kafkalisteners.s3.amazonaws.com/outboxconsumer-0.0.1-SNAPSHOT.jar                  
                     EOF      
       } 
     ]

}

resource "aws_instance" "terraform_ec2_example" {
    for_each = {
       for index, vm in local.ec2_instances:
       index => vm
    }
    ami = "ami-0cff7528ff583bf9a"
    instance_type = "t2.micro"
    private_ip = each.value.ip_address
    vpc_security_group_ids = [
         aws_security_group.terraform_segurity_group.id
    ]
    key_name   = "mdymen"    
    subnet_id = aws_subnet.public_subnet_a.id
    tags = {
        Name = each.value.name
    }	
    user_data = each.value.script  
}
