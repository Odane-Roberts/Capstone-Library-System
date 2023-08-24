package dev.odane.capstoneproject.exception;

import dev.odane.capstoneproject.DTOs.ExceptionDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BookNotFoundBookException.class)
    public ExceptionDTO handleBookNotFoundException(BookNotFoundBookException bookNotFoundException){
        return ExceptionDTO.builder()
                .message(bookNotFoundException.getMessage())
                .build();
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ExceptionDTO handleMemberNotFoundException(MemberNotFoundException memberNotFoundException){
        return ExceptionDTO.builder()
                .message(memberNotFoundException.getMessage())
                .build();
    }

    @ExceptionHandler(BookNotAvailableException.class)
    public ExceptionDTO handleBookNotAvailableException(BookNotAvailableException bookNotAvailableException){
        return ExceptionDTO.builder()
                .message(bookNotAvailableException.getMessage())
                .build();
    }

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
    @ExceptionHandler(NoFineException.class)
    public ExceptionDTO handleNoFineException(NoFineException noFineException){
        return  ExceptionDTO.builder()
                .message(noFineException.getMessage())
                .build();
    }
    @ExceptionHandler(BagIsEmptyException.class)
    public ExceptionDTO handleBagIsEmptyException(BagIsEmptyException bagIsEmptyException){
        return  ExceptionDTO.builder()
                .message(bagIsEmptyException.getMessage())
                .build();
    }

}
