CREATE TABLE account
(
    id                 SERIAL PRIMARY KEY,
    username           TEXT UNIQUE NOT NULL,
    encrypted_password TEXT        NOT NULL,
    status             TEXT        NOT NULL DEFAULT 'OK',
    created_at         TIMESTAMP   NOT NULL DEFAULT now(),
    updated_at         TIMESTAMP   NOT NULL DEFAULT now()
);

INSERT INTO account (username, encrypted_password)
VALUES ('student1', ''),
       ('teacher1', ''),
       ('admin1', '');

CREATE TABLE role
(
    id         SERIAL PRIMARY KEY,
    name       TEXT UNIQUE NOT NULL,
    status     TEXT        NOT NULL DEFAULT 'OK',
    created_at TIMESTAMP   NOT NULL DEFAULT now(),
    updated_at TIMESTAMP   NOT NULL DEFAULT now()
);

INSERT INTO role (name)
VALUES ('学生');
INSERT INTO role (name)
VALUES ('老师');
INSERT INTO role (name)
VALUES ('管理员');

CREATE TABLE account_role
(
    id         SERIAL PRIMARY KEY,
    account_id INTEGER   NOT NULL,
    role_id    INTEGER   NOT NULL,
    status     TEXT      NOT NULL DEFAULT 'OK',
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

INSERT INTO account_role (account_id, role_id)
VALUES (1, 1);
INSERT INTO account_role (account_id, role_id)
VALUES (2, 2);
INSERT INTO account_role (account_id, role_id)
VALUES (3, 3);

CREATE TABLE permission
(
    id         SERIAL PRIMARY KEY,
    name       TEXT      NOT NULL,
    role_id    INTEGER   NOT NULL,
    status     TEXT      NOT NULL DEFAULT 'OK',
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

INSERT INTO permission(name, role_id)
values ('用户登录', 1);
INSERT INTO permission(name, role_id)
values ('用户登录', 2);
INSERT INTO permission(name, role_id)
values ('用户登录', 3);
INSERT INTO permission(name, role_id)
values ('课程管理', 2);
INSERT INTO permission(name, role_id)
values ('课程管理', 3);
INSERT INTO permission(name, role_id)
values ('用户管理', 3);