package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.MemberDTO;
import dev.odane.capstoneproject.exception.BookNotFoundBookException;
import dev.odane.capstoneproject.exception.BorrowedBookNotFoundException;
import dev.odane.capstoneproject.exception.MemberNotFoundException;
import dev.odane.capstoneproject.mapper.MemberMapper;
import dev.odane.capstoneproject.model.*;
import dev.odane.capstoneproject.repository.BookRepository;
import dev.odane.capstoneproject.repository.BorrowedBookRepository;
import dev.odane.capstoneproject.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository repository;
    private final MemberMapper memberMapper;
    private final BookRepository bookRepository;
    private final BorrowedBookRepository borrowedBookRepository;

    public MemberServiceImpl(MemberRepository repository, MemberMapper memberMapper, BookRepository bookRepository, BorrowedBookRepository borrowedBookRepository) {
        this.repository = repository;
        this.memberMapper = memberMapper;
        this.bookRepository = bookRepository;
        this.borrowedBookRepository = borrowedBookRepository;
    }

    @Override
    public List<MemberDTO> findAllMembers() {
        return repository.findAll().stream()
                .map(memberMapper::memberToMemberDTO).toList();
    }

    @Override
    public Member findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->new MemberNotFoundException("Member not found "+id));
    }


    @Override
    public Member removeMember(Member member) {
        repository.delete(member);
        return member;
    }

    @Override
    public Member updateMember(Member member) {
        return repository.save(member);
    }

    @Override
    public List<BorrowedBook> getBorrowBooks(Long id) {
        return borrowedBookRepository.findBorrowedBookByMember(repository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member not found "+id)));
    }

    @Override
    public String returnBooks(Book book) {

        BorrowedBook borrowedBook = borrowedBookRepository.findBorrowedBookByBook(book)
                        .orElseThrow(() -> new BorrowedBookNotFoundException("Borrowed book not found "));
        borrowedBook.setDateReturned(LocalDateTime.now());
        borrowedBookRepository.save(borrowedBook);


        Book book1 = bookRepository.findById(book.getId())
                .orElseThrow(() -> new BookNotFoundBookException("Book not found"));
                book1.setStatus(Status.AVAILABLE);
        bookRepository.save(book1);

        return "Thanks for returning the book"; // return a status
    }

    @Override
    public void deactivateMember(Member member) {
        member.setStatus(MemberStatus.INACTIVE);
    }
    @Override
    public void activateMember(Member member) {
        member.setStatus(MemberStatus.ACTIVE);
    }
}
