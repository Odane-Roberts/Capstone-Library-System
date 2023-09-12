package dev.odane.bagservice.controller;


import dev.odane.bagservice.dtos.BookBagDTO;
import dev.odane.bagservice.model.Book;
import dev.odane.bagservice.service.BagService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bag")
public class BagController {
    private static final Logger logger = LoggerFactory.getLogger(BagController.class);

    private final BagService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Book addToBag(@RequestBody BookBagDTO book, HttpSession session) {
        logger.info("Adding book to bag request received");
        Book addedBook = service.addToBag(book, session);
        logger.info("Book added to bag: {}", addedBook.getTitle());
        return addedBook;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/view")
    public List<Book> viewBag(HttpSession session) {
        logger.info("View bag request received");
        List<Book> bagContents = service.viewBag(session);
        logger.info("Bag contents retrieved");
        return bagContents;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public Book removeFromBag(@RequestBody Book book, HttpSession session) {
        logger.info("Removing book from bag request received");
        Book removedBook = service.removeFromBag(book, session);
        logger.info("Book removed from bag: {}", book.getTitle());
        return removedBook;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/empty")
    public String emptyBag(HttpSession session) {
        logger.info("Emptying bag request received");
        String result = service.emptyBag(session);
        logger.info("Bag emptied");
        return result;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/borrow")
    public String borrowBooks(@PathVariable UUID id, HttpSession session) {
        logger.info("Borrow books request received for from member ID: {}", id);
        String result = service.borrowBooks(id, session);
        logger.info("Borrow books operation result: {}", result);
        return result;
    }

    @PutMapping("/reserve")
    public String reserveBook(@RequestBody Book book) {
        logger.info("Reserve book request received");
        String result = service.reserveBook(book);
        logger.info("Reserve book operation result: {}", result);
        return result;
    }
}
