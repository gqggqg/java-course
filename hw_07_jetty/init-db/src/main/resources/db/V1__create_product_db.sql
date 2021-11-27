CREATE TABLE IF NOT EXISTS db.product
(
    id serial NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL,
    manufacturer VARCHAR NOT NULL,
    quantity BIGINT NOT NULL
);
