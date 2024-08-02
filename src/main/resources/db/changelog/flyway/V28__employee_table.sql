DROP TABLE IF EXISTS employee;

CREATE TABLE employee
(
    id                    BIGINT NOT NULL AUTO_INCREMENT,
    name                  VARCHAR(255),
    surname               VARCHAR(255),
    salary                DECIMAL(8, 2),
    data_to_encode        VARCHAR(255),
    second_data_to_encode VARCHAR(255),
    created_date          DATETIME(6),
    modified_date         DATETIME(6),
    PRIMARY KEY (id)
);
