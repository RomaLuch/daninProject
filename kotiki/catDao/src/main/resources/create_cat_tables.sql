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
