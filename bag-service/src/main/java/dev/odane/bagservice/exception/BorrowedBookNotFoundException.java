package dev.odane.bagservice.exception;

public class BorrowedBookNotFoundException extends RuntimeException {
    public BorrowedBookNotFoundException(String msg) {
        super(msg);
    }
}
