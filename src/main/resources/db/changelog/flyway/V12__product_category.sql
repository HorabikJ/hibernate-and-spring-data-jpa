create table category
(
    id            bigint not null auto_increment primary key,
    description   varchar(50),
    created_date  timestamp,
    modified_date timestamp
) engine = InnoDB;

create table product_category
(
    product_id  bigint not null,
    category_id bigint not null,
    primary key (product_id, category_id),
    constraint pc_product_id_fk foreign key (product_id) references product (id),
    constraint pc_category_id_fk foreign key (category_id) references product (id)
) engine = InnoDB;
