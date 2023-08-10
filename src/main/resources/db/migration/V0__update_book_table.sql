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
                          phone VARCHAR(255) NOT NULL,
                          status VARCHAR(10) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE'))
);

-- CREATE TABLE Author (
--     id VARCHAR(255) PRIMARY KEY,
--     name VARCHAR(255) NOT NULL
-- );

-- Create the BorrowedBook table to represent the many-to-many relationship between Book and Borrower
CREATE TABLE Borrowed_Book (
                            id SERIAL PRIMARY KEY,
                            borrowerId INTEGER NOT NULL ,
                            book_id INTEGER NOT NULL,
                            dateBorrowed DATE,
                            dueDate DATE,
                            dateReturned DATE,
                            FOREIGN KEY (borrowerId) REFERENCES Borrower(borrowerId),
                            FOREIGN KEY (book_id) REFERENCES Book(id)
);

-- Create the BookReview table
CREATE TABLE BookReview (
                            reviewId SERIAL PRIMARY KEY,
                            borrowerId INTEGER NOT NULL,
                            id INTEGER NOT NULL,
                            comment TEXT,
                            FOREIGN KEY (borrowerId) REFERENCES Borrower(borrowerId),
                            FOREIGN KEY (id) REFERENCES Book(id)
);

-- Create the ReturnBook table to represent the many-to-many relationship between Book and Borrower
CREATE TABLE ReturnBook (
                            borrowerId INTEGER NOT NULL,
                            id INTEGER NOT NULL,
                            dateBorrowed DATE,
                            dueDate DATE,
                            dateReturned DATE,
                            PRIMARY KEY (borrowerId, id),
                            FOREIGN KEY (borrowerId) REFERENCES Borrower(borrowerId),
                            FOREIGN KEY (id) REFERENCES Book(id)
);
CREATE SEQUENCE borrowed_book_seq START 1;