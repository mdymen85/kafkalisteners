-- auto-generated definition
create table relayer_info
(
    id bigint auto_increment primary key,
    uuid varchar(100) not null,
    name varchar(100) not null
);

-- auto-generated definition
create table relayer_outbox
(
    id bigint auto_increment primary key,
    headers text null,
    payload text not null
);

