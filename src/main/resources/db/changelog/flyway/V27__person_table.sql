DROP TABLE IF EXISTS person;

CREATE TABLE person
(
    id                      BIGINT NOT NULL AUTO_INCREMENT,
    name                    VARCHAR(255),
    surname                 VARCHAR(255),
    personal_data_to_encode VARCHAR(255),
    created_date            DATETIME(6),
    modified_date           DATETIME(6),
    primary key (id)
);
