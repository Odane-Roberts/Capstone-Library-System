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
    @GetMapping("/{id}/view")
    public List<Book> viewBag(@PathVariable Long id, HttpSession session) {
        return service.viewBag(id, session);
    }
}
