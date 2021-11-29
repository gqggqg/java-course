CREATE TABLE IF NOT EXISTS db.product
(
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL,
    manufacturer VARCHAR NOT NULL,
    quantity INT NOT NULL
);
