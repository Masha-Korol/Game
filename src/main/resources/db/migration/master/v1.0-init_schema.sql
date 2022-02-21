-- v1.0
CREATE TABLE game_users
(
    game_user_id serial       NOT NULL,
    picture_id   integer,
    login        varchar(100) NOT NULL UNIQUE,
    password     varchar(100) NOT NULL,
    email        varchar(100) UNIQUE,
    CONSTRAINT game_users_pk PRIMARY KEY (game_user_id)
);



CREATE TABLE files
(
    file_id serial       NOT NULL,
    name    varchar(100),
    type    varchar(100) NOT NULL,
    data    bytea        NOT NULL,
    CONSTRAINT files_pk PRIMARY KEY (file_id)
);



CREATE TABLE tiledmaps
(
    tiledmap_id serial  NOT NULL,
    tileset_id  integer NOT NULL,
    name        varchar(100) UNIQUE,
    data        jsonb   NOT NULL,
    CONSTRAINT tiledmaps_pk PRIMARY KEY (tiledmap_id)
);



CREATE TABLE tilesets
(
    tileset_id serial  NOT NULL,
    file_id    integer NOT NULL,
    name       varchar(100) UNIQUE,
    data       jsonb   NOT NULL,
    CONSTRAINT tilesets_pk PRIMARY KEY (tileset_id)
);



CREATE TABLE rooms (
	room_id serial NOT NULL,
	game_leader integer NOT NULL,
	template_id integer NOT NULL,
	room_type varchar(100) NOT NULL,
	name varchar(100),
	code varchar(100),
	CONSTRAINT rooms_pk PRIMARY KEY (room_id)
);



CREATE TABLE maps
(
    map_id      serial  NOT NULL,
    tiledmap_id integer NOT NULL,
    preview_id  integer NOT NULL,
    name        varchar(100) UNIQUE,
    description varchar(100),
    CONSTRAINT maps_pk PRIMARY KEY (map_id)
);



CREATE TABLE task_template
(
    task_template_id    serial      NOT NULL,
    task_type_id        integer     NOT NULL,
    general_template    json        NOT NULL,
    CONSTRAINT task_template_pk PRIMARY KEY (task_template_id)
);



CREATE TABLE game_templates
(
    template_id       serial  NOT NULL,
    owner_id          integer NOT NULL,
    map_id            integer,
    name              varchar(100),
    number_of_players integer NOT NULL,
    CONSTRAINT game_templates_pk PRIMARY KEY (template_id)
);



CREATE TABLE room_players
(
    game_user_id integer NOT NULL,
    room_id      integer NOT NULL
);



CREATE TABLE user_authorization_roles
(
    role_id   serial NOT NULL,
    role_name varchar(100),
    CONSTRAINT user_authorization_roles_pk PRIMARY KEY (role_id)
);



CREATE TABLE game_user_roles
(
    game_user_id integer NOT NULL,
    role_id      integer NOT NULL
);



ALTER TABLE game_users
    ADD CONSTRAINT game_users_fk0 FOREIGN KEY (picture_id) REFERENCES files (file_id);

ALTER TABLE rooms
    ADD CONSTRAINT rooms_fk0 FOREIGN KEY (game_leader) REFERENCES game_users (game_user_id);
ALTER TABLE rooms
    ADD CONSTRAINT rooms_fk1 FOREIGN KEY (template_id) REFERENCES game_templates (template_id);

ALTER TABLE maps
    ADD CONSTRAINT maps_fk0 FOREIGN KEY (tiledmap_id) REFERENCES tiledmaps (tiledmap_id);
ALTER TABLE maps
    ADD CONSTRAINT maps_fk1 FOREIGN KEY (preview_id) REFERENCES files (file_id);

ALTER TABLE tiledmaps
    ADD CONSTRAINT tiledmaps_fk0 FOREIGN KEY (tileset_id) REFERENCES tilesets (tileset_id);

ALTER TABLE tilesets
    ADD CONSTRAINT tilesets_fk0 FOREIGN KEY (file_id) REFERENCES files (file_id);

ALTER TABLE game_templates
    ADD CONSTRAINT game_templates_fk0 FOREIGN KEY (owner_id) REFERENCES game_users (game_user_id);
ALTER TABLE game_templates
    ADD CONSTRAINT game_templates_fk1 FOREIGN KEY (map_id) REFERENCES maps (map_id);

ALTER TABLE room_players
    ADD CONSTRAINT room_players_fk0 FOREIGN KEY (game_user_id) REFERENCES game_users (game_user_id);
ALTER TABLE room_players
    ADD CONSTRAINT room_players_fk1 FOREIGN KEY (room_id) REFERENCES rooms (room_id);

ALTER TABLE game_user_roles
    ADD CONSTRAINT game_user_roles_fk0 FOREIGN KEY (game_user_id) REFERENCES game_users (game_user_id);
ALTER TABLE game_user_roles
    ADD CONSTRAINT game_user_roles_fk1 FOREIGN KEY (role_id) REFERENCES user_authorization_roles (role_id);

create sequence game_user_seq
    minvalue 1
    maxvalue 9999999999999
    start with 6
    increment by 1 cache 20;

create sequence tilesets_seq
    minvalue 1
    maxvalue 9999999999999
    start with 3
    increment by 1 cache 20;

create sequence tiledmaps_seq
    minvalue 1
    maxvalue 9999999999999
    start with 2
    increment by 1 cache 20;

create sequence files_seq
    minvalue 1
    maxvalue 9999999999999
    start with 4
    increment by 1 cache 20;

create sequence game_template_seq
    minvalue 1
    maxvalue 9999999999999
    start with 11
    increment by 1 cache 20;

create sequence map_seq
    minvalue 1
    maxvalue 9999999999999
    start with 2
    increment by 1 cache 20;

create sequence room_seq
    minvalue 1
    maxvalue 9999999999999
    start with 6
    increment by 1 cache 20;

create sequence roles_seq
    minvalue 1
    maxvalue 9999999999999
    start with 4
    increment by 1 cache 20;

create sequence task_template_seq
    minvalue 1
    maxvalue 9999999999999
    start with 4
    increment by 1 cache 20;
