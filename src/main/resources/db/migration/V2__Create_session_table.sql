CREATE TABLE session
(
    id         SERIAL PRIMARY KEY,
    cookie     TEXT UNIQUE NOT NULL,
    account_id INT         NOT NULL
);

INSERT INTO session(cookie, account_id)
VALUES ('student_user_cookie', 1);
INSERT INTO session(cookie, account_id)
VALUES ('teacher_user_cookie', 2);
INSERT INTO session(cookie, account_id)
VALUES ('admin_user_cookie', 3);