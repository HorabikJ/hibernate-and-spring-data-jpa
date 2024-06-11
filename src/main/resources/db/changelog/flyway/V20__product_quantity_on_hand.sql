alter table product
    add column quantity_on_hand integer not null default 0;

update product
set quantity_on_hand = 10;
