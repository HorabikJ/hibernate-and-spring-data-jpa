create table book_uuid
(
-- Storing UUID in binary format is more efficient from db storage perspective as it takes only 16 bytes rather than 
-- 36 bytes as in `author_uuid` example. That is why we created this `book_uuid` entity, to demonstrate that.
-- The way of storing UUID in a binary format is called UUID RFC 4122
    id        binary(16) not null,
    isbn      varchar(255),
    publisher varchar(255),
    title     varchar(255),
    primary key (id)
) engine=InnoDB;
