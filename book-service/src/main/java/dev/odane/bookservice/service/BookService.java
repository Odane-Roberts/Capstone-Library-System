package dev.odane.bookservice.service;



import dev.odane.bookservice.dto.BookDTO;
import dev.odane.bookservice.model.Book;
import dev.odane.bookservice.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface BookService {
    Book findById(UUID id);

    Book addBook(Book book);

    Book removeBook(Book book);

    Page<BookDTO> findAll(int page, int size, Optional<Category> category, Optional<String> author);

    List<Book> findByCategory(Category category);

    Book updateBook(Book book);
    List<Book> findByAuthor(String author);


}