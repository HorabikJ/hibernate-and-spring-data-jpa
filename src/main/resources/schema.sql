-- This file is written in PostgreSQL dialect for PostgreSQL DDL purposes.

DROP TABLE if EXISTS book;
DROP SEQUENCE if EXISTS book_seq;

CREATE TABLE book (
    id bigint NOT NULL,
    isbn VARCHAR(255),
    publisher VARCHAR(255),
    title VARCHAR(255),
    PRIMARY KEY (id)
);

-- In Hibernate 5 and below there was one table `hibernate_sequence` that was used by all tables in db.
-- From Hibernate 6, each entity table has its own sequence table, which is much better from performance point of 
-- view. The sequence table name convention goes like this: <table-name>_seq, that for `book` table gives `book_seq`.
-- So Far `INCREMENT 50` is demanded by Book @Entity, maybe I will change that in future.
CREATE SEQUENCE book_seq INCREMENT 50 START 1;
