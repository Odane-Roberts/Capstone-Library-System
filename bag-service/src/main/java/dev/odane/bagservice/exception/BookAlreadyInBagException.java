package dev.odane.bagservice.exception;

public class BookAlreadyInBagException extends RuntimeException {
    public BookAlreadyInBagException(String msg) {
        super(msg);
    }
}
