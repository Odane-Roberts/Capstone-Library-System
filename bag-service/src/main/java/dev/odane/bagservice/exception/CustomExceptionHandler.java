package dev.odane.bagservice.exception;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(BookAlreadyInBagException.class)
    public ExceptionDTO handleBookAlreadyInBagException(BookAlreadyInBagException bookAlreadyInBagException){
        return ExceptionDTO.builder()
                .message(bookAlreadyInBagException.getMessage())
                .build();
    }

    @ExceptionHandler(BorrowedBookNotFoundException.class)
    public ExceptionDTO handleBorrowedBookNotFoundException(BorrowedBookNotFoundException borrowedBookNotFoundException){
        return  ExceptionDTO.builder()
                .message(borrowedBookNotFoundException.getMessage())
                .build();
    }

    @ExceptionHandler(BagIsEmptyException.class)
    public ExceptionDTO handleBagIsEmptyException(BagIsEmptyException bagIsEmptyException){
        return  ExceptionDTO.builder()
                .message(bagIsEmptyException.getMessage())
                .build();
    }

    @ExceptionHandler(BookNotFoundBookException.class)
    public ExceptionDTO handleBookNotFoundException(BookNotFoundBookException bookNotFoundException){
        return ExceptionDTO.builder()
                .message(bookNotFoundException.getMessage())
                .build();
    }

    @ExceptionHandler(NoFineException.class)
    public ExceptionDTO handleNoFineException(NoFineException noFineException){
        return ExceptionDTO.builder()
                .message(noFineException.getMessage())
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ExceptionDTO handleValidationException(ConstraintViolationException constraintViolationExceptions ) {
        Map<String, String> errors = new HashMap<>();

        constraintViolationExceptions.getConstraintViolations().forEach(error -> {
            String field = error.getPropertyPath().toString();
            String message = error.getMessage();

            errors.put(field, message);
        });


        //Todo refactor ExceptionDTO to include more information
        return ExceptionDTO.builder()
                .message("Validation error!: " + errors.toString())
                .build();
    }

}
