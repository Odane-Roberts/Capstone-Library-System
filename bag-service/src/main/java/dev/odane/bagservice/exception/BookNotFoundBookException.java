package dev.odane.bagservice.exception;


public class BookNotFoundBookException extends RuntimeException {
    public BookNotFoundBookException(String message) {
        super(message);
    }
}
