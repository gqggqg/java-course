CREATE TABLE product
(
    id bigserial NOT NULL,
    name VARCHAR NOT NULL,
    code BIGINT NOT NULL,
    CONSTRAINT product_pk PRIMARY KEY (id)
);

CREATE TABLE item
(
    id bigserial NOT NULL,
    price BIGINT NOT NULL,
    product_id INT REFERENCES product (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    quantity BIGINT NOT NULL,
    CONSTRAINT item_pk PRIMARY KEY (id)
);

CREATE TABLE organization
(
    name VARCHAR NOT NULL,
    INN BIGINT NOT NULL UNIQUE,
    account BIGINT NOT NULL,
    CONSTRAINT organization_pk PRIMARY KEY (INN)
);

CREATE TABLE waybill
(
    number BIGINT NOT NULL UNIQUE,
    "date" DATE NOT NULL,
    organization_id BIGINT REFERENCES organization (INN) ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT waybill_pk PRIMARY KEY (number)
);

CREATE TABLE waybill_item
(
    id bigserial NOT NULL,
    waybill_id BIGINT REFERENCES waybill (number) ON UPDATE CASCADE ON DELETE CASCADE,
    item_id INT REFERENCES item (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT waybill_item_pk PRIMARY KEY (id)
);
