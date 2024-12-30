CREATE TABLE Person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    age INTEGER,
    has_driverlicense BOOLEAN
);

CREATE TABLE Car (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    price DECIMAL
);

CREATE TABLE Users (
    person_id INTEGER REFERENCES Person(id),
    car_id INTEGER REFERENCES Car(id),
    PRIMARY KEY (person_id, car_id)
);