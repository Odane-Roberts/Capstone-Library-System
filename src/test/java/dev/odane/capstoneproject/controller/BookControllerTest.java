package dev.odane.capstoneproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import dev.odane.capstoneproject.DTOs.BookDTO;
import dev.odane.capstoneproject.mapper.BookMapper;
import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.Category;
import dev.odane.capstoneproject.model.Status;
import dev.odane.capstoneproject.repository.BookRepository;
import dev.odane.capstoneproject.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    Book updateBook;
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    ObjectMapper objectMapper = new ObjectMapper(); // to convert object to string content
    ObjectWriter objectWriter = objectMapper.writer(); // can be implemented better


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

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

    @Test
    void getBooks() throws Exception { // create a test to test the parameters
        //given that
        List<BookDTO> books = new ArrayList<>(List.of(book1, book2, book3));
        //when
        Mockito.when(bookService.findAll(Optional.empty(),
                Optional.empty())).thenReturn(books);

        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", is("Book 1")));


    }

    @Test
    void getBookById() throws Exception {
        // given

        Book book = bookPojo;

        UUID id = UUID.randomUUID();
        //when
        Mockito.when(bookService.findById(id)).thenReturn(book);


        // then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/book/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("Book 1")));
    }

    @Test
    void addBooks() throws Exception {
        //given
        Book newBook = bookPojo;

        //when
        Mockito.when(bookService.addBook(newBook)).thenReturn(newBook);

        //performing some utility methods
        objectMapper.findAndRegisterModules(); // update the module for object mapper to use local time and date
        String content = objectWriter.writeValueAsString(newBook); // convert object to json/string


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());

    }

    @Test
    void deleteBook() throws Exception {
        //given
        Book deletedBook = bookPojo;

        //when
        Mockito.when(bookService.removeBook(deletedBook)).thenReturn(deletedBook);

        String content = objectWriter.writeValueAsString(deletedBook);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());

    }

    @Test
    void updateBook() throws Exception {
        Book updatedBook = bookPojo;

        //when
        Mockito.when(bookService.updateBook(updatedBook)).thenReturn(updatedBook);

        objectMapper.findAndRegisterModules();
        String content = objectWriter.writeValueAsString(updatedBook);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isAccepted());
    }
}