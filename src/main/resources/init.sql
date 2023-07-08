INSERT INTO roles (id, name, description) VALUES (1, 'ROLE_ADMIN', 'Администратор'), (2, 'ROLE_USER','Пользователь'), (3, 'ROLE_GUEST','Гость');
INSERT INTO users (id, first_name, last_name, email, password) VALUES (1, 'admin', 'adminov', 'admin@nikak.net', '$2y$10$kbBc2/YyhalAHuK.SRiFPeu/iENCtVjUS9sK4A3/4b5EXdgqcj0cy');
INSERT INTO users_roles VALUES (1, 1);
