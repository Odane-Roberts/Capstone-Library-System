package dev.odane.capstoneproject.repository;

import dev.odane.capstoneproject.model.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
}
