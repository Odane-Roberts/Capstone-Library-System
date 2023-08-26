package dev.odane.capstoneproject.exception;

import dev.odane.capstoneproject.DTOs.ExceptionDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomExceptionHandlerTest {

    @InjectMocks
    private CustomExceptionHandler customExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleBookNotFoundException() {
        BookNotFoundBookException exception = new BookNotFoundBookException("Book not found");
        ExceptionDTO response = customExceptionHandler.handleBookNotFoundException(exception);

        assertEquals(exception.getMessage(), response.getMessage());
        assertEquals("Book not found", response.getMessage());
    }

    @Test
    void testHandleMemberNotFoundException() {
        MemberNotFoundException exception = new MemberNotFoundException("Member not found");
        ExceptionDTO response = customExceptionHandler.handleMemberNotFoundException(exception);

        assertEquals(exception.getMessage(), response.getMessage());
        assertEquals("Member not found", response.getMessage());
    }

    @Test
    void testHandleBookNotAvailableException() {
        BookNotAvailableException exception = new BookNotAvailableException("Book not available");
        ExceptionDTO response = customExceptionHandler.handleBookNotAvailableException(exception);

        assertEquals(exception.getMessage(), response.getMessage());
        assertEquals("Book not available", response.getMessage());
    }

    @Test
    void testHandleBookAlreadyInBagException() {
        BookAlreadyInBagException exception = new BookAlreadyInBagException("Book already in bag");
        ExceptionDTO response = customExceptionHandler.handleBookAlreadyInBagException(exception);

        assertEquals(exception.getMessage(), response.getMessage());
        assertEquals("Book already in bag", response.getMessage());
    }

    @Test
    void testHandleBorrowedBookNotFoundException() {
        BorrowedBookNotFoundException exception = new BorrowedBookNotFoundException("Borrowed book not found");
        ExceptionDTO response = customExceptionHandler.handleBorrowedBookNotFoundException(exception);

        assertEquals(exception.getMessage(), response.getMessage());
        assertEquals("Borrowed book not found", response.getMessage());
    }

    @Test
    void testHandleNoFineException() {
        NoFineException exception = new NoFineException("No fine owed");
        ExceptionDTO response = customExceptionHandler.handleNoFineException(exception);

        assertEquals(exception.getMessage(), response.getMessage());
        assertEquals("No fine owed", response.getMessage());
    }

    @Test
    void testHandleBagIsEmptyException() {
        BagIsEmptyException exception = new BagIsEmptyException("Bag is empty");
        ExceptionDTO response = customExceptionHandler.handleBagIsEmptyException(exception);

        assertEquals(exception.getMessage(), response.getMessage());
        assertEquals("Bag is empty", response.getMessage());
    }

    @Test
    void testHandleValidationException() {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        Path propertyPath = mock(Path.class);
        when(violation.getPropertyPath()).thenReturn(propertyPath);
        when(propertyPath.toString()).thenReturn("fieldName");
        when(violation.getMessage()).thenReturn("must not be null");

        Set<ConstraintViolation<?>> violations = Collections.singleton(violation);
        ConstraintViolationException exception = new ConstraintViolationException(violations);

        ExceptionDTO response = customExceptionHandler.handleValidationException(exception);

        assertEquals("Validation error!: {fieldName=must not be null}", response.getMessage());
    }



}
