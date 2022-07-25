-- auto-generated definition
create table relayer_info
(
    id bigint auto_increment primary key,
    uuid varchar(100) not null,
    name varchar(100) not null
);
alter table relayer_info
    add created datetime not null;

-- auto-generated definition
create table relayer_outbox
(
    id      bigint auto_increment
        primary key,
    headers text         null,
    payload text         not null,
    uuid    varchar(100) not null
);


create table destiny_message
(
    id   bigint auto_increment,
    uuid varchar(100) not null,
    name varchar(100) not null,
    constraint destiny_message_pk
        primary key (id)
);

alter table destiny_message
    add created datetime not null;
