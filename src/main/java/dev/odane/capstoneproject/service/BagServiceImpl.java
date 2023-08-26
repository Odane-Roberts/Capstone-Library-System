package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.BookBagDTO;
import dev.odane.capstoneproject.exception.*;
import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.BorrowedBook;
import dev.odane.capstoneproject.model.Member;
import dev.odane.capstoneproject.model.Status;
import dev.odane.capstoneproject.repository.BookRepository;
import dev.odane.capstoneproject.repository.BorrowedBookRepository;
import dev.odane.capstoneproject.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class BagServiceImpl implements BagService {
    public static final  String MEMBER_BAG = "BAG";
    private final BookRepository bookRepository;
    private final BorrowedBookRepository borrowedBookRepository;
    private final MemberRepository memberRepository;



    @Override
    public Book addToBag(BookBagDTO bookBagDTO, HttpSession session) {

        if (bookRepository.findById(bookBagDTO
                .getBookId()).orElseThrow( () -> new BookNotFoundBookException("Book is not in the catalog"))
                .getStatus().equals(Status.BORROWED)) {
            log.debug("Book is Reserved or Borrowed");
            throw new BookNotAvailableException("Book not available");
        }

        log.debug("Adding book to bag for book ID: {}", bookBagDTO.getBookId());

        List<Book> bag = getBag(session);

        Book book = bookRepository.findById(bookBagDTO.getBookId())
                .orElseThrow(() -> {
                    log.error("Book not found for ID: {}", bookBagDTO.getBookId());
                    return new BookNotFoundBookException("Book not found");
                });

        if (!bag.contains(book)) {
            ifBookAvailableAddToBag(book, bag);
        } else {
            log.debug("Book already in bag: {}", book.getTitle());
            throw new BookAlreadyInBagException("Book already in bag");
        }
        session.setAttribute(MEMBER_BAG, bag);
        return book;
    }

    @Override
    public String emptyBag(HttpSession session) {
        log.debug("Emptying bag");
        session.removeAttribute(MEMBER_BAG);
        return "Cart emptied";
    }

    @Override
    public String reserveBook(Book book) {
        log.debug("Reserving book with ID: {}", book.getId());

        final Book book1 = bookRepository.findById(book.getId())
                .orElseThrow(() -> {
                    log.error("Book not found for ID: {}", book.getId());
                    return new BookNotFoundBookException("Book not found");
                });

        if (book1.getStatus().equals(Status.AVAILABLE)) {
            return "Book is Available";
        } else if (book1.getStatus().equals(Status.RESERVED)) {
            return "Book is already Reserved by another member";
        } else {
            book1.setStatus(Status.RESERVED);
            return "Book is reserved";
        }
    }

    @Override
    public Book removeFromBag(Book book, HttpSession session) {
        log.debug("Removing book from bag with ID: {}", book.getId());

        List<Book> bag = getBag(session);
        bag.remove(book);
        session.setAttribute(MEMBER_BAG, bag);
        return book;
    }

    @Override
    public List<Book> viewBag(HttpSession session) {
        log.debug("Viewing bag contents");
        return getBag(session);
    }

    @Override
    public String borrowBooks(UUID id, HttpSession session) {
        log.debug("Borrowing books for member ID: {}", id);

        final Member member = memberRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Member not found for ID: {}", id);
                    return new MemberNotFoundException("Member " + id + " not found");
                });

        List<Book> bag = getBag(session);
        if (bag.isEmpty()) {
            log.error("Book bag is empty, can't borrow book");
            return "Bag is Empty";
        }
        log.info(bag.size() +" books in bag");
        for (Book book : bag) {
            processBorrowedBook(member, book);
            log.debug("Book borrowed: {}", book.getTitle());
        }

        updateCatalog(bag);
        session.setAttribute(MEMBER_BAG, new ArrayList<Book>());

        return "Thanks for using our system";
    }


    /*
    *
    * UTILITY FUNCTIONS
    * */

    private void processBorrowedBook(Member member, Book book) {
        log.debug("Processing borrowed book for member ID: {}, book ID: {}", member.getId(), book.getId());

        BorrowedBook transaction = BorrowedBook.builder()
                .dateBorrowed(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(7))
                .dateReturned(null)
                .member(member)
                .book(book)
                .build();

        borrowedBookRepository.save(transaction);
    }

    private void updateCatalog(List<Book> bag) {
        log.debug("Updating catalog for borrowed books");

        bag.forEach(book -> {
            book.setStatus(Status.BORROWED);
            bookRepository.save(book);
            log.debug("Book status updated to BORROWED for book ID: {}", book.getId());
        });
    }


    // HELPER METHODS
    private static List<Book> getBag(HttpSession session) {
        List<Book> bag = (List<Book>) session.getAttribute(MEMBER_BAG);

        if (bag == null) {
            bag = new ArrayList<>();
            session.setAttribute(MEMBER_BAG, bag);
        }

        return bag;
    }

    private static void ifBookAvailableAddToBag(Book book, List<Book> bag) {
        if (book.getStatus() == Status.AVAILABLE) {
            bag.add(book);
            log.debug("Book added to bag: {}", book.getTitle());
        } else {
            throw new BookNotAvailableException("Not available");
        }
    }
}