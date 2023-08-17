package dev.odane.capstoneproject.controller;

import dev.odane.capstoneproject.DTOs.BookBagDTO;
import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.service.BagService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/bag")
public class BagController {
    private final BagService service;

    public BagController(BagService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Book addToBag(@RequestBody BookBagDTO book, HttpSession session) {
        return service.addToBag(book, session);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Book> viewBag( HttpSession session) {
        return service.viewBag(session);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public Book removeFromBag(@RequestBody Book book, HttpSession session){
        return service.removeFromBag(book, session);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/empty")
    public String emptyBag(HttpSession session){
        return service.emptyBag(session);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/borrow")
    public String borrowBooks(@PathVariable Long id, HttpSession session) {
        return service.borrowBooks(id, session);
    }


    @PutMapping("/reserve")
    public String reserveBook(@RequestBody Book book){
        return service.reserveBook(book);
    }
}

