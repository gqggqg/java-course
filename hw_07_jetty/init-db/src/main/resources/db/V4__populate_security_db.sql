INSERT INTO security.roles(role) VALUES('admin');
INSERT INTO security.roles(role) VALUES('user');

INSERT INTO security.users(name,password) VALUES('admin', 'admin');
INSERT INTO security.users(name,password) VALUES('user', 'user');

INSERT INTO security.user_roles(user_id,role_id) VALUES(1, 1);
INSERT INTO security.user_roles(user_id,role_id) VALUES(2, 2);
