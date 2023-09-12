package dev.odane.bagservice.exception;

public class BagIsEmptyException extends RuntimeException {


    public BagIsEmptyException(String msg) {
        super(msg);
    }
}
