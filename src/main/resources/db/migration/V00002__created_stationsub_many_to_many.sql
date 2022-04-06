-- add PRIMARY KEY FOR tg_user
ALTER TABLE tg_user
    ADD PRIMARY KEY (chat_id);

-- ensure that the tables with these names are removed before creating a new one.
DROP TABLE IF EXISTS station_sub;
DROP TABLE IF EXISTS station_x_user;

CREATE TABLE station_sub(
    id             INT,
    title          VARCHAR(50),
    last_update_id INT,
    PRIMARY KEY (id)
);

CREATE TABLE station_x_user(
    station_sub_id INT          NOT NULL,
    user_id           VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES tg_user (chat_id),
    FOREIGN KEY (station_sub_id) REFERENCES station_sub (id),
    UNIQUE (user_id, station_sub_id)
);