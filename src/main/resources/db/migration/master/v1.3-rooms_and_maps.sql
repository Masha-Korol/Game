-- v1.3
insert into game_db.public.maps (tiledmap_id, preview_id, name, description)
    values (1, 2, 'Standard map', 'Basic map for 4 to 6 players.');

insert into game_db.public.game_templates (owner_id, name, map_id, number_of_players) values (3, 'super', 1, 14);
insert into game_db.public.game_templates (owner_id, name, map_id, number_of_players) values (5, 'cool', 1, 20);
insert into game_db.public.game_templates (owner_id, name, map_id, number_of_players) values (1, 'simple', 1, 5);
insert into game_db.public.game_templates (owner_id, name, map_id, number_of_players) values (2, 'hard', 1, 10);
insert into game_db.public.game_templates (owner_id, name, map_id, number_of_players) values (3, 'new', 1, 3);
insert into game_db.public.game_templates (owner_id, name, map_id, number_of_players) values (1, 'dev1', 1, 2);
insert into game_db.public.game_templates (owner_id, name, map_id, number_of_players) values (1, 'dev2', 1, 3);
insert into game_db.public.game_templates (owner_id, name, map_id, number_of_players) values (1, 'dev3', 1, 4);
insert into game_db.public.game_templates (owner_id, name, map_id, number_of_players) values (1, 'dev4', 1, 5);

insert into game_db.public.rooms (game_leader, template_id, room_type, name, code) values (1, 1, 'PRIVATE', 'us', '123');
insert into game_db.public.rooms (game_leader, template_id, room_type, name) values (3, 1, 'PUBLIC', 'friends');
insert into game_db.public.rooms (game_leader, template_id, room_type, name) values (2, 3, 'PUBLIC', 'fun');
insert into game_db.public.rooms (game_leader, template_id, room_type, name, code) values (3, 5, 'PRIVATE', 'room', '924');
insert into game_db.public.rooms (game_leader, template_id, room_type, name) values (5, 4, 'PUBLIC', 'play');

insert into game_db.public.room_players (game_user_id, room_id) values (1, 1);
insert into game_db.public.room_players (game_user_id, room_id) values (2, 1);
insert into game_db.public.room_players (game_user_id, room_id) values (3, 1);
insert into game_db.public.room_players (game_user_id, room_id) values (4, 1);
insert into game_db.public.room_players (game_user_id, room_id) values (5, 1);
insert into game_db.public.room_players (game_user_id, room_id) values (3, 2);
insert into game_db.public.room_players (game_user_id, room_id) values (1, 2);
insert into game_db.public.room_players (game_user_id, room_id) values (5, 2);
insert into game_db.public.room_players (game_user_id, room_id) values (2, 3);
insert into game_db.public.room_players (game_user_id, room_id) values (3, 3);
insert into game_db.public.room_players (game_user_id, room_id) values (4, 3);
insert into game_db.public.room_players (game_user_id, room_id) values (3, 4);
insert into game_db.public.room_players (game_user_id, room_id) values (2, 4);
insert into game_db.public.room_players (game_user_id, room_id) values (5, 4);
insert into game_db.public.room_players (game_user_id, room_id) values (4, 4);
