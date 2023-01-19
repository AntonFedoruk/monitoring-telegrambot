-- Modify station_sub column last_update_id INT to last_status ENUM()
# ALTER TABLE station_sub
#     CHANGE last_update_id last_status
#         ENUM('ONLINE', 'OFFLINE', 'CHARGING', 'ERROR') NOT NULL;

ALTER TABLE station_sub
    DROP COLUMN last_update_id;
ALTER TABLE  station_sub
    ADD last_status
        ENUM('ONLINE', 'OFFLINE', 'CHARGING', 'ERROR') NOT NULL;