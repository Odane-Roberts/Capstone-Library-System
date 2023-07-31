package dev.odane.capstoneproject.repository;

import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByCategory(Category category);
    List<Book> findAllByAuthor(String author);


}
