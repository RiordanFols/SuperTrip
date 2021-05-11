insert into usr (id, username, name, surname, middle_name, passport_id, passport_series, password, is_active, spent)
VALUES (150, 'pavel99', 'Pavel', 'Chernov', 'Vladimirovich', 234123, 1050, '123456', true, 512.25),
       (151, 'manager', 'Anton', 'Turkov', 'Alexandrovich', 0, 0, '123456', true, 0.0),
       (152, 'admin', 'Admin', 'Adminov', 'Adminovich', 0, 0, '123456', true, 0.0);

insert into user_role (user_id, role)
VALUES (150, 'USER'),
       (151, 'MANAGER'),
       (152, 'ADMIN');