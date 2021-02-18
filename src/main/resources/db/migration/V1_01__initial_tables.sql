CREATE TABLE number_authorizations
(
    id                      BIGSERIAL PRIMARY KEY,
    quantity                INTEGER   DEFAULT 3,
    last_authorization_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users
(
    id                      BIGSERIAL PRIMARY KEY,
    username                CHARACTER VARYING(64) UNIQUE NOT NULL,
    password                CHARACTER VARYING(512)       NOT NULL,
    first_name              CHARACTER VARYING(64)        NOT NULL,
    last_name               CHARACTER VARYING(64)        NOT NULL,
    email                   CHARACTER VARYING(64) UNIQUE NOT NULL,
    age                     INTEGER,
    number_authorization_id BIGINT REFERENCES number_authorizations (id),
    enabled                 BOOLEAN DEFAULT FALSE
);

CREATE TABLE confirmation_tokens
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT REFERENCES users (id) NOT NULL,
    token       CHARACTER VARYING(256),
    status      CHARACTER VARYING(64),
    issued_date TIMESTAMP                    NOT NULL
);

CREATE TABLE roles
(
    id   BIGSERIAL PRIMARY KEY,
    name CHARACTER VARYING(64) UNIQUE NOT NULL
);

CREATE TABLE users_roles
(
    user_id BIGINT REFERENCES users (id),
    role_id BIGINT REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE books
(
    id             BIGSERIAL PRIMARY KEY,
    name           CHARACTER VARYING(128) NOT NULL,
    book_condition CHARACTER VARYING(128) NOT NULL,
    description    CHARACTER VARYING(512)
);

CREATE TABLE order_books
(
    id         BIGSERIAL PRIMARY KEY,
    status     CHARACTER VARYING(64),
    user_id    BIGINT REFERENCES users (id) NOT NULL,
    book_id    BIGINT REFERENCES books (id) NOT NULL,
    note       CHARACTER VARYING(512),
    start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_date   TIMESTAMP
);
