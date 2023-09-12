package dev.odane.bookservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.odane.bookservice.controller.BookController;
import dev.odane.bookservice.dto.BookDTO;
import dev.odane.bookservice.model.Book;
import dev.odane.bookservice.model.Category;
import dev.odane.bookservice.model.Status;
import dev.odane.bookservice.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;


    private ObjectMapper objectMapper = new ObjectMapper();

    BookDTO book1 = BookDTO.builder()
            .id(UUID.randomUUID())
            .title("Book 1")
            .author("John")
            .status(Status.AVAILABLE)
            .build();
    BookDTO book2 = BookDTO.builder()
            .title("Book 2")
            .author("Joe")
            .status(Status.AVAILABLE)
            .build();
    BookDTO book3 = BookDTO.builder()
            .title("Book 3")
            .author("Job")
            .status(Status.AVAILABLE)
            .build();
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
        mockMvc = MockMvcBuilders.standaloneSetup(new BookController(bookService)).build();
    }

    @Test
    void testGetBooks() throws Exception {
        // Arrange
        int page = 0;
        int size = 10;

        List<BookDTO> bookList = new ArrayList<>();
        bookList.add(book1);

        Page<BookDTO> bookPage = new PageImpl<>(bookList, Pageable.unpaged(), bookList.size());

        when(bookService.findAll(page, size, Optional.empty(), Optional.empty())).thenReturn(bookPage);

        // Act and Assert
        mockMvc.perform(get("/api/v1/book")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        // Add more assertions for the response body if needed
    }

    @Test
    void testGetBookById() throws Exception {
        // Arrange
        UUID bookId = UUID.randomUUID();
        Book book = bookPojo;

        when(bookService.findById(bookId)).thenReturn(book);

        // Act and Assert
        mockMvc.perform(get("/api/v1/book/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        // Add more assertions for the response body if needed
    }

    @Test
    void testAddBook() throws Exception {
        // Arrange
        Book bookToAdd = bookPojo;

        when(bookService.addBook(any(Book.class))).thenReturn(bookToAdd);

        // Act and Assert
        mockMvc.perform(post("/api/v1/book")
                        .content(objectMapper.writeValueAsString(bookToAdd))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        // Add more assertions for the response body if needed
    }

    @Test
    void testDeleteBook() throws Exception {
        // Arrange
        Book bookToDelete = bookPojo;

        when(bookService.removeBook(bookToDelete)).thenReturn(bookToDelete);

        // Act and Assert
        mockMvc.perform(delete("/api/v1/book")
                        .content(objectMapper.writeValueAsString(bookToDelete))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateBook() throws Exception {
        // Arrange
        Book bookToUpdate =  bookPojo;
        when(bookService.updateBook(any(Book.class))).thenReturn(bookToUpdate);

        // Act and Assert
        mockMvc.perform(put("/api/v1/book")
                        .content(objectMapper.writeValueAsString(bookToUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        // Add more assertions for the response body if needed
    }
}
