insert into owners (name, birthday)
VALUES ('Elvis Presley', '08.01.1935'::date);

insert into cats (breed, birthday, color, owner_id, name)
values ('england', '01.01.2022'::date, 'GREY', (SELECT id FROM owners LIMIT 1), 'John'),
       ('england', '01.01.2022'::date, 'BLACK', (SELECT id FROM owners LIMIT 1), 'Paul'),
       ('england', '01.01.2022'::date, 'WHITE', (SELECT id FROM owners LIMIT 1), 'George'),
       ('england', '01.01.2022'::date, 'MULTICOLOR', (SELECT id FROM owners LIMIT 1), 'Ringo');