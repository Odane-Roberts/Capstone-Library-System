package dev.odane.bookservice.controller;


import dev.odane.bookservice.dto.BookDTO;
import dev.odane.bookservice.model.Book;
import dev.odane.bookservice.model.Category;
import dev.odane.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService service;

    @GetMapping
    public Page<BookDTO> getBooks(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam Optional<String> author,
                                  @RequestParam Optional<Category> category) {
        logger.info("Get books request received");
        Page<BookDTO> books = service.findAll(page, size, category, author);
        logger.info("Books retrieved");
        return books;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable UUID id) {
        logger.info("Get book by ID request received for ID: {}", id);
        Book book = service.findById(id);
        logger.info("Book retrieved with ID: {}", id);
        return book;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Book addBooks(@RequestBody Book book) {
        logger.info("Add book request received");
        Book addedBook = service.addBook(book);
        logger.info("Book added with ID: {}", book.getId());
        return addedBook;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void deleteBook(@RequestBody Book book) {
        logger.info("Delete book request received");
        service.removeBook(book);
        logger.info("Book deleted with ID: {}", book.getId());
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    public Book updateBook(@RequestBody Book book) {
        logger.info("Update book request received for ID: {}", book.getId());
        Book updatedBook = service.updateBook(book);
        logger.info("Book updated with ID: {}", book.getId());
        return updatedBook;
    }
}
