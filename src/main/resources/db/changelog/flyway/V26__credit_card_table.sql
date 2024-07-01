DROP TABLE IF EXISTS credit_card;

CREATE TABLE credit_card
(
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    credit_card_number VARCHAR(20),
    cvv                VARCHAR(4),
    expiration_date    VARCHAR(7),
    PRIMARY KEY (id)
);
