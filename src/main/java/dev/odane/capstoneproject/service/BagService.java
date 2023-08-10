package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.BookBagDTO;
import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.BorrowedBook;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface BagService {
    Book addToBag(BookBagDTO book, HttpSession session);
    Book removeFromBag(Book book, HttpSession session);
    List<Book> viewBag(HttpSession session);

    String borrowBooks(long id, HttpSession session);
    String returnBorrowedBook(Book book);
    String emptyBag(HttpSession session);

    String reserveBook(BorrowedBook book);
}
