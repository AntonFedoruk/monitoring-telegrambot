-- The type and definition of foreign key field and reference must be equal.

-- remove foreign key and change to INT type
ALTER TABLE station_x_user
    DROP FOREIGN KEY station_x_user_ibfk_1,
    MODIFY user_id INT;

-- change user_id in tg_user table
ALTER TABLE tg_user
    MODIFY chat_id INT;

-- recreate foreign key
ALTER TABLE station_x_user
    ADD CONSTRAINT station_x_user_ibfk_1 FOREIGN KEY (user_id) REFERENCES tg_user (chat_id);