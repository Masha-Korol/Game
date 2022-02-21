-- v1.1
insert into game_db.public.game_users (login, password, email) values ('Sasha', '$2a$09$2UMoR6OLcvLASPxRxnX4G.gQOUdGfs6ixY3xIdfUXE65utwqcfUby', 's@mail.ru');
insert into game_db.public.game_users (login, password, email) values ('Petya', '$2a$09$0BWegF9rseUjHv9/JyhIG.qxpEj/KSNvj65wciBUk3mNAom3JaskO', 'p@mail.ru');
insert into game_db.public.game_users (login, password, email) values ('Kostya', '$2a$09$hXE3iTacKuNCzXe2u5ACAu76Oxo5ZHB1Q7KgxB4GZxo1K8G78M7oe', 'k@mail.ru');
insert into game_db.public.game_users (login, password, email) values ('Vicka', '$2a$09$hgOIvOs/vITrmUIYs/Y6hubthd5rGg70GQHpAzHIezjiszlmOaj6O', 'v@mail.ru');
insert into game_db.public.game_users (login, password, email) values ('Lev', '$2a$09$RoAh8/c6.yTzBz/72w02P.Wu1f64l6ar2Xw/gyGJdWqoDuJDxRaFK', 'l@mail.ru');

insert into game_db.public.user_authorization_roles (role_name) values ('ROLE_USER');
insert into game_db.public.user_authorization_roles (role_name) values ('ROLE_MODERATOR');
insert into game_db.public.user_authorization_roles (role_name) values ('ROLE_ADMIN');

insert into game_db.public.game_user_roles (game_user_id, role_id) values (1, 1);
insert into game_db.public.game_user_roles (game_user_id, role_id) values (1, 2);
insert into game_db.public.game_user_roles (game_user_id, role_id) values (1, 3);
insert into game_db.public.game_user_roles (game_user_id, role_id) values (2, 1);
insert into game_db.public.game_user_roles (game_user_id, role_id) values (2, 2);
insert into game_db.public.game_user_roles (game_user_id, role_id) values (3, 1);
insert into game_db.public.game_user_roles (game_user_id, role_id) values (4, 1);
insert into game_db.public.game_user_roles (game_user_id, role_id) values (5, 1);
