CREATE SCHEMA IF NOT EXISTS locationsearchsvc;

USE locationsearchsvc;

CREATE TABLE IF NOT EXISTS place_data_raw (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    keyword VARCHAR(255) NOT NULL,
    create_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_place_data_raw_create_at ON place_data_raw (create_at);

CREATE TABLE IF NOT EXISTS place_data_stat (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    keyword VARCHAR(255) NOT NULL,
    count BIGINT NOT NULL DEFAULT 0,
    create_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE place_data_stat
    ADD CONSTRAINT uq_place_data_stat_keyword UNIQUE (keyword);
CREATE INDEX idx_place_data_stat_count ON place_data_stat (count);
CREATE INDEX idx_place_data_stat_create_at ON place_data_stat (create_at);
