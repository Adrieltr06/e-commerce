-- V1__Create_Account_Table.sql
CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    balance DOUBLE PRECISION DEFAULT 0.0,
    is_admin BOOLEAN DEFAULT FALSE
)