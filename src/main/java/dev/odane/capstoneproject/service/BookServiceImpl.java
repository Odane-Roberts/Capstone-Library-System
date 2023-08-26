package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.BookDTO;
import dev.odane.capstoneproject.exception.BookNotFoundBookException;
import dev.odane.capstoneproject.mapper.BookMapper;
import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.Category;
import dev.odane.capstoneproject.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository repository;
    private final BookMapper mapper;

    @Override
    public Book findById(UUID id) {
        logger.info("Finding book by ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Book not found: {}", id);
                    return new BookNotFoundBookException("Book not found " + id);
                });
    }

    @Override
    public Book addBook(Book book) {
        logger.info("Adding book: {}", book.getTitle());
        return repository.save(book);
    }

    @Override
    public Book removeBook(Book book) {
        logger.info("Removing book: {}", book.getTitle());
        repository.delete(book);
        return book;
    }

    @Override
    public List<BookDTO> findAll(Optional<Category> category, Optional<String> author) {
        logger.info("Finding books with category: {} and author: {}", category, author);
        if (category.isPresent()) {
            return repository.findAllByCategory(category.get()).stream().map(mapper::bookToBookDTO)
                    .toList();
        } else if (author.isPresent()) {
            return repository.findAllByAuthor(author.get()).stream().map(mapper::bookToBookDTO)
                    .toList();
        }
        return repository.findAll().stream().map(mapper::bookToBookDTO)
                .toList();
    }

    @Override
    public List<Book> findByCategory(Category category) {
        logger.info("Finding books by category: {}", category);
        return repository.findAllByCategory(category);
    }

    @Override
    public Book updateBook(Book book) {
        logger.info("Updating book: {}", book.getTitle());
        return repository.save(book);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        logger.info("Finding books by author: {}", author);
        return repository.findAllByAuthor(author);
    }
}
