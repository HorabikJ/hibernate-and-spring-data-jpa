create table joined_table_vehicle
(
    id            bigint  not null auto_increment,
    created_date  datetime(6),
    modified_date datetime(6),
    horse_power   integer not null,
    make          varchar(255),
    model         varchar(255),
    primary key (id)
);

create table joined_table_truck
(
    load_limit_kg integer not null,
    id            bigint  not null,
    primary key (id),
    constraint fk_truck_to_vehicle foreign key (id) references joined_table_vehicle (id)
);

create table joined_table_sports_vehicle
(
    seconds_to100 decimal(38, 2),
    id            bigint not null,
    primary key (id),
    constraint fk_sports_vehicle_to_vehicle foreign key (id) references joined_table_vehicle (id)
);

create table joined_table_electric_sports_vehicle
(
    battery_volume integer,
    id             bigint not null,
    primary key (id),
    constraint fk_electric_sports_vehicle_to_sports_vehicle foreign key (id) references joined_table_sports_vehicle (id)
);


