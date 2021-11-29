CREATE TABLE IF NOT EXISTS security.user
(
    id INT NOT NULL UNIQUE PRIMARY KEY,
    username VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS security.role
(
    id INT NOT NULL UNIQUE PRIMARY KEY,
    role VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS security.user_role
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    CONSTRAINT user_role_pk PRIMARY KEY (user_id, role_id)
);
