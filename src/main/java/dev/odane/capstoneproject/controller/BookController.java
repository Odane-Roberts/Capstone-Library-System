package dev.odane.capstoneproject.controller;

import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.Category;
import dev.odane.capstoneproject.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<Book> getBooks(@RequestParam Optional<Category> category,
                               @RequestParam Optional<String> author) {
        if (category.isPresent()){
            return service.findByCategory(category.get());
        } else if(author.isPresent()){
            return service.findByAuthor(author.get());
        }
       return service.findAll();
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
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete")
    public void deleteBook(@RequestBody Book book){
        service.removeBook(book);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/update")
    public Book updateBook(@RequestBody Book book){
        return service.updateBook(book);
    }

}
