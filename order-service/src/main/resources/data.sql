INSERT INTO users (id, email, firstname, lastname, password, username) VALUES (1, 'user@miu.edu', 'user', 'user', 'user', 'user');
INSERT INTO users (id, email, firstname, lastname, password, username) VALUES (2, 'admin@miu.edu', 'admin', 'admin', 'admin', 'admin');
INSERT INTO role (id, role) VALUES(1, 'USER');
INSERT INTO role (id, role) VALUES(2, 'ADMIN');
INSERT INTO users_roles (user_id, roles_id) VALUES(1, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES(2, 2);