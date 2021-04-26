insert into usr (id, username, name, surname, middle_name, passport_id, passport_series, password, is_active )
VALUES (150, 'pavel99', 'Pavel', 'Chernov', 'Vladimirovich', 234123, 1050, '123456', true),
       (151, 'manager', 'Anton', 'Turkov', 'Alexandrovich', 0, 0, '123456', true),
       (152, 'admin', 'Admin', 'Adminov', 'Adminovich', 0, 0, '123456', true);

insert into user_role (user_id, role)
VALUES (150, 'USER'),
       (151, 'MANAGER'),
       (152, 'ADMIN');