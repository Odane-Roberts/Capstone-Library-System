package dev.odane.bookservice.service;

import dev.odane.bookservice.dto.BookDTO;
import dev.odane.bookservice.exception.BookNotFoundBookException;
import dev.odane.bookservice.mapper.BookMapper;
import dev.odane.bookservice.model.Book;
import dev.odane.bookservice.model.Category;
import dev.odane.bookservice.model.Status;
import dev.odane.bookservice.repository.BookRepository;
import dev.odane.bookservice.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    Book bookPojo = Book.builder()
            .id(UUID.randomUUID())
            .title("Book 1")
            .author("John")
            .isbn("234234234234")
            .publicationDate(LocalDateTime.now().minusYears(3))
            .category(Category.FICTION)
            .quantity(5)
            .status(Status.AVAILABLE)
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        bookService = new BookServiceImpl(bookRepository, bookMapper);
    }

    @Test
    void testFindById_ExistingBook() {
        // Arrange
        UUID bookId = UUID.randomUUID();
        Book book = bookPojo;
        book.setId(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        Book result = bookService.findById(bookId);

        // Assert
        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("Book 1", result.getTitle());
        assertEquals("John", result.getAuthor());
        assertEquals(Category.FICTION, result.getCategory());
    }

    @Test
    void testFindById_NonExistentBook() {
        // Arrange
        UUID bookId = UUID.randomUUID();

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BookNotFoundBookException.class, () -> bookService.findById(bookId));
    }

    @Test
    void testAddBook() {
        // Arrange
        Book bookToAdd = bookPojo;

        when(bookRepository.save(bookToAdd)).thenReturn(bookToAdd);

        // Act
        Book result = bookService.addBook(bookToAdd);

        // Assert
        assertNotNull(result);
        assertEquals("Book 1", result.getTitle());
        assertEquals("John", result.getAuthor());
        assertEquals(Category.FICTION, result.getCategory());
    }

    @Test
    void testRemoveBook() {
        // Arrange
        UUID bookId = UUID.randomUUID();
        Book bookToRemove = bookPojo;
        bookToRemove.setId(bookId);

        // Act
        bookService.removeBook(bookToRemove);

        // Assert
        verify(bookRepository, times(1)).delete(bookToRemove);
    }

    @Test
    void testFindAll_ByCategory() {
        // Arrange
        int page = 0;
        int size = 10;
        Category category = Category.FICTION;
        PageRequest pageRequest = PageRequest.of(page, size);

        List<Book> books = new ArrayList<>();
        books.add(bookPojo);
        books.add(bookPojo);

        when(bookRepository.findAllByCategory(category)).thenReturn(books);

        // Act
        Page<BookDTO> result = bookService.findAll(page, size, Optional.of(category), Optional.empty());

        // Assert
        assertNotNull(result);
        assertEquals(books.size(), result.getContent().size());
    }


    @Test
    void testFindAll_ByAuthor() {
        // Arrange
        int page = 0;
        int size = 10;
        String author = "John";
        PageRequest pageRequest = PageRequest.of(page, size);

        List<Book> books = new ArrayList<>();
        books.add(bookPojo);
        books.add(bookPojo);

        when(bookRepository.findAllByAuthor(author)).thenReturn(books);

        // Act
        Page<BookDTO> result = bookService.findAll(page, size, Optional.empty(), Optional.of(author));

        // Assert
        assertNotNull(result);
        assertEquals(books.size(), result.getContent().size());
    }

    @Test
    void testUpdateBook() {
        // Arrange
        UUID bookId = UUID.randomUUID();
        Book bookToUpdate = bookPojo;
        bookToUpdate.setId(bookId);

        when(bookRepository.save(bookToUpdate)).thenReturn(bookToUpdate);

        // Act
        Book result = bookService.updateBook(bookToUpdate);

        // Assert
        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("Book 1", result.getTitle());
        assertEquals("John", result.getAuthor());
        assertEquals(Category.FICTION, result.getCategory());
    }

    @Test
    void testFindByAuthor() {
        // Arrange
        String author = "Test Author";
        List<Book> books = new ArrayList<>();
        books.add(bookPojo);
        books.add(bookPojo);

        when(bookRepository.findAllByAuthor(author)).thenReturn(books);

        // Act
        List<Book> result = bookService.findByAuthor(author);

        // Assert
        assertNotNull(result);
        assertEquals(books.size(), result.size());
    }

}
