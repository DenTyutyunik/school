CREATE TABLE Student (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    age INT DEFAULT 20 CHECK (age >= 16),
    faculty_id BIGINT REFERENCES Faculty(id)
);

CREATE TABLE Faculty (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    color VARCHAR(20) NOT NULL,
    UNIQUE (name, color)
);