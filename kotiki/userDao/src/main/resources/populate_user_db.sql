insert into owners (name, birthday)
VALUES ('Elvis Presley', '08.01.1935'::date),
       ('Kenedy', '08.01.1930'::date);

insert into cats (breed, birthday, color, owner_id, name)
values ('england', '01.01.2022'::date, 'GREY', (SELECT id FROM owners LIMIT 1), 'John'),
       ('england', '01.01.2022'::date, 'BLACK', (SELECT id FROM owners LIMIT 1), 'Paul'),
       ('england', '01.01.2022'::date, 'WHITE', (SELECT id FROM owners LIMIT 1), 'George'),
       ('england', '01.01.2022'::date, 'MULTICOLOR', (SELECT id FROM owners LIMIT 1), 'Ringo');

insert into roles (name)
values ('ADMIN'),
       ('USER');

insert into users (name, password, enabled, role_id, owner_id)
VALUES ('elvis', 'password', true, (SELECT id FROM roles WHERE roles.name = 'USER'), (SELECT min(id) FROM owners)),
       ('kenedy', 'password', true, (SELECT id FROM roles WHERE roles.name = 'ADMIN'), (SELECT max(id) FROM owners));