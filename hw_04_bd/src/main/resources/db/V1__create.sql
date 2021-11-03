CREATE TABLE lecturer
(
    id serial NOT NULL,
    name VARCHAR(64) NOT NULL,
    surname VARCHAR(64) NOT NULL,
    email VARCHAR(128) NOT NULL,
    CONSTRAINT lecturer_pk PRIMARY KEY (id)
);

CREATE TABLE student
(
    id serial NOT NULL,
    name VARCHAR(64) NOT NULL,
    surname VARCHAR(64) NOT NULL,
    email VARCHAR(128) NOT NULL,
    CONSTRAINT student_pk PRIMARY KEY (id)
);

CREATE TABLE course
(
    id serial NOT NULL,
    title VARCHAR(128) NOT NULL,
    description text,
    lecturer_id INT REFERENCES lecturer (id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT course_pk PRIMARY KEY (id)
);

CREATE TABLE course_student
(
    course_id INT NOT NULL REFERENCES course (id) ON UPDATE CASCADE ON DELETE CASCADE,
    student_id INT NOT NULL REFERENCES student (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT course_student_pk PRIMARY KEY (course_id, student_id)
);

CREATE TABLE date_time
(
     id serial NOT NULl,
     "date" date NOT NULL,
     "time" time NOT NULL,
     CONSTRAINT date_time_pk PRIMARY KEY (id)
);

CREATE TABLE schedule
(
    course_id INT NOT NULL REFERENCES course (id) ON UPDATE CASCADE ON DELETE CASCADE,
    date_time_id INT REFERENCES date_time (id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT schedule_pk PRIMARY KEY (course_id, date_time_id)
);

CREATE TABLE progress
(
    schedule_id int NOT NULL,
    student_id int NOT NULL,
    attendance bool NOT NULL,
    homework bool NOT NULL,
    CONSTRAINT progress_pk PRIMARY KEY (schedule_id, student_id)
);
