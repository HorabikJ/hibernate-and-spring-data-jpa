create table table_per_class_truck
(
    id            bigint  not null auto_increment primary key,
    model         varchar(50),
    make          varchar(50),
    horse_power   integer,
    load_limit_kg integer,
    version       integer not null default 0,
    created_date  timestamp,
    modified_date timestamp
);

create table table_per_class_sports_vehicle
(
    id            bigint  not null auto_increment primary key,
    model         varchar(50),
    make          varchar(50),
    horse_power   integer,
    seconds_to100 decimal,
    version       integer not null default 0,
    created_date  timestamp,
    modified_date timestamp
);
