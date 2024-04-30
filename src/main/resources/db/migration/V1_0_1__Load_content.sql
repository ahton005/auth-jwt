INSERT INTO security.user (username, password, email)
VALUES ('admin', '{bcrypt}$2a$12$eWMJ4crDn3OmZev4w4sg9.xbpnyq8G1FA.qCB6ehdTvV8qONAAE/q', 'admin@mail.ru'),
       ('user', '{bcrypt}$2a$12$lRqtx5in2z1FWdmsY/g9I.8A9KI0j5.zEp39iD706.kbYV3nr550m', 'user@mail.ru');

INSERT INTO security.authority (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

INSERT INTO security.user_authority (id_user, id_authority)
VALUES (1, 1),
       (2, 2);
