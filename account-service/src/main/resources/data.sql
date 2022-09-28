INSERT INTO users (id, email, firstname, lastname, password, username) VALUES (1, 'user@miu.edu', 'user', 'user', 'user', 'user') ON CONFLICT DO NOTHING;
INSERT INTO users (id, email, firstname, lastname, password, username) VALUES (2, 'saintur.batkhuu@miu.edu', 'admin', 'admin', 'admin', 'admin') ON CONFLICT DO NOTHING;
INSERT INTO users (id, email, firstname, lastname, password, username) VALUES (3, 'system@miu.edu', 'system', 'system', 'system', 'system') ON CONFLICT DO NOTHING;
INSERT INTO role (id, role) VALUES(1, 'USER') ON CONFLICT DO NOTHING;
INSERT INTO role (id, role) VALUES(2, 'ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO role (id, role) VALUES(3, 'SERVICE') ON CONFLICT DO NOTHING;

INSERT INTO users_roles
(user_id, roles_id)
SELECT 1, 1
WHERE
    NOT EXISTS (
            SELECT user_id FROM users_roles WHERE user_id = 1 AND roles_id = 1
        );

INSERT INTO users_roles
(user_id, roles_id)
SELECT 2, 2
WHERE
    NOT EXISTS (
            SELECT user_id FROM users_roles WHERE user_id = 2 AND roles_id = 2
        );