CREATE TABLE people_data
(
    id serial NOT NULL,
    name VARCHAR(64),
    surname VARCHAR(64),
    email VARCHAR(128),
    CONSTRAINT people_data_pk PRIMARY KEY (id)
);

CREATE TABLE test
(
    id serial NOT NULL,
    people_data_id INT NOT NULL REFERENCES people_data (id) ON UPDATE CASCADE ON DELETE CASCADE
)