package dev.odane.capstoneproject.exception;

public class BookAlreadyInBagException extends RuntimeException {
    public BookAlreadyInBagException(String msg) {
        super(msg);
    }
}
