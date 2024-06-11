alter table order_line
    add column version integer not null default 0;
