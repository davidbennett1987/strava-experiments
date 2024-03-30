CREATE TABLE IF NOT EXISTS activity (
    id BIGINT NOT NULL,
    name VARCHAR(500) NOT NULL,
    sport_type VARCHAR(20) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    elapsed_time INT NOT NULL,
    moving_time INT NOT NULL,
    start_latitude DOUBLE PRECISION,
    start_longitude DOUBLE PRECISION,
    end_latitude DOUBLE PRECISION,
    end_longitude DOUBLE PRECISION,
    distance FLOAT,
    version INT,
    PRIMARY KEY (id)
);