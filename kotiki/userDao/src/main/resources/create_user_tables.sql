create table cats
(
    id       serial
        constraint cats_pk
            primary key,
    breed    varchar,
    birthday date,
    color    varchar,
    owner_id integer,
    name     varchar
);

alter table cats
    owner to postgres;

create unique index cats_id_uindex
    on cats (id);

create table owners
(
    id       serial
        constraint owners_pk
            primary key,
    name     varchar,
    birthday date
);

alter table owners
    owner to postgres;

create unique index owners_id_uindex
    on owners (id);

CREATE TABLE roles
(
    id   serial
        constraint roles_pk
            primary key,
    name varchar(45) NOT NULL
);

alter table roles
    owner to postgres;

CREATE TABLE users
(
    id       serial
        constraint users_pk
            primary key,
    name     varchar(45) NOT NULL,
    password varchar(64) NOT NULL,
    enabled  bool,
    role_id int,
    owner_id int
)
