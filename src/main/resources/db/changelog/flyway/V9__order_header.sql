create table order_header
(
    id                BIGINT auto_increment,
    customer          varchar(255),
    shipping_address  varchar(30),
    shipping_city     varchar(30),
    shipping_state    varchar(30),
    shipping_zip_code varchar(30),
    billing_address   varchar(30),
    billing_city      varchar(30),
    billing_state     varchar(30),
    billing_zip_code  varchar(30),
    order_status      varchar(30),
    created_date      timestamp,
    modified_date     timestamp,
    primary key (id)
) engine = InnoDB;
