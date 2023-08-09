package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.BookBagDTO;
import dev.odane.capstoneproject.model.Book;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface BagService {
    Book addToBag(BookBagDTO book, HttpSession session);
    Book removeFromBag(Book book, HttpSession session);
    List<Book> viewBag(long id, HttpSession session);

    String borrowBooks(long id, HttpSession session);

}
