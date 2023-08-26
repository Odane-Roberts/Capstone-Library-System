-- Create the Book table
CREATE TABLE Book (
                      id UUID PRIMARY KEY, -- use big serial along with IDENTITY
                      title VARCHAR(255) NOT NULL,
                      author VARCHAR(255) NOT NULL,
                      isbn VARCHAR(255) NOT NULL,
                      publicationDate DATE NOT NULL,
                      category VARCHAR(20) NOT NULL CHECK ( category IN ('FICTION', 'NONFICTION') ),
                      quantity INT NOT NULL,
                      status VARCHAR(10) NOT NULL CHECK (status IN ('AVAILABLE', 'BORROWED')) -- Borrowed or unavailable
);

CREATE TABLE ADMIN (
    id UUID NOT NULL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    dob DATE NOT NULL,
    gender VARCHAR(255) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    role VARCHAR(255) NOT NULL

);


CREATE SEQUENCE book_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 50;

ALTER SEQUENCE book_seq
    CACHE 50;




-- Create the Borrower table
CREATE TABLE Borrower (
                          borrowerId UUID NOT NULL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          dob DATE NOT NULL,
                          gender VARCHAR(25) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          phone VARCHAR(255) NOT NULL,
                          status VARCHAR(10) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE'))
);



-- Create the BorrowedBook table to represent the many-to-many relationship between Book and Borrower
CREATE TABLE Borrowed_Book (
                            id UUID NOT NULL PRIMARY KEY,
                            borrowerId UUID NOT NULL ,
                            book_id UUID NOT NULL,
                            dateBorrowed DATE,
                            dueDate DATE,
                            dateReturned DATE,
                            FOREIGN KEY (borrowerId) REFERENCES Borrower(borrowerId),
                            FOREIGN KEY (book_id) REFERENCES Book(id)
);

-- Create the BookReview table
CREATE TABLE BookReview (
                            reviewId UUID PRIMARY KEY,
                            borrowerId UUID NOT NULL,
                            id UUID NOT NULL,
                            comment TEXT,
                            FOREIGN KEY (borrowerId) REFERENCES Borrower(borrowerId),
                            FOREIGN KEY (id) REFERENCES Book(id)
);

-- Create the ReturnBook table to represent the many-to-many relationship between Book and Borrower
CREATE TABLE ReturnBook (
                            borrowerId UUID NOT NULL,
                            id UUID NOT NULL,
                            dateBorrowed DATE,
                            dueDate DATE,
                            dateReturned DATE,
                            PRIMARY KEY (borrowerId, id),
                            FOREIGN KEY (borrowerId) REFERENCES Borrower(borrowerId),
                            FOREIGN KEY (id) REFERENCES Book(id)
);
