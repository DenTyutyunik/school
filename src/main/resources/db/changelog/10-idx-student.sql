--liquibase formatted sql
--changeset tyutyunik:idx_student

-- Create the index on the name of the student table
CREATE INDEX idx_student_name ON student (name);