package dev.odane.capstoneproject.exception;

public class BorrowedBookNotFoundException extends RuntimeException {
    public BorrowedBookNotFoundException(String msg) {
        super(msg);
    }
}
