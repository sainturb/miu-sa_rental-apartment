INSERT INTO users (id, email, firstname, lastname, password, username) VALUES (1, 'user@miu.edu', 'user', 'user', 'user', 'user') ON CONFLICT DO NOTHING;
INSERT INTO users (id, email, firstname, lastname, password, username) VALUES (2, 'admin@miu.edu', 'admin', 'admin', 'admin', 'admin') ON CONFLICT DO NOTHING;
INSERT INTO role (id, role) VALUES(1, 'USER') ON CONFLICT DO NOTHING;
INSERT INTO role (id, role) VALUES(2, 'ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO users_roles (user_id, roles_id) VALUES(1, 1) ON CONFLICT DO NOTHING;
INSERT INTO users_roles (user_id, roles_id) VALUES(2, 2) ON CONFLICT DO NOTHING;