package dev.odane.capstoneproject.exception;

import dev.odane.capstoneproject.model.Book;

public class BookNotFoundBookException extends RuntimeException {
    public BookNotFoundBookException(String message) {
        super(message);
    }
}
