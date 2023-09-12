package dev.odane.bagservice.exception;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String msg) {
        super(msg);
    }
}
