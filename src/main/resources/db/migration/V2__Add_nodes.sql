insert into transfer_map(id, bb, tt, pp, bt, bp, tp)
values (51, 30, 20, 45, 45, 130, 100),
       (52, 30, 20, 45, 20, 155, 115),
       (53, 30, 20, 45, 15, 145, 120),
       (54, 30, 20, 45, 35, 140, 125),
       (55, 30, 20, 45, 50, 125, 130),
       (56, 30, 20, 45, 55, 115, 125);

insert into node(id, name, lat, lon, transfer_map_id)
values (1, 'Bristol', 51.4545, -2.5879, 51),
       (2, 'London', 51.5074, -0.1278, 52),
       (3, 'Coventry', 52.4068, -1.5197, 53),
       (4, 'Cambridge', 52.2053, 0.1218, 54),
       (5, 'Nottingham', 52.9548, -1.1581, 55),
       (6, 'Wolverhampton', 52.5870, -2.1288, 56);