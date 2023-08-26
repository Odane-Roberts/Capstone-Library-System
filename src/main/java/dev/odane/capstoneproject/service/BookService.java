package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.BookDTO;
import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface BookService {
    Book findById(UUID id);

    Book addBook(Book book);

    Book removeBook(Book book);

    List<BookDTO> findAll(Optional<Category> category, Optional<String> author);

    List<Book> findByCategory(Category category);

    Book updateBook(Book book);
    List<Book> findByAuthor(String author);


}