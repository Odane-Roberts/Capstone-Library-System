package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.exception.BookNotFoundBookException;
import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.Category;
import dev.odane.capstoneproject.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BookNotFoundBookException("Book not found " + id));
    }

    @Override
    public Book addBook(Book book) {
        return repository.save(book);
    }

    @Override
    public Book removeBook(Book book) {
        repository.delete(book);
        return book;
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Book> findByCategory(Category category) {
        return repository.findAllByCategory(category);
    }

    @Override
    public Book updateBook(Book book) {
        return repository.save(book);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return repository.findAllByAuthor(author);
    }

}
