-- this file starts with `V1__` as it is the default naming convention in Flyway.
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

-- Below table book_seq was created just for educational purposes. One of the strategies in hibernate for keeping 
-- primary keys for tables is keeping them in a separate table, called <table_name>_seq. We can tell hibernate to do 
-- this by annotating the `@Id` field with `@GeneratedValue(strategy = GenerationType.TABLE)`. This solution is not 
-- advised, as it can slow down performance due to often locking of this xyz_seq table. Much better solution is to 
-- have a `id` column with `auto_increment` strategy in given table. As is is implemented in `V4__autoincrement_pk.sql`
-- file.
-- Also see first slide in presentations/08_Hibernate-Primary-Keys/HibernatePKOverview.pdf
create table book_seq
(
    next_val bigint
) engine=InnoDB;

insert into book_seq
values (1);
