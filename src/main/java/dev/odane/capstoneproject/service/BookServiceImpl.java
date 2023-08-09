package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.BookDTO;
import dev.odane.capstoneproject.exception.BookNotFoundBookException;
import dev.odane.capstoneproject.mapper.BookMapper;
import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.Category;
import dev.odane.capstoneproject.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
private final BookRepository repository;
private final BookMapper mapper;

    public BookServiceImpl(BookRepository repository, BookMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
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
    public List<BookDTO> findAll(Optional<Category> category, Optional<String> author) {
        if (category.isPresent()){
            return repository.findAllByCategory(category.get()).stream().map(mapper::bookToBookDTO)
                    .toList();
        } else if(author.isPresent()){
            return repository.findAllByAuthor(author.get()).stream().map(mapper::bookToBookDTO)
                    .toList();
        }
        return repository.findAll().stream().map(mapper::bookToBookDTO)
                .toList();
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
