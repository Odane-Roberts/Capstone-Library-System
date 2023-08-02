alter table Borrower
    add column status VARCHAR(10) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')) default 'ACTIVE';


alter table Borrower
    alter column status drop default;