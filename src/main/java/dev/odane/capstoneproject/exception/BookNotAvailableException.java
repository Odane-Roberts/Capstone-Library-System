package dev.odane.capstoneproject.exception;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String msg) {
        super(msg);
    }
}
