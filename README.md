# kafkalisteners

![alt text](https://github.com/mdymen85/kafkalisteners/blob/main/diagram.png)

App With Multiple Listeners, Multiple Apache Camel Interactions, and else....

wget https://##/messagerelayer-0.0.1-SNAPSHOT.jar 
wget https://##/outboxrelayer-0.0.1-SNAPSHOT.jar 
wget https://##/relayerconsumer-0.0.1-SNAPSHOT.jar 

sudo su
yum update -y
wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.rpm
rpm -ivh jdk-17_linux-x64_bin.rpm

wget https://archive.apache.org/dist/kafka/2.6.2/kafka_2.12-2.6.2.tgz
tar -xzf kafka_2.12-2.6.2.tgz
cd kafka_2.12-2.6.2/

bin/kafka-topics.sh --create --bootstrap-server b-2.kafkacluster.hsx4os.c24.kafka.us-east-1.amazonaws.com:9092 --topic relayer-topic

bin/kafka-topics.sh --create --bootstrap-server b-2.kafkacluster.hsx4os.c24.kafka.us-east-1.amazonaws.com:9092 --topic camel-relayer-topic

bin/kafka-topics.sh --delete --bootstrap-server b-2.kafkacluster.hsx4os.c24.kafka.us-east-1.amazonaws.com:9092 --topic relayer-topic

producer
java -jar -Dspring.kafka.producer.bootstrap-servers=b-2.kafkacluster.hsx4os.c24.kafka.us-east-1.amazonaws.com:9092 -DBOOTSTRAP_SERVER=b-2.kafkacluster.hsx4os.c24.kafka.us-east-1.amazonaws.com:9092 -Dserver.port=8080 relayerproducer-0.0.1-SNAPSHOT.jar

consumer
java -jar -DMYSQL_DB=relayer_database -DMYSQL_HOST=terraform-20220807165336061000000002.c8msbexhqwuz.us-east-1.rds.amazonaws.com  -Dspring.kafka.consumer.bootstrap-servers=b-2.kafkacluster.hsx4os.c24.kafka.us-east-1.amazonaws.com:9092 -DBOOSTRAP_SERVER=b-2.kafkacluster.hsx4os.c24.kafka.us-east-1.amazonaws.com:9092 -Dserver.port=8080 relayerconsumer-0.0.1-SNAPSHOT.jar

message relayer

java -jar -DMYSQL_DB=relayer_database -DMYSQL_HOST=terraform-20220807165336061000000002.c8msbexhqwuz.us-east-1.rds.amazonaws.com  -Dspring.kafka.producer.bootstrap-servers=b-2.kafkacluster.hsx4os.c24.kafka.us-east-1.amazonaws.com:9092 -DBOOSTRAP_SERVER=b-2.kafkacluster.hsx4os.c24.kafka.us-east-1.amazonaws.com:9092 -Dserver.port=8080 messagerelayer-0.0.1-SNAPSHOT.jar

outboxconsumer
java -jar -DMYSQL_DB=relayer_database -DMYSQL_HOST=terraform-20220807165336061000000002.c8msbexhqwuz.us-east-1.rds.amazonaws.com  -Dspring.kafka.consumer.bootstrap-servers=b-2.kafkacluster.hsx4os.c24.kafka.us-east-1.amazonaws.com:9092 -DBOOTSTRAP_SERVER=b-2.kafkacluster.hsx4os.c24.kafka.us-east-1.amazonaws.com:9092 -Dserver.port=8080 outboxconsumer-0.0.1-SNAPSHOT.jar

