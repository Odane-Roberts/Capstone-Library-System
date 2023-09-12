package dev.odane.bookservice.service;

import dev.odane.bookservice.dto.BookDTO;
import dev.odane.bookservice.exception.BookNotFoundBookException;
import dev.odane.bookservice.mapper.BookMapper;
import dev.odane.bookservice.model.Book;
import dev.odane.bookservice.model.Category;
import dev.odane.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Page<BookDTO> findAll(int page,int size,Optional<Category> category, Optional<String> author) {

        PageRequest pageRequest = PageRequest.of(page, size);
        List<BookDTO> bookDTOs ;

        logger.info("Finding books with category: {} and author: {}", category, author);

        bookDTOs = category.map(value -> repository.findAllByCategory(value).stream().map(mapper::bookToBookDTO)
                .toList()).orElseGet(() -> author.map(s -> repository.findAllByAuthor(s).stream().map(mapper::bookToBookDTO)
                .toList()).orElseGet(() -> repository.findAll().stream().map(mapper::bookToBookDTO)
                .toList()));

       return new PageImpl<>(bookDTOs,pageRequest, bookDTOs.size());
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
