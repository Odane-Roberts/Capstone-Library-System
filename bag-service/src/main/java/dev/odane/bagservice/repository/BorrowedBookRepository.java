package dev.odane.bagservice.repository;


import dev.odane.bagservice.model.Book;
import dev.odane.bagservice.model.BorrowedBook;
import dev.odane.bagservice.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, UUID> {

    List<BorrowedBook> findBorrowedBookByMember(Member member);

    @Query("""
            SELECT b.title, COUNT(bt.id) AS borrow_count
            FROM Book b
            JOIN BorrowedBook bt ON b.id = bt.book.id
            GROUP BY b.title
            ORDER BY borrow_count DESC
            """)
    List<Object[]> findMostBorrowedBook();

    @Query(value = "SELECT m.name, m.email, SUM(CASE " +
            "WHEN bb.dateReturned > bb.dueDate " +
            "THEN CAST(EXTRACT(DAY FROM (bb.dateReturned - bb.dueDate) * interval '1 day') AS INTEGER) " +
            "ELSE 0 " +
            "END) AS days_outstanding " +
            "FROM borrowed_book bb JOIN borrower m ON bb.id = m.borrowerid " +
            "GROUP BY m.name, m.email", nativeQuery = true)
    List<Object[]> findMembersWhoOwe();

    Optional<BorrowedBook> findBorrowedBookByBook(Book book);
}
