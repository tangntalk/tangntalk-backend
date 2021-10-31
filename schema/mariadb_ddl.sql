DROP TABLE IF EXISTS  yonseitalk.message, yonseitalk.chatroom, yonseitalk.friends, yonseitalk.yt_user, yonseitalk.location cascade;


CREATE DATABASE IF NOT EXISTS yonseitalk;

# location table ------------------------------------
CREATE TABLE IF NOT EXISTS yonseitalk.location(
    name varchar(255) NOT NULL UNIQUE
);

ALTER TABLE yonseitalk.location
    ADD CONSTRAINT location_pk PRIMARY KEY (name);

INSERT IGNORE INTO yonseitalk.location VALUES ('공학관');
INSERT IGNORE INTO yonseitalk.location VALUES ('백양관');
INSERT IGNORE INTO yonseitalk.location VALUES ('학생회관');
INSERT IGNORE INTO yonseitalk.location VALUES ('언더우드관');



# yt_user table -------------------------------------
CREATE TABLE IF NOT EXISTS yonseitalk.yt_user(
    user_id varchar(255) NOT NULL UNIQUE,
    name varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    #user_time timestamp NOT NULL,
    status_message varchar(255),
    type varchar(255) NOT NULL,
    connection_status boolean NOT NULL,
    user_location varchar(255) NOT NULL
);

ALTER TABLE yonseitalk.yt_user
    ADD CONSTRAINT yt_user_pk PRIMARY KEY (user_id);

ALTER TABLE yonseitalk.yt_user
    ADD CONSTRAINT user_location_fk FOREIGN KEY (user_location) REFERENCES yonseitalk.location(name);

ALTER TABLE yonseitalk.yt_user
    ALTER user_location SET DEFAULT '공학관';


# friends table ------------------------------------
CREATE TABLE IF NOT EXISTS yonseitalk.friends(
    user_id varchar(255) NOT NULL,
    friend_id varchar(255) NOT NULL
);

ALTER TABLE yonseitalk.friends
    ADD CONSTRAINT friends_pk FOREIGN KEY (user_id) REFERENCES yonseitalk.yt_user(user_id);

ALTER TABLE yonseitalk.friends
    ADD CONSTRAINT friends_fk FOREIGN KEY (friend_id) REFERENCES yonseitalk.yt_user(user_id);

# chatroom table ------------------------------------
CREATE TABLE IF NOT EXISTS yonseitalk.chatroom(
    chatroom_id bigint NOT NULL UNIQUE AUTO_INCREMENT,
    user_1 varchar(255) NOT NULL,
    user_2 varchar(255) NOT NULL,
    talk varchar(255) NOT NULL,
    last_send_time timestamp NOT NULL,
    last_send_user varchar(255) NOT NULL
);

ALTER TABLE yonseitalk.chatroom
    ADD CONSTRAINT user_1_fk FOREIGN KEY (user_1) REFERENCES yonseitalk.yt_user(user_id);

ALTER TABLE yonseitalk.chatroom
    ADD CONSTRAINT user_2_fk FOREIGN KEY (user_2) REFERENCES yonseitalk.yt_user(user_id);

ALTER TABLE yonseitalk.chatroom
    ADD CONSTRAINT last_send_user_fk FOREIGN KEY (last_send_user) REFERENCES yonseitalk.yt_user(user_id);



# message table ------------------------------------
CREATE TABLE IF NOT EXISTS yonseitalk.message(
    message_id bigint NOT NULL AUTO_INCREMENT UNIQUE,
    chatroom_id bigint NOT NULL,
    sender_id varchar(255) NOT NULL,
    send_time timestamp NOT NULL,
    content varchar(255) NOT NULL,
    rendezvous_location_flag boolean NOT NULL,
    rendezvous_location varchar(255),
    receiver_id varchar(255) NOT NULL,
    rendezvous_time_flag boolean,
    rendezvous_time timestamp,
    read_time timestamp,
    rendezvous_time_location_flag boolean NOT NULL
);

ALTER TABLE yonseitalk.message
    ADD CONSTRAINT message_pk PRIMARY KEY (message_id);

ALTER TABLE yonseitalk.message
    ADD CONSTRAINT message_location_fk FOREIGN KEY (message_location) REFERENCES yonseitalk.location(name);

ALTER TABLE yonseitalk.message
    ADD CONSTRAINT sender_id_fk FOREIGN KEY (sender_id) REFERENCES yonseitalk.yt_user(user_id);

ALTER TABLE yonseitalk.message
    ADD CONSTRAINT receiver_id_fk FOREIGN KEY (receiver_id) REFERENCES yonseitalk.yt_user(user_id);

ALTER TABLE yonseitalk.message
    ADD CONSTRAINT chatroom_id_fk FOREIGN KEY (chatroom_id) REFERENCES yonseitalk.chatroom(chatroom_id);

ALTER TABLE yonseitalk.message
    ALTER rendezvous_location_flag SET DEFAULT false;

ALTER TABLE yonseitalk.message
    ALTER rendezvous_location SET DEFAULT NULL;

ALTER TABLE yonseitalk.message
    ALTER rendezvous_time_flag SET DEFAULT false;

ALTER TABLE yonseitalk.message
    ALTER rendezvous_time SET DEFAULT 0;

ALTER TABLE yonseitalk.message
    ALTER read_time SET DEFAULT 0;

ALTER TABLE yonseitalk.message
    ALTER rendezvous_time_location_flag SET DEFAULT false;
