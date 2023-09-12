package dev.odane.bagservice.service;


import dev.odane.bagservice.dtos.BookBagDTO;
import dev.odane.bagservice.model.Book;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.UUID;

public interface BagService {
    Book addToBag(BookBagDTO book, HttpSession session);
    Book removeFromBag(Book book, HttpSession session);
    List<Book> viewBag(HttpSession session);
    String borrowBooks(UUID id, HttpSession session);
    String emptyBag(HttpSession session);

    String reserveBook(Book book);
}
