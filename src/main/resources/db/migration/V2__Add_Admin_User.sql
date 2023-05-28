-- Добавление пользователя
INSERT INTO users (active, email, password, activation_code, phone_number, username, image_id)
VALUES (true, 'admin@example.com', 'Admin123', null, '+375295895061', 'admin', null);

-- Присвоение ролей ROLE_ADMIN и ROLE_USER
INSERT INTO user_role (user_id, roles)
VALUES (LAST_INSERT_ID(), 'ROLE_ADMIN'), (LAST_INSERT_ID(), 'ROLE_USER');

COMMIT;
