package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.MemberDTO;
import dev.odane.capstoneproject.mapper.MemberMapper;
import dev.odane.capstoneproject.model.*;
import dev.odane.capstoneproject.repository.BookRepository;
import dev.odane.capstoneproject.repository.BorrowedBookRepository;
import dev.odane.capstoneproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
class MemberServiceImplTest {



    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberMapper memberMapper;

    @Mock
    private BagServiceImpl bagService;
    @Captor
    private ArgumentCaptor<BorrowedBook> borrowedBookCaptor;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowedBookRepository borrowedBookRepository;

    private MemberServiceImpl memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        memberService = new MemberServiceImpl(memberRepository, memberMapper, bookRepository, borrowedBookRepository);
    }


    Member member = Member.builder()
            .id(1L)
            .name("O'dane")
            .email("odane@gmail.com")
            .gender(Gender.MALE)
            .phone("876-486-6195")
            .status(MemberStatus.ACTIVE)
            .borrowedBooks(new ArrayList<>())
            .build();
    MemberDTO member3  = MemberDTO.builder()
            .id(3L)
            .name("Andrew")
            .phone("876-486-5678")
            .build();

    Book bookPojo = Book.builder()
            .id(1L)
            .title("Book 1")
            .author("John")
            .isbn("234234234234")
            .publicationDate(LocalDateTime.now().minusYears(3))
            .category(Category.FICTION)
            .quantity(5)
            .status(Status.AVAILABLE)
            .build();

    BorrowedBook borrowedBook = BorrowedBook.builder()
            .id(1L)
            .book(bookPojo)
            .dateBorrowed(LocalDateTime.now())
            .dueDate(LocalDateTime.now().plusDays(7))
            .dateReturned(null)
            .member(member)
            .build();



    @Test
    void findAllMembers() {
        when(memberRepository.findAll()).thenReturn(Collections.emptyList());
        when(memberMapper.memberToMemberDTO(any())).thenReturn(member3);

        List<MemberDTO> members = memberService.findAllMembers();

        assertNotNull(members);
        assertEquals(0, members.size());

        verify(memberRepository, times(1)).findAll();
        verify(memberMapper, times(0)).memberToMemberDTO(any());

    }

    @Test
    void findById() {
        Long memberId = 1L;
        Member member = new Member();
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        Member result = memberService.findById(memberId);

        assertNotNull(result);
        assertEquals(member, result);

        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    void addMember() {
        Member member = new Member();
        when(memberRepository.save(member)).thenReturn(member);

        Member result = memberService.addMember(member);

        assertNotNull(result);
        assertEquals(member, result);

        verify(memberRepository, times(1)).save(member);
    }

    @Test
    void removeMember() {
        Member member = new Member();

        memberService.removeMember(member);

        verify(memberRepository, times(1)).delete(member);
    }

    @Test
    void updateMember() {
        Member member = new Member();
        when(memberRepository.save(member)).thenReturn(member);

        Member result = memberService.updateMember(member);

        assertNotNull(result);
        assertEquals(member, result);

        verify(memberRepository, times(1)).save(member);
    }

    @Test
    void getBorrowBooks() {
        Long memberId = 1L;
        Member member = new Member();
        member.setId(memberId);

        BorrowedBook borrowedBook = new BorrowedBook();
        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        borrowedBooks.add(borrowedBook);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(borrowedBookRepository.findBorrowedBookByMember(member)).thenReturn(borrowedBooks);

        List<BorrowedBook> result = memberService.getBorrowBooks(memberId);

        assertNotNull(result);
        assertEquals(borrowedBooks, result);

        verify(memberRepository, times(1)).findById(memberId);
        verify(borrowedBookRepository, times(1)).findBorrowedBookByMember(member);

    }

    @Test
    void returnBooks() {
        Book book = new Book();
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBook(book);

        when(borrowedBookRepository.findBorrowedBookByBook(book)).thenReturn(Optional.of(borrowedBook));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        String result = memberService.returnBooks(book);

        assertNotNull(result);
        assertEquals("Thanks for returning the book", result);

        verify(borrowedBookRepository, times(1)).findBorrowedBookByBook(book);
        verify(borrowedBookRepository, times(1)).save(borrowedBookCaptor.capture());

        BorrowedBook capturedBorrowedBook = borrowedBookCaptor.getValue();
        assertNotNull(capturedBorrowedBook);
        assertNotNull(capturedBorrowedBook.getDateReturned());

        verify(bookRepository, times(1)).findById(book.getId());
        verify(bookRepository, times(1)).save(book);
        assertEquals(Status.AVAILABLE, book.getStatus());
    }

    @Test
    void deactivateMember() {
        Member member = new Member();

        memberService.deactivateMember(member);

        assertEquals(MemberStatus.INACTIVE, member.getStatus());
    }

    @Test
    void activateMember() {
        Member member = new Member();

        memberService.activateMember(member);

        assertEquals(MemberStatus.ACTIVE, member.getStatus());
    }
}