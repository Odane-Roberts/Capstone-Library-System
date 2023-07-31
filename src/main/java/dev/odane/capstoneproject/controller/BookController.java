package dev.odane.capstoneproject.controller;

import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<Book> getBooks() {
        return service.findAll();
    }

    @GetMapping("/hello")
    public String getGreeting() {
        return "Hello World";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public Book addBooks(@RequestBody Book book) {
        return service.addBook(book);
    }
}
