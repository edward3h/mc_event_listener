--liquibase formatted sql
--changeset edward3h:1

create table location (
    id integer primary key asc,
    world text not null,
    player text not null,
    dimension text not null,
    x double not null,
    y double not null,
    z double not null,
    created_at DATETIME NOT NULL DEFAULT (datetime(CURRENT_TIMESTAMP, 'localtime'))
);
--rollback drop table location;

create table event (
    id integer primary key asc,
    world text not null,
    player text not null,
    event text not null,
    detail text,
    count integer not null default 0
);
--rollback drop table event;