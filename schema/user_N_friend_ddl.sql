CREATE DATABASE IF NOT EXISTS yonseitalk;

# location table ------------------------------------
CREATE TABLE IF NOT EXISTS yonseitalk.location(
    name varchar(255) NOT NULL UNIQUE
);

ALTER TABLE yonseitalk.location
    ADD CONSTRAINT location_pk PRIMARY KEY (name);

-- # yt_user table -------------------------------------
CREATE TABLE IF NOT EXISTS yonseitalk.yt_user(
    user_id varchar(255) NOT NULL UNIQUE,
    name varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    user_time timestamp NOT NULL,
    status_message varchar(255) NOT NULL,
    type varchar(255) NOT NULL,
    connection_status boolean NOT NULL,
    user_location varchar(255) NOT NULL
    );

ALTER TABLE yonseitalk.yt_user
    ADD CONSTRAINT yt_user_pk PRIMARY KEY (user_id);

ALTER TABLE yonseitalk.yt_user
    ADD CONSTRAINT user_location_fk FOREIGN KEY (user_location) REFERENCES location(name);


-- # friends table ------------------------------------
CREATE TABLE IF NOT EXISTS yonseitalk.friends(
    user_id varchar(255) NOT NULL,
    friend_id varchar(255) NOT NULL
    );

ALTER TABLE yonseitalk.friends
    ADD CONSTRAINT friends_pk foreign key (user_id) REFERENCES yt_user(user_id);

ALTER TABLE yonseitalk.friends
    ADD CONSTRAINT friends_fk FOREIGN KEY (friend_id) REFERENCES yt_user(user_id);

