-- Create the Book table
CREATE TABLE Book (
                      id VARCHAR(255) PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      author VARCHAR(255) NOT NULL,
                      isbn VARCHAR(255) NOT NULL,
                      publicationDate DATE NOT NULL,
                      quantity INT NOT NULL,
                      status VARCHAR(10) NOT NULL CHECK (status IN ('available', 'borrowed'))
);

-- Create the Borrower table
CREATE TABLE Borrower (
                          borrowerId VARCHAR(255) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          phone VARCHAR(255) NOT NULL
);

-- Create the BorrowedBook table to represent the many-to-many relationship between Book and Borrower
CREATE TABLE BorrowBook (
                            borrowerId VARCHAR(255),
                            id VARCHAR(255),
                            dateBorrowed DATE,
                            dateToBeReturned DATE,
                            PRIMARY KEY (borrowerId, id),
                            FOREIGN KEY (borrowerId) REFERENCES Borrower(borrowerId),
                            FOREIGN KEY (id) REFERENCES Book(id)
);

-- Create the BookReview table
CREATE TABLE BookReview (
                            reviewId SERIAL PRIMARY KEY,
                            borrowerId VARCHAR(255),
                            id VARCHAR(255),
                            rating DECIMAL(3, 2) NOT NULL,
                            comment TEXT,
                            FOREIGN KEY (borrowerId) REFERENCES Borrower(borrowerId),
                            FOREIGN KEY (id) REFERENCES Book(id)
);

-- Create the ReturnBook table to represent the many-to-many relationship between Book and Borrower
CREATE TABLE ReturnBook (
                            borrowerId VARCHAR(255),
                            id VARCHAR(255),
                            dateBorrowed DATE,
                            dateToBeReturned DATE,
                            PRIMARY KEY (borrowerId, id),
                            FOREIGN KEY (borrowerId) REFERENCES Borrower(borrowerId),
                            FOREIGN KEY (id) REFERENCES Book(id)
);
