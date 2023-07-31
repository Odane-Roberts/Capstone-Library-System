package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.Category;

import java.util.List;


public interface BookService {
    Book findById(Long id);

    Book addBook(Book book);

    Book removeBook(Book book);

    List<Book> findAll();

    List<Book> findByCategory(Category category);
    Book updateBook(Book book);
//    Book findByAuthor(String author);


}