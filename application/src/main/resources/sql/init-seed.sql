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
values (1, '경기도');
INSERT INTO region (id, name, parent_id)
values (2, '성남시', 1);
INSERT INTO region (id, name, parent_id)
values (3, '분당구', 2);
INSERT INTO region (id, name, parent_id)
values (4, '삼평동', 3);
INSERT INTO region (id, name, parent_id)
values (5, '정자동', 3);
INSERT INTO region (id, name, parent_id)
values (6, '수내동', 3);

INSERT INTO region (id, name)
values (7, '서울특별시');
INSERT INTO region (id, name, parent_id)
values (8, '강남구', 7);
INSERT INTO region (id, name, parent_id)
values (9, '도곡동', 8);
INSERT INTO region (id, name, parent_id)
values (10, '대치동', 8);

INSERT INTO user_regions(user_id, region_id)
VALUES (1, 4);
INSERT INTO user_regions(user_id, region_id)
VALUES (1, 10);

INSERT INTO category (id, name)
values (1, '가전');
INSERT INTO category (id, name, parent_id)
values (2, '컴퓨터', 1);