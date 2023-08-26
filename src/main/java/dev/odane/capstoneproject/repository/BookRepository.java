package dev.odane.capstoneproject.repository;

import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findAllByCategory(Category category);
    List<Book> findAllByAuthor(String author);


}
