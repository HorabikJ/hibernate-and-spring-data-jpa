insert into product (description, product_status, created_date, modified_date)
values ('PRODUCT1', 'NEW', now(), now());

insert into product (description, product_status, created_date, modified_date)
values ('PRODUCT2', 'NEW', now(), now());

insert into product (description, product_status, created_date, modified_date)
values ('PRODUCT3', 'NEW', now(), now());

insert into product (description, product_status, created_date, modified_date)
values ('PRODUCT4', 'NEW', now(), now());

insert into category (description, created_date, modified_date)
VALUES ('CAT1', now(), now());

insert into category (description, created_date, modified_date)
VALUES ('CAT2', now(), now());

insert into product_category (product_id, category_id)
SELECT p.id, c.id
FROM product p,
     category c
where p.description = 'PRODUCT1'
  and c.description = 'CAT1';

insert into product_category (product_id, category_id)
SELECT p.id, c.id
FROM product p,
     category c
where p.description = 'PRODUCT1'
  and c.description = 'CAT2';

insert into product_category (product_id, category_id)
SELECT p.id, c.id
FROM product p,
     category c
where p.description = 'PRODUCT2'
  and c.description = 'CAT1';

insert into product_category (product_id, category_id)
SELECT p.id, c.id
FROM product p,
     category c
where p.description = 'PRODUCT3'
  and c.description = 'CAT2';

insert into product_category (product_id, category_id)
SELECT p.id, c.id
FROM product p,
     category c
where p.description = 'PRODUCT4'
  and c.description = 'CAT2';
