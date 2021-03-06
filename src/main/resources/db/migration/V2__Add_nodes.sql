insert into transfer_map(id, bb, tt, pp, bt, bp, tp)
values (51, 30, 20, 45, 45, 130, 100),
       (52, 30, 20, 45, 20, 155, 115),
       (53, 30, 20, 45, 15, 145, 120),
       (54, 30, 20, 45, 35, 140, 125),
       (55, 30, 20, 45, 50, 125, 130),
       (56, 30, 20, 45, 55, 115, 125);

insert into node(id, name, lat, lon, transfer_map_id)
values (1, 'Munich', 50.4501, 30.5234, 51),
       (2, 'Vein', 51.5924, 45.9608, 52),
       (3, 'Prague', 55.7558, 37.6173, 53),
       (4, 'Kiev', 54.9885, 73.3242, 54),
       (5, 'Warsaw', 58.0297, 56.2628, 55),
       (6, 'Berlin', 59.9343, 30.3351, 56);