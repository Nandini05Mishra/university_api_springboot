-- University database: schema used by the Spring Boot API
-- Run this in MySQL (Workbench or command line) BEFORE starting the app.
-- Data types are my choice, as the assignment allows. Adjust if your class tables differ.

CREATE DATABASE IF NOT EXISTS university;
USE university;

CREATE TABLE department (
    dept_id    INT PRIMARY KEY,
    dept_name  VARCHAR(100) NOT NULL,
    building   VARCHAR(50),
    budget     DECIMAL(12,2)
);

CREATE TABLE teacher (
    teacher_id   INT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    designation  VARCHAR(50),
    experience   INT,
    salary       DECIMAL(10,2),
    dept_id      INT,
    FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);

CREATE TABLE student (
    student_id      INT PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    city            VARCHAR(50),
    admission_year  INT,
    cgpa            DECIMAL(3,2),
    dept_id         INT,
    FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);

CREATE TABLE course (
    course_id     INT PRIMARY KEY,
    course_title  VARCHAR(100) NOT NULL,
    credits       INT,
    dept_id       INT,
    FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);

CREATE TABLE teaches (
    teacher_id  INT,
    course_id   INT,
    PRIMARY KEY (teacher_id, course_id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id),
    FOREIGN KEY (course_id)  REFERENCES course(course_id)
);

CREATE TABLE enrollment (
    student_id  INT,
    course_id   INT,
    marks       INT,
    attendance  INT,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES student(student_id),
    FOREIGN KEY (course_id)  REFERENCES course(course_id)
);
