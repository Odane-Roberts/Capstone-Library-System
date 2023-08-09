package dev.odane.capstoneproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BookNotFoundBookException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundBookException bookNotFoundException){
        return new ResponseEntity<>(bookNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFoundException(MemberNotFoundException memberNotFoundException){
        return new ResponseEntity<>(memberNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<String> handleBookNotAvailableException(BookNotAvailableException bookNotAvailableException){
        return new ResponseEntity<>(bookNotAvailableException.getMessage(), HttpStatus.NOT_FOUND);
    }

}
