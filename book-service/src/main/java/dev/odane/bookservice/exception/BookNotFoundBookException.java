package dev.odane.bookservice.exception;


public class BookNotFoundBookException extends RuntimeException {
    public BookNotFoundBookException(String message) {
        super(message);
    }
}
