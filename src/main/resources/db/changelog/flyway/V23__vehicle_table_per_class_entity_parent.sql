create table hibernate_sequences
(
    sequence_name varchar(255) not null,
    next_val      bigint,
    primary key (sequence_name)
);

insert into hibernate_sequences(sequence_name, next_val)
values ('default', 0);


# ----tables for concrete parent class below

create table table_per_class_vehicle_entity_concrete_parent
(
    id            bigint  not null,
    make          varchar(255),
    model         varchar(255),
    horse_power   integer not null,
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
);

create table table_per_class_truck_entity_concrete_parent
(
    id            bigint  not null,
    make          varchar(255),
    model         varchar(255),
    horse_power   integer not null,
    load_limit_kg integer not null,
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
);

create table table_per_class_sports_vehicle_entity_concrete_parent
(
    id            bigint  not null,
    make          varchar(255),
    model         varchar(255),
    horse_power   integer not null,
    seconds_to100 decimal(38, 2),
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
);

# ----tables for abstract parent class below

create table table_per_class_vehicle_entity_abstract_parent
(
    id            bigint  not null,
    make          varchar(255),
    model         varchar(255),
    horse_power   integer not null,
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
);

create table table_per_class_truck_entity_abstract_parent
(
    id            bigint  not null,
    make          varchar(255),
    model         varchar(255),
    horse_power   integer not null,
    load_limit_kg integer not null,
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
);

create table table_per_class_sports_vehicle_entity_abstract_parent
(
    id            bigint  not null,
    make          varchar(255),
    model         varchar(255),
    horse_power   integer not null,
    seconds_to100 decimal(38, 2),
    created_date  datetime(6),
    modified_date datetime(6),
    primary key (id)
);
