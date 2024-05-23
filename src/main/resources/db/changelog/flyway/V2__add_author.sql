drop table if exists author;
drop table if exists author_seq;

create table author
(
    id         bigint not null,
    first_name varchar(255),
    last_name  varchar(255),
    primary key (id)
) engine = InnoDB;

-- Below table author_seq was created just for educational purposes. One of the strategies in hibernate for keeping 
-- primary keys for tables is keeping them in a separate table, called <table_name>_seq. We can tell hibernate to do 
-- this by annotating the `@Id` field with `@GeneratedValue(strategy = GenerationType.TABLE)`. This solution is not 
-- advised, as it can slow down performance due to often locking of this xyz_seq table. Much better solution is to 
-- have a `id` column with `auto_increment` strategy in given table. As is is implemented in `V4__autoincrement_pk.sql`
-- file.
-- Also see first slide in presentations/08_Hibernate-Primary-Keys/HibernatePKOverview.pdf
create table author_seq
(
    next_val bigint
) engine=InnoDB;

insert into author_seq
values (1);
