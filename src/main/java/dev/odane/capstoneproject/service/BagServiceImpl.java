package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.BookBagDTO;
import dev.odane.capstoneproject.exception.BookNotAvailableException;
import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.BorrowedBook;
import dev.odane.capstoneproject.model.Status;
import dev.odane.capstoneproject.repository.BookRepository;
import dev.odane.capstoneproject.repository.BorrowedBookRepository;
import dev.odane.capstoneproject.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
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

        Book book = bookRepository.findById(bookBagDTO.getBookId()).get(); // change name later and fix optional warning
        if(!bag.contains(book)) {
            ifBookAvailableAddToBag(book, bag); // preliminary check` to only add available books to bag
        } else {
            throw new RuntimeException("Book already in bag "); // change to custom exception later
        }
        session.setAttribute(MEMBER_BAG, bag);
        return book;
    }

    @Override
    public Book removeFromBag(Book book, HttpSession session) {
        List<Book> bag = getBag(session);
        bag.remove(book);
        session.setAttribute(MEMBER_BAG,bag);
        return book;
    }

    @Override
    public List<Book> viewBag(long id, HttpSession session) {
        return getBag(session);
    }
    
    @Override
    public String borrowBooks(long id, HttpSession session) {
        List<Book> bag = getBag(session);
        BorrowedBook transaction = BorrowedBook.builder()
                .dateBorrowed(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(7))
                .dateReturned(null)
                .book(bag)
                .build();
        
        borrowedBookRepository.save(transaction);

        addToBorrowedList(id, bag);

        updateCatalog(bag);

        return "";
    }

    private void updateCatalog(List<Book> bag) { // set all the books in the transaction to borrowed
        bag.forEach(book -> book.setStatus(Status.BORROWED));
    }

    private void addToBorrowedList(long id, List<Book> bag) {
        memberRepository.findById(id)
                     .ifPresent(value -> bag
                     .forEach(value.getBorrowedBooks()::add));
    }

    // HELPER METHODS
    private static List<Book> getBag(HttpSession session) { // helper method to fetch bag from session
        return session.getAttribute(MEMBER_BAG) == null ? new ArrayList<>() :
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
