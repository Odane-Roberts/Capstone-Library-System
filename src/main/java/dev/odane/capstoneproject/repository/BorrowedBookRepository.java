package dev.odane.capstoneproject.repository;

import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.BorrowedBook;
import dev.odane.capstoneproject.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    List<BorrowedBook> findBorrowedBookByMember(Member member);

    Optional<BorrowedBook> findBorrowedBookByBook(Book book);
}
