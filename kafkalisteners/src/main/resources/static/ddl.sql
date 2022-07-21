create table outbox_camel_table1
(
    id     bigint auto_increment
        primary key,
    uuid   varchar(60) not null,
    number bigint      not null
);

create table outbox_camel_table2
(
    id     bigint auto_increment
        primary key,
    uuid   varchar(60) not null,
    number bigint      not null
);

create table outbox_camel_table3
(
    id     bigint auto_increment
        primary key,
    uuid   varchar(60) not null,
    number bigint      not null
);

create table outbox_camel_table4
(
    id     bigint auto_increment
        primary key,
    uuid   varchar(60) not null,
    number bigint      not null
);

create table camel_table1
(
    id     bigint auto_increment
        primary key,
    uuid   varchar(60) not null,
    number bigint      not null
);

create table camel_table2
(
    id     bigint auto_increment
        primary key,
    uuid   varchar(60) not null,
    number bigint      not null
);

create table camel_table3
(
    id     bigint auto_increment
        primary key,
    uuid   varchar(60) not null,
    number bigint      not null
);

create table camel_table4
(
    id     bigint auto_increment
        primary key,
    uuid   varchar(60) not null,
    number bigint      not null
);

create table camel_result_table1
(
    id     bigint auto_increment
        primary key,
    uuid   varchar(60) not null,
    number bigint      not null
);