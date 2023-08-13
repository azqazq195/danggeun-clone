INSERT INTO `user` (temperature, created_at, id, modified_at, email, nickname, password, phone, profile_url, role)
VALUES (36.0,
        NOW(),
        1,
        NOW(),
        'moseoh@gmail.com',
        'moseoh',
        '$2a$10$hI2OezXWwNYNve2SwNt5Q.jVnyOvxHUSqL/fTdzzVjARU0BQ7saJ2',
        '01012345678',
        'url',
        'USER');
INSERT INTO region (id, name)
values (1, '삼평동');
INSERT INTO region (id, name)
values (2, '도곡동');
INSERT INTO user_regions(region_id, user_id)
VALUES (1, 1);
INSERT INTO user_regions(region_id, user_id)
VALUES (2, 1);
