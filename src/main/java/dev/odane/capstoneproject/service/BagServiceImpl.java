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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;




@Service
@Slf4j
public class BagServiceImpl implements BagService {
    private static final  String MEMBER_BAG = "BAG";
    private final BookRepository bookRepository;
    private final BorrowedBookRepository borrowedBookRepository;
    private final MemberRepository memberRepository;

    public BagServiceImpl(BookRepository bookRepository, BorrowedBookRepository borrowedBookRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.borrowedBookRepository = borrowedBookRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Book addToBag(BookBagDTO bookBagDTO, HttpSession session) {
        List<Book> bag = getBag(session);

        Book book = bookRepository.findById(bookBagDTO.getBookId()).orElseThrow(() -> new BookNotFoundBookException("Book not found")); // change name later and fix optional warning
        if(!bag.contains(book)) {
            ifBookAvailableAddToBag(book, bag); // preliminary check` to only add available books to bag
        } else {
            throw new BookAlreadyInBagException("Book already in bag "); // change to custom exception later
        }
        session.setAttribute(MEMBER_BAG, bag);
        return book;
    }

    @Override
    public String emptyBag(HttpSession session) {
        session.setAttribute(MEMBER_BAG,new ArrayList<Book>());
        return "Cart emptied";
    }

    @Override
    public String reserveBook(BorrowedBook book) {
        final Book book1 = bookRepository.findById(book.getBook().getId())
                .orElseThrow(() -> new BookNotFoundBookException(" Book not found"));// change name later and fix optional warning
        if(book1.getStatus().equals(Status.AVAILABLE)) {
            return "Book is Available";
        } else if (book1.getStatus().equals(Status.RESERVED)){
            return "Book is already Reserved by another member";
        } else {
            book1.setStatus(Status.RESERVED);
            return "Book is reserved";
        }
    }

    @Override
    public Book removeFromBag(Book book, HttpSession session) {
        List<Book> bag = getBag(session);
        bag.remove(book);
        session.setAttribute(MEMBER_BAG,bag);
        return book;
    }

    @Override
    public List<Book> viewBag(HttpSession session) {
        return getBag(session);
    }
    
    @Override
    public String borrowBooks(long id, HttpSession session) {
        final Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member " + id + " not found"));
        List<Book> bag = getBag(session);
        for(Book book : bag) {
            processBorrowedBook(member, book); //create different borrowed book transaction for each book within the bag
        }
        updateCatalog(bag); // updating catalog
        session.setAttribute(MEMBER_BAG,new ArrayList<Book>()); // empty bag after borrowing books

        return "Thanks for using our system";
    }

    @Override
    public String returnBorrowedBook(Book book) {
        borrowedBookRepository.findBorrowedBookByBook(book)
                .orElseThrow(() -> new BorrowedBookNotFoundException("Borrowed book not found"))
                .setDateReturned(LocalDateTime.now());

        return "Thanks for returning the book";
    }


    /*
    *
    * UTILITY FUNCTIONS
    * */
    private void processBorrowedBook(Member member, Book book) {
        log.debug(String.valueOf(member.getId()));
        BorrowedBook transaction = BorrowedBook.builder()
                .dateBorrowed(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(7))
                .dateReturned(null)
                .member(member)
                .book(book)
                .build();

        borrowedBookRepository.save(transaction);
        addToBorrowedList(member.getId(), transaction);
    }

    private void updateCatalog(List<Book> bag) { // set all the books in the transaction to borrowed
        bag.forEach(book -> book.setStatus(Status.BORROWED));
    }

    private void addToBorrowedList(long id, BorrowedBook transaction) { // add list of borrowed books to member
        memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException("Member " + id + " not found"))
                .getBorrowedBooks().add(transaction);
    }

    // HELPER METHODS
    private static List<Book> getBag(HttpSession session) { // helper method to fetch bag from session
        return (session.getAttribute(MEMBER_BAG) == null) ? new ArrayList<>() :
                (List<Book>) session.getAttribute(MEMBER_BAG);
    }
    private static void ifBookAvailableAddToBag(Book book, List<Book> bag) {
        if(book.getStatus() == Status.AVAILABLE){
            bag.add(book);
        } else {
            throw new BookNotAvailableException("Not available");
        }
    }
}
