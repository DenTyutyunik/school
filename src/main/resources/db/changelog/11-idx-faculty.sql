--liquibase formatted sql
--changeset tyutyunik:idx_faculty

-- Create the index on the name and color of the faculty table
CREATE INDEX idx_faculty_name_color ON faculty (name, color);