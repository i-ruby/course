CREATE TABLE session
(
    id         SERIAL PRIMARY KEY,
    cookie     TEXT UNIQUE NOT NULL,
    account_id INT         NOT NULL
);