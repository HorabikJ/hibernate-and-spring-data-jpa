create table order_line
(
    id               bigint auto_increment primary key,
    quantity_ordered int,
    order_header_id  bigint,
    product_id       bigint,
    created_date     timestamp,
    modified_date    timestamp,
    constraint order_header_pk foreign key (order_header_id) references order_header (id),
    constraint product_fk foreign key (product_id) references product (id)
) engine = InnoDB;
