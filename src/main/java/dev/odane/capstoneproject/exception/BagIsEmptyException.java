package dev.odane.capstoneproject.exception;

public class BagIsEmptyException extends RuntimeException {
    public BagIsEmptyException(String msg) {
        super(msg);
    }
}
