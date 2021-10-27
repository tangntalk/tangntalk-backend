#DROP TABLE IF EXISTS yonseitalk.chatroom, yonseitalk.message, yonseitalk.friends, yonseitalk.yt_user, yonseitalk.location cascade;


CREATE DATABASE IF NOT EXISTS yonseitalk;

# location table ------------------------------------
CREATE TABLE IF NOT EXISTS location(
    name varchar(255) NOT NULL UNIQUE
);

ALTER TABLE location
    ADD CONSTRAINT location_pk PRIMARY KEY (name);

INSERT IGNORE INTO location VALUES ('공학관');
INSERT IGNORE INTO location VALUES ('백양관');
INSERT IGNORE INTO location VALUES ('학생회관');
INSERT IGNORE INTO location VALUES ('언더우드관');

ALTER TABLE location
    ALTER name SET DEFAULT '공학관';

# yt_user table -------------------------------------
CREATE TABLE IF NOT EXISTS yt_user(
    user_id varchar(255) NOT NULL UNIQUE AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    user_time timestamp NOT NULL,
    status_message varchar(255) NOT NULL,
    type varchar(255) NOT NULL,
    #connection_status boolean NOT NULL,
    user_location varchar(255) NOT NULL
);

ALTER TABLE yt_user
    ADD CONSTRAINT yt_user_pk PRIMARY KEY (user_id);

ALTER TABLE yt_user
    ADD CONSTRAINT user_location_fk FOREIGN KEY (user_location) REFERENCES location(name);


# friends table ------------------------------------
CREATE TABLE IF NOT EXISTS friends(
    user_id varchar(255) NOT NULL,
    friend_id varchar(255) NOT NULL
);

ALTER TABLE friends
    ADD CONSTRAINT friends_pk FOREIGN KEY (user_id) REFERENCES yt_user(user_id);

ALTER TABLE friends
    ADD CONSTRAINT friends_fk FOREIGN KEY (friend_id) REFERENCES yt_user(user_id);

# chatroom table ------------------------------------
CREATE TABLE IF NOT EXISTS chatroom(
    chatroom_id bigint NOT NULL UNIQUE AUTO_INCREMENT,
    user_1 varchar(255) NOT NULL,
    user_2 varchar(255) NOT NULL,
    talk bigint NOT NULL,
    last_send_time timestamp NOT NULL,
    last_send_user varchar(255) NOT NULL
);

ALTER TABLE chatroom
    ADD CONSTRAINT user_1_fk FOREIGN KEY (user_1) REFERENCES yt_user(user_id);

ALTER TABLE chatroom
    ADD CONSTRAINT user_2_fk FOREIGN KEY (user_2) REFERENCES yt_user(user_id);

ALTER TABLE chatroom
    ADD CONSTRAINT last_send_user_fk FOREIGN KEY (last_send_user) REFERENCES yt_user(user_id);



# message table ------------------------------------
CREATE TABLE IF NOT EXISTS message(
    message_id bigint NOT NULL AUTO_INCREMENT UNIQUE,
    chatroom_id bigint NOT NULL,
    sender_id varchar(255) NOT NULL,
    send_time timestamp NOT NULL,
    content varchar(255) NOT NULL,
    message_location varchar(255) NOT NULL,
    receiver_id varchar(255) NOT NULL,
    rendezvous_flag boolean,
    rendezvous_time timestamp,
    read_time timestamp
);

ALTER TABLE message
    ADD CONSTRAINT message_pk PRIMARY KEY (message_id);

ALTER TABLE message
    ADD CONSTRAINT message_location_fk FOREIGN KEY (message_location) REFERENCES location(name);

ALTER TABLE message
    ADD CONSTRAINT sender_id_fk FOREIGN KEY (sender_id) REFERENCES yt_user(user_id);

ALTER TABLE message
    ADD CONSTRAINT receiver_id_fk FOREIGN KEY (receiver_id) REFERENCES yt_user(user_id);

ALTER TABLE message
    ADD CONSTRAINT chatroom_id_fk FOREIGN KEY (chatroom_id) REFERENCES chatroom(chatroom_id);

