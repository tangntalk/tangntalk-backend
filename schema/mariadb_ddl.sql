-- DROP DATABASE tangntalk;

CREATE DATABASE IF NOT EXISTS tangntalk;

# location table ------------------------------------
CREATE TABLE IF NOT EXISTS tangntalk.location(
    name varchar(255) NOT NULL UNIQUE
);

ALTER TABLE tangntalk.location
    ADD CONSTRAINT location_pk PRIMARY KEY (name);

INSERT IGNORE INTO tangntalk.location VALUES ('공학관');
INSERT IGNORE INTO tangntalk.location VALUES ('백양관');
INSERT IGNORE INTO tangntalk.location VALUES ('학생회관');
INSERT IGNORE INTO tangntalk.location VALUES ('신촌역');



# yt_user table -------------------------------------
CREATE TABLE IF NOT EXISTS tangntalk.yt_user(
    user_id varchar(255) NOT NULL UNIQUE,
    name varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    #user_time timestamp NOT NULL,
    status_message varchar(255),
    type varchar(255) NOT NULL,
    connection_status boolean NOT NULL,
    user_location varchar(255) NOT NULL DEFAULT '공학관'
);

ALTER TABLE tangntalk.yt_user
    ADD CONSTRAINT yt_user_pk PRIMARY KEY (user_id);

ALTER TABLE tangntalk.yt_user
    ADD CONSTRAINT user_location_fk FOREIGN KEY (user_location) REFERENCES tangntalk.location(name);

ALTER TABLE tangntalk.yt_user
    ADD CONSTRAINT type_check CHECK (type IN ('일반', '학생', '강사', '기업'));

# friends table ------------------------------------
CREATE TABLE IF NOT EXISTS tangntalk.friends(
    user_id varchar(255) NOT NULL,
    friend_id varchar(255) NOT NULL
);

ALTER TABLE tangntalk.friends
    ADD CONSTRAINT friends_pk FOREIGN KEY (user_id) REFERENCES tangntalk.yt_user(user_id) ON DELETE cascade ;

ALTER TABLE tangntalk.friends
    ADD CONSTRAINT friends_fk FOREIGN KEY (friend_id) REFERENCES tangntalk.yt_user(user_id) ON DELETE cascade;

# chatroom table ------------------------------------
CREATE TABLE IF NOT EXISTS tangntalk.chatroom(
    chatroom_id bigint NOT NULL UNIQUE AUTO_INCREMENT,
    user_1 varchar(255) NOT NULL,
    user_2 varchar(255) NOT NULL,
    last_message_id bigint
);

ALTER TABLE tangntalk.chatroom
    ADD CONSTRAINT user_1_fk FOREIGN KEY (user_1) REFERENCES tangntalk.yt_user(user_id) ON DELETE cascade;

ALTER TABLE tangntalk.chatroom
    ADD CONSTRAINT user_2_fk FOREIGN KEY (user_2) REFERENCES tangntalk.yt_user(user_id) ON DELETE cascade;

# ALTER TABLE tangntalk.chatroom
#     ADD CONSTRAINT last_send_user_fk FOREIGN KEY (last_send_user) REFERENCES tangntalk.yt_user(user_id) ON DELETE cascade;


# message table ------------------------------------
CREATE TABLE IF NOT EXISTS tangntalk.message(
    message_id bigint NOT NULL AUTO_INCREMENT UNIQUE,
    chatroom_id bigint NOT NULL,
    sender_id varchar(255) NOT NULL,
#     receiver_id varchar(255) NOT NULL,
    content varchar(255) NOT NULL,
#     not null이라고 하면 current_timestamp constraint를 추가하는데,
#     이러면 update시 current_timestamp를 null로 계속 준다. 따라서 null value를 허용하도록 바꿈
    send_time timestamp NULL,
    read_time timestamp NULL ,
    rendezvous_flag boolean NOT NULL,
    rendezvous_location varchar(255),
    rendezvous_time timestamp NULL
#     rendezvous_time_flag boolean,
#    rendezvous_time_location_flag boolean NOT NULL
);

ALTER TABLE tangntalk.message
    ADD CONSTRAINT message_pk PRIMARY KEY (message_id);

ALTER TABLE tangntalk.message
    ADD CONSTRAINT message_location_fk FOREIGN KEY (rendezvous_location) REFERENCES tangntalk.location(name);

ALTER TABLE tangntalk.message
    ADD CONSTRAINT sender_id_fk FOREIGN KEY (sender_id) REFERENCES tangntalk.yt_user(user_id) ON DELETE cascade;

# ALTER TABLE tangntalk.message
#     ADD CONSTRAINT receiver_id_fk FOREIGN KEY (receiver_id) REFERENCES tangntalk.yt_user(user_id) ON DELETE cascade;

ALTER TABLE tangntalk.message
    ADD CONSTRAINT chatroom_id_fk FOREIGN KEY (chatroom_id) REFERENCES tangntalk.chatroom(chatroom_id) ON DELETE cascade;

ALTER TABLE tangntalk.message
    ALTER rendezvous_flag SET DEFAULT false;

-- location 테이블에 not null이어서 여기도 not null로 해야할듯..
-- ALTER TABLE tangntalk.message
--     ALTER rendezvous_location SET DEFAULT NULL;

-- ALTER TABLE tangntalk.message
--    ALTER rendezvous_time_flag SET DEFAULT false;

ALTER TABLE tangntalk.message
    ALTER rendezvous_time SET DEFAULT 0;

ALTER TABLE tangntalk.message
    ALTER read_time SET DEFAULT 0;

-- ALTER TABLE tangntalk.message
--    ALTER rendezvous_time_location_flag SET DEFAULT false;

ALTER TABLE tangntalk.chatroom
    ADD CONSTRAINT last_message_id_fk FOREIGN KEY (last_message_id) REFERENCES tangntalk.message(message_id) ON DELETE SET NULL;