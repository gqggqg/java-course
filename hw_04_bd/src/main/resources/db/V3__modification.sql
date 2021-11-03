ALTER TABLE lecturer
ADD data_id int,
ADD FOREIGN KEY (data_id) REFERENCES people_data (id) ON UPDATE CASCADE ON DELETE SET NULL,
DROP COLUMN name,
DROP COLUMN surname,
DROP COLUMN email;

ALTER TABLE student
ADD data_id int,
ADD FOREIGN KEY (data_id) REFERENCES people_data (id) ON UPDATE CASCADE ON DELETE SET NULL,
DROP COLUMN name,
DROP COLUMN surname,
DROP COLUMN email;

ALTER TABLE course
ALTER description SET DEFAULT 'Description';