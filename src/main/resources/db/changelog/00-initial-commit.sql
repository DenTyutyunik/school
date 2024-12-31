--liquibase formatted sql
--changeset tyutyunik:create-tables-student-faculty-avatar

-- Create the faculty table
CREATE TABLE faculty (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL
);

-- Create the student table
CREATE TABLE student (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    faculty_id BIGINT NOT NULL,
    FOREIGN KEY (faculty_id) REFERENCES faculty(id)
);

-- Create the avatar table
CREATE TABLE avatar (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    file_size BIGINT NOT NULL,
    media_type VARCHAR(255) NOT NULL,
    data BYTEA NOT NULL,
    student_id BIGINT UNIQUE, -- Enforces one-to-one
    FOREIGN KEY (student_id) REFERENCES student(id)
);

-- Initial data (example)
-- INSERT INTO faculty (name, color) VALUES ('Renunciation', 'Grey');
-- INSERT INTO faculty (name, color) VALUES ('Erudition', 'Blue');
-- INSERT INTO faculty (name, color) VALUES ('Fearlessness', 'Red');
-- INSERT INTO faculty (name, color) VALUES ('Friendliness', 'Green');
-- INSERT INTO faculty (name, color) VALUES ('Sincerity', 'White');
-- INSERT INTO student (name, age, faculty_id) VALUES ('Alexandr', 20, (SELECT id FROM faculty WHERE name = 'Renunciation'));
-- INSERT INTO student (name, age, faculty_id) VALUES ('Alex', 20, (SELECT id FROM faculty WHERE name = 'Erudition'));
-- INSERT INTO student (name, age, faculty_id) VALUES ('Iskander', 20, (SELECT id FROM faculty WHERE name = 'Fearlessness'));
-- INSERT INTO student (name, age, faculty_id) VALUES ('Sasha', 20, (SELECT id FROM faculty WHERE name = 'Friendliness'));
-- INSERT INTO student (name, age, faculty_id) VALUES ('Alexandro', 20, (SELECT id FROM faculty WHERE name = 'Sincerity'));