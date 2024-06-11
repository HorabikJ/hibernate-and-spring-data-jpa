alter table order_header
    add column version integer not null default 0;
