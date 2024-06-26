-- This file is for creating the database structure/tables. Spring default location for this file is 
-- `resources/schema.sql`.
-- Currently we are not using this file, the usage of this file is disabled by `spring.sql.init.mode=never` property 
-- in the properties file.
drop table if exists book;
drop table if exists book_seq;

create table book
(
    id        bigint not null,
    isbn      varchar(255),
    publisher varchar(255),
    title     varchar(255),
    primary key (id)
) engine=InnoDB;

create table book_seq
(
    next_val bigint
) engine=InnoDB;

insert into book_seq
values (1);
