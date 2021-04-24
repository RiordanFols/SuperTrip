insert into ticket (number, status, creation_date_time, pas_name, pas_surname,
                    pas_middle_name, pas_passport_id, pas_passport_series)
VALUES ('f4a5300d-9335-44e0-9848-c35a47d4ab5f', 'PAID', '2021-04-24 21:11:36+03',
        'Pavel', 'Chernov', 'Vladimirovich', 234123 , 1050),
       ('eb85bead-9a04-41f7-a632-dd0f00d0ec0d', 'PAID', '2021-04-24 21:11:36+03',
        'Pavel', 'Chernov', 'Vladimirovich', 234123 , 1050),
       ('75ac0b6b-9d78-406b-8239-054d0a8de30f', 'PAID', '2021-04-24 21:11:36+03',
        'Pavel', 'Chernov', 'Vladimirovich', 234123 , 1050);

insert into ticket_trips (ticket_id, trip_id)
VALUES ('f4a5300d-9335-44e0-9848-c35a47d4ab5f', 5001),
       ('eb85bead-9a04-41f7-a632-dd0f00d0ec0d', 6511),
       ('eb85bead-9a04-41f7-a632-dd0f00d0ec0d', 2728),
       ('75ac0b6b-9d78-406b-8239-054d0a8de30f', 3102);