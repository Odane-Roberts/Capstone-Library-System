package dev.odane.bookservice.exception;


import dev.odane.bookservice.dto.ExceptionDTO;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BookNotFoundBookException.class)
    public ExceptionDTO handleBookNotFoundException(BookNotFoundBookException bookNotFoundException){
        return ExceptionDTO.builder()
                .message(bookNotFoundException.getMessage())
                .build();
    }



    @ExceptionHandler(BookNotAvailableException.class)
    public ExceptionDTO handleBookNotAvailableException(BookNotAvailableException bookNotAvailableException){
        return ExceptionDTO.builder()
                .message(bookNotAvailableException.getMessage())
                .build();
    }

//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ExceptionDTO handleValidationException(ConstraintViolationException constraintViolationExceptions ) {
//        Map<String, String> errors = new HashMap<>();
//
//        constraintViolationExceptions.getConstraintViolations().forEach(error -> {
//            String field = error.getPropertyPath().toString();
//            String message = error.getMessage();
//
//            errors.put(field, message);
//        });
//
//
//        //Todo refactor ExceptionDTO to include more information
//        return ExceptionDTO.builder()
//                .message("Validation error!: " + errors.toString())
//                .build();
//    }

}
