CREATE SCHEMA IF NOT EXISTS security;

CREATE TABLE IF NOT EXISTS security.user
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL CHECK (length(trim(username)) >= 1) unique,
    password VARCHAR(100) NOT NULL CHECK ( length(trim(password)) >= 1 ),
    email    VARCHAR(100) NOT NULL CHECK ( length(trim(email)) >= 1 ) unique
);

CREATE INDEX idx_user_email ON security.user(UPPER(email));
CREATE INDEX idx_user_username ON security.user(UPPER(username));

CREATE TABLE IF NOT EXISTS security.authority
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL CHECK (length(trim(name)) >= 1) unique
);

CREATE TABLE IF NOT EXISTS security.user_authority
(
    id           SERIAL PRIMARY KEY,
    id_user      INT NOT NULL REFERENCES security.user (id),
    id_authority INT NOT NULL REFERENCES security.authority (id),
    CONSTRAINT uk_user_authority unique (id_user, id_authority)
)