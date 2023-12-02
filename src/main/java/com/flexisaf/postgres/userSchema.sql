CREATE TABLE Users (
    userId SERIAL PRIMARY KEY,
    fullname VARCHAR(400) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    DOB DATE,
    userType VARCHAR(20) CHECK (userType IN ('admin', 'owner', 'customer')) NOT NULL,
    hashedPassword VARCHAR(60) CHECK (length(hashedPassword) >= 8) NOT NULL
);