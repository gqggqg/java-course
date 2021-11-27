CREATE TABLE IF NOT EXISTS security.users
(
    id serial PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS security.roles
(
    id serial PRIMARY KEY,
    role VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS security.user_roles
(
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT user_roles_pk PRIMARY KEY (user_id, role_id)
);
