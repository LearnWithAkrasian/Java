DROP DATABASE IF EXISTS khojTheSearch;

CREATE DATABASE khojTheSearch;

USE khojTheSearch;

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer`(
    id bigint auto_increment primary key,
    username varchar(20) not null,
    password varchar(100) not null,
    version bigint not null,
    date_created timestamp not null,
    date_last_updated timestamp not null
);

DROP TABLE IF EXISTS `user_values`;

CREATE TABLE `user_values`(
    id bigint auto_increment primary key,
    user_id bigint not null,
    insert_time timestamp not null,
    user_values bigint not null,
    version bigint not null,
    date_created timestamp not null,
    date_last_updated timestamp not null,
    constraint customer_user_id_fk
    foreign key (user_id) references customer(id)
)