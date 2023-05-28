-- migration V2__Add_Admin_User.sql

-- Добавление пользователя
INSERT INTO users (active, email, password, activation_code, phone_number, username, image_id)
VALUES (true, 'admin@example.com', 'password', null, '+123456789', 'admin', null);

-- Получение идентификатора добавленного пользователя
SET @user_id = LAST_INSERT_ID();

-- Присвоение роли ROLE_ADMIN
INSERT INTO user_role (user_id, roles)
VALUES (@user_id, 'ROLE_ADMIN');

COMMIT;
