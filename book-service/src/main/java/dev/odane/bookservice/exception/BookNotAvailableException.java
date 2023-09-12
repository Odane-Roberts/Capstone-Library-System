package dev.odane.bookservice.exception;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String msg) {
        super(msg);
    }
}
