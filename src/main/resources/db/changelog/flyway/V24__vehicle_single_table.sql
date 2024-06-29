create table single_table_vehicle
(
    id            bigint  not null,
    make          varchar(255),
    model         varchar(255),
    horse_power   integer not null,
    load_limit_kg integer,
    seconds_to100 decimal(38, 2),
    vehicle_type  varchar(31),
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
);

-- We can not use `not null` on `load_limit_kg` and on `seconds_to100`, as car and truck entities will go the the 
-- same table defined above. All trucks will have null values for `seconds_to100` and all sports vehicles will have 
-- null values for `load_limit_kg`.
