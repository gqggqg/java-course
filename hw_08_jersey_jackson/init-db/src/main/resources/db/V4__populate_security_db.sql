INSERT INTO security.role(id,role) VALUES(1,'manager');
INSERT INTO security.role(id,role) VALUES(2,'guest');

INSERT INTO security.user(id,username,password) VALUES(1,'admin', 'admin');
INSERT INTO security.user(id,username,password) VALUES(2,'user', 'user');

INSERT INTO security.user_role(user_id,role_id) VALUES(1, 1);
INSERT INTO security.user_role(user_id,role_id) VALUES(2, 2);
