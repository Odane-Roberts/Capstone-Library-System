package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.BookDTO;
import dev.odane.capstoneproject.exception.BookNotFoundBookException;
import dev.odane.capstoneproject.mapper.BookMapper;
import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.Category;
import dev.odane.capstoneproject.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {


    private BookRepository repository;
    private BookMapper mapper;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        repository = mock(BookRepository.class);
        mapper = mock(BookMapper.class);
        bookService = new BookServiceImpl(repository, mapper);
    }

    @Test
    void testFindById() {
        UUID bookId = UUID.randomUUID();
        Book book = new Book();
        when(repository.findById(bookId)).thenReturn(Optional.of(book));

        Book result = bookService.findById(bookId);

        assertEquals(book, result);
    }

    @Test
    void testFindByIdNotFound() {
        UUID bookId = UUID.randomUUID();
        when(repository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundBookException.class, () -> bookService.findById(bookId));
    }

    @Test
    void testAddBook() {
        Book book = new Book();
        when(repository.save(book)).thenReturn(book);

        Book result = bookService.addBook(book);

        assertEquals(book, result);
    }

    @Test
    void testRemoveBook() {
        Book book = new Book();

        Book result = bookService.removeBook(book);

        Mockito.verify(repository, times(1)).delete(book);
        assertEquals(book, result);
    }

    @Test
    void testFindAllWithCategory() {
        Category category = Category.FICTION;
        when(repository.findAllByCategory(category)).thenReturn(new ArrayList<>());

        List<BookDTO> result = bookService.findAll(Optional.of(category), Optional.empty());

        assertNotNull(result);
        verify(repository, times(1)).findAllByCategory(category);
        verify(mapper, times(0)).bookToBookDTO(any());
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        when(repository.save(book)).thenReturn(book);

        Book result = bookService.updateBook(book);

        assertEquals(book, result);
    }

    @Test
    void testFindByAuthor() {
        String author = "John Doe";
        when(repository.findAllByAuthor(author)).thenReturn(new ArrayList<>());

        List<Book> result = bookService.findByAuthor(author);

        assertNotNull(result);
        verify(repository, times(1)).findAllByAuthor(author);
    }
}