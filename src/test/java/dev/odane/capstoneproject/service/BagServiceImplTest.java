package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.BookBagDTO;
import dev.odane.capstoneproject.exception.BookNotFoundBookException;
import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.BorrowedBook;
import dev.odane.capstoneproject.model.Member;
import dev.odane.capstoneproject.model.Status;
import dev.odane.capstoneproject.repository.BookRepository;
import dev.odane.capstoneproject.repository.BorrowedBookRepository;
import dev.odane.capstoneproject.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BagServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowedBookRepository borrowedBookRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private HttpSession session;

    private BagServiceImpl bagService;

    @Captor
    private ArgumentCaptor<BorrowedBook> borrowedBookCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bagService = new BagServiceImpl(bookRepository, borrowedBookRepository, memberRepository);
    }

    @Test
    void addToBag() {
        // Create a test book and DTO
        Book book = new Book();
        book.setStatus(Status.AVAILABLE);
        BookBagDTO bookBagDTO = new BookBagDTO();
        bookBagDTO.setBookId(UUID.randomUUID());

        when(bookRepository.findById(bookBagDTO.getBookId())).thenReturn(java.util.Optional.of(book));
        when(session.getAttribute(BagServiceImpl.MEMBER_BAG)).thenReturn(new ArrayList<>());

        Book result = bagService.addToBag(bookBagDTO, session);

        assertNotNull(result);
        assertEquals(book, result);

       Mockito.verify(bookRepository, times(2)).findById(bookBagDTO.getBookId());
       Mockito.verify(session, times(1)).setAttribute(eq(BagServiceImpl.MEMBER_BAG), anyList());
    }

    @Test
    void testAddToBagBookNotFound() {
        BookBagDTO bookBagDTO = new BookBagDTO();
        bookBagDTO.setBookId(UUID.randomUUID());

        when(bookRepository.findById(bookBagDTO.getBookId())).thenReturn(java.util.Optional.empty());

        assertThrows(BookNotFoundBookException.class, () -> bagService.addToBag(bookBagDTO, session));

        Mockito.verify(bookRepository, times(1)).findById(bookBagDTO.getBookId());
    }

    @Test
    void testEmptyBag() {
        when(session.getAttribute(("BAG"))).thenReturn(new ArrayList<>()); // Assume Cart is the type stored in the session

        // Act
        String result = bagService.emptyBag(session);

        // Assert
        verify(session).removeAttribute("BAG"); // Verify that session.removeAttribute is called
        assertEquals("Cart emptied", result); // Check the expected return value
    }

    @Test
    void testReserveBookAvailable() {
        Book book = new Book();
        book.setStatus(Status.AVAILABLE);
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBook(book);

        when(bookRepository.findById(borrowedBook.getBook().getId())).thenReturn(java.util.Optional.of(book));

        String result = bagService.reserveBook(borrowedBook.getBook());

        assertNotNull(result);
        assertEquals("Book is Available", result);

        verify(bookRepository, times(1)).findById(borrowedBook.getBook().getId());
    }

    @Test
    void testReserveBookReserved() {
        Book book = new Book();
        book.setStatus(Status.RESERVED);
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBook(book);

        when(bookRepository.findById(borrowedBook.getBook().getId())).thenReturn(java.util.Optional.of(book));

        String result = bagService.reserveBook(borrowedBook.getBook());

        assertNotNull(result);
        assertEquals("Book is already Reserved by another member", result);

        verify(bookRepository, times(1)).findById(borrowedBook.getBook().getId());
    }

    @Test
    void testReserveBookNotFound() {
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBook(new Book());

        when(bookRepository.findById(borrowedBook.getBook().getId())).thenReturn(java.util.Optional.empty());

        assertThrows(BookNotFoundBookException.class, () -> bagService.reserveBook(borrowedBook.getBook()));

        verify(bookRepository, times(1)).findById(borrowedBook.getBook().getId());
    }

    @Test
    void testRemoveFromBag() {
        Book book = new Book();
        book.setId(UUID.randomUUID());
        List<Book> bag = new ArrayList<>();
        bag.add(book);

        when(session.getAttribute(BagServiceImpl.MEMBER_BAG)).thenReturn(bag);

        Book removedBook = bagService.removeFromBag(book, session);

        assertNotNull(removedBook);
        assertEquals(book, removedBook);
        assertTrue(bag.isEmpty());

        verify(session, times(1)).getAttribute(BagServiceImpl.MEMBER_BAG);
        verify(session, times(1)).setAttribute(eq(BagServiceImpl.MEMBER_BAG), anyList());
    }

    @Test
    void testViewBag() {
        Book book1 = new Book();
        Book book2 = new Book();
        List<Book> bag = new ArrayList<>();
        bag.add(book1);
        bag.add(book2);

        when(session.getAttribute(BagServiceImpl.MEMBER_BAG)).thenReturn(bag);

        List<Book> result = bagService.viewBag(session);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(book1));
        assertTrue(result.contains(book2));

        verify(session, times(1)).getAttribute(BagServiceImpl.MEMBER_BAG);
    }
    @Test
    void testBorrowBooks() {
        UUID memberId = UUID.randomUUID();
        Member member = new Member();
        member.setId(memberId);

        Book book1 = new Book();
        book1.setId(UUID.randomUUID());
        Book book2 = new Book();
        book2.setId(UUID.randomUUID());

        List<Book> bag = new ArrayList<>();
        bag.add(book1);
        bag.add(book2);

        when(memberRepository.findById(memberId)).thenReturn(java.util.Optional.of(member));
        when(session.getAttribute(BagServiceImpl.MEMBER_BAG)).thenReturn(bag);

        bagService.borrowBooks(memberId, session);

        verify(memberRepository,times(1)).findById(memberId);
        verify(session, times(1)).getAttribute(BagServiceImpl.MEMBER_BAG);
        verify(borrowedBookRepository, times(2)).save(borrowedBookCaptor.capture());

        // Verify that bookRepository.save() is invoked for each book in the bag

        List<BorrowedBook> capturedBorrowedBooks = borrowedBookCaptor.getAllValues();
        assertEquals(2, capturedBorrowedBooks.size());

        for (BorrowedBook borrowedBook : capturedBorrowedBooks) {
            assertEquals(member, borrowedBook.getMember());
            assertNotNull(borrowedBook.getDateBorrowed());
            assertNotNull(borrowedBook.getDueDate());
            assertNull(borrowedBook.getDateReturned());
            assertTrue(bag.contains(borrowedBook.getBook()));
        }
    }
}