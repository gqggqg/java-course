CREATE TABLE product
(
    id serial NOT NULL,
    name VARCHAR NOT NULL,
    manufacturer VARCHAR NOT NULL,
    quantity BIGINT NOT NULL,
    CONSTRAINT product_pk PRIMARY KEY (id)
);