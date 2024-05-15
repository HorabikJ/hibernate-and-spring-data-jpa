drop table if exists book;
drop table if exists book_seq;

create table book (
                      id bigint not null,
                      isbn varchar(255),
                      publisher varchar(255),
                      title varchar(255),
                      primary key (id)
) engine=InnoDB;

-- In Hibernate 5 and below there was one table `hibernate_sequence` that was used by all tables in db.
-- From Hibernate 6, each entity table has its own sequence table, which is much better from performance point of 
-- view. The sequence table name convention goes like this: <table-name>_seq, that for `book` table gives `book_seq`.  
create table book_seq (
    next_val bigint
) engine=InnoDB;

insert into book_seq values ( 1 );
