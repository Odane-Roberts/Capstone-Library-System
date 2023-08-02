-- Create the Book table
CREATE TABLE Book (
                      id SERIAL PRIMARY KEY, -- use big serial along with IDENTITY
                      title VARCHAR(255) NOT NULL,
                      author VARCHAR(255) NOT NULL,
                      isbn VARCHAR(255) NOT NULL,
                      publicationDate DATE NOT NULL,
                      category VARCHAR(20) NOT NULL CHECK ( category IN ('FICTION', 'NONFICTION') ),
                      quantity INT NOT NULL,
                      status VARCHAR(10) NOT NULL CHECK (status IN ('AVAILABLE', 'BORROWED')) -- Borrowed or unavailable
);
CREATE SEQUENCE book_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE book_seq
    CACHE 50;




-- Create the Borrower table
CREATE TABLE Borrower (
                          borrowerId SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          gender VARCHAR(25) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          phone VARCHAR(255) NOT NULL
);

-- CREATE TABLE Author (
--     id VARCHAR(255) PRIMARY KEY,
--     name VARCHAR(255) NOT NULL
-- );

-- Create the BorrowedBook table to represent the many-to-many relationship between Book and Borrower
CREATE TABLE Borrowed_Book (
                            borrowerId SERIAL,
                            id SERIAL NOT NULL,
                            dateBorrowed DATE,
                            dueDate DATE,
                            dateReturned DATE,
                            PRIMARY KEY (borrowerId, id),
                            FOREIGN KEY (borrowerId) REFERENCES Borrower(borrowerId),
                            FOREIGN KEY (id) REFERENCES Book(id)
);

-- Create the BookReview table
CREATE TABLE BookReview (
                            reviewId INTEGER PRIMARY KEY,
                            borrowerId SERIAL,
                            id SERIAL,
                            comment TEXT,
                            FOREIGN KEY (borrowerId) REFERENCES Borrower(borrowerId),
                            FOREIGN KEY (id) REFERENCES Book(id)
);

-- Create the ReturnBook table to represent the many-to-many relationship between Book and Borrower
CREATE TABLE ReturnBook (
                            borrowerId SERIAL,
                            id SERIAL,
                            dateBorrowed DATE,
                            dueDate DATE,
                            dateReturned DATE,
                            PRIMARY KEY (borrowerId, id),
                            FOREIGN KEY (borrowerId) REFERENCES Borrower(borrowerId),
                            FOREIGN KEY (id) REFERENCES Book(id)
);
