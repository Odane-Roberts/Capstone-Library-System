


-- V1.1__add_role_column_to_borrower.sql

-- Add the "role" column to the "borrower" table
ALTER TABLE borrower
    ADD COLUMN role VARCHAR(50);