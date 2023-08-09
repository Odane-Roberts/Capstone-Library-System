package dev.odane.capstoneproject.controller;

import dev.odane.capstoneproject.DTOs.BookDTO;
import dev.odane.capstoneproject.mapper.BookMapper;
import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.Category;
import dev.odane.capstoneproject.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;

    }

    @GetMapping
    public List<BookDTO> getBooks(@RequestParam Optional<Category> category,
                                  @RequestParam Optional<String> author) {
       return service.findAll(category, author);// parameters for search
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Book addBooks(@RequestBody Book book) {
        return service.addBook(book);
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void deleteBook(@RequestBody Book book){
        service.removeBook(book);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    public Book updateBook(@RequestBody Book book){
        return service.updateBook(book);
    }

}
