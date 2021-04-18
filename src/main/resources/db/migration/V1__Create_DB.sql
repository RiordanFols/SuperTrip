alter table if exists node
    drop constraint if exists FK5pdh1j3cxragtocd3b49kw95o;
alter table if exists ticket_trips
    drop constraint if exists FKd80fuu8au08pdv2061r9nsxes;
alter table if exists ticket_trips
    drop constraint if exists FK1sl7bejme6g019nxa1kw94xej;
alter table if exists trip
    drop constraint if exists FKqqtdlq664x5fmlvn58d0r69o4;
alter table if exists trip
    drop constraint if exists FKge5erckfobmfixg5vqqcep83y;
alter table if exists user_role
    drop constraint if exists FKfpm8swft53ulq2hl11yplpr5;

drop table if exists node cascade;
drop table if exists ticket cascade;
drop table if exists ticket_trips cascade;
drop table if exists transfer_map cascade;
drop table if exists trip cascade;
drop table if exists user_role cascade;
drop table if exists usr cascade;
drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start 10000 increment 1;

create table node
(
    id              int8        not null,
    name            varchar(50) not null,
    x               float4      not null,
    y               float4      not null,
    transfer_map_id int8,
    primary key (id)
);

create table ticket
(
    id                        int8        not null,
    passenger_middle_name     varchar(25) not null,
    passenger_name            varchar(25) not null,
    passenger_passport_id     varchar(6)  not null,
    passenger_passport_series varchar(4)  not null,
    passenger_surname         varchar(25) not null,
    primary key (id)
);

create table ticket_trips
(
    ticket_id int8 not null,
    trips_id  int8 not null,
    primary key (ticket_id, trips_id)
);

create table transfer_map
(
    id int8 not null,
    bb int8 not null,
    bp int8 not null,
    bt int8 not null,
    pp int8 not null,
    tp int8 not null,
    tt int8 not null,
    primary key (id)
);

create table trip
(
    id        int8         not null,
    cost      int4         not null,
    from_time timestamp    not null,
    to_time   timestamp,
    type      varchar(255) not null,
    from_id   int8,
    to_id     int8,
    primary key (id)
);

create table user_role
(
    user_id int8         not null,
    role    varchar(255) not null,
    primary key (user_id, role)
);

create table usr
(
    id              int8         not null,
    is_active       boolean      not null,
    middle_name     varchar(25),
    name            varchar(25)  not null,
    passport_id     int4,
    passport_series int4,
    password        varchar(100) not null,
    surname         varchar(25)  not null,
    username        varchar(25)  not null,
    primary key (id)
);

alter table if exists ticket_trips
    add constraint UK_3t24fpg39ys0wnc2fxys5tuxg unique (trips_id);
alter table if exists node
    add constraint FK5pdh1j3cxragtocd3b49kw95o foreign key (transfer_map_id) references transfer_map;
alter table if exists ticket_trips
    add constraint FKd80fuu8au08pdv2061r9nsxes foreign key (trips_id) references trip;
alter table if exists ticket_trips
    add constraint FK1sl7bejme6g019nxa1kw94xej foreign key (ticket_id) references ticket;
alter table if exists trip
    add constraint FKqqtdlq664x5fmlvn58d0r69o4 foreign key (from_id) references node;
alter table if exists trip
    add constraint FKge5erckfobmfixg5vqqcep83y foreign key (to_id) references node;
alter table if exists user_role
    add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr;