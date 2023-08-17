package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.MemberDTO;
import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.BorrowedBook;
import dev.odane.capstoneproject.model.Member;

import java.util.List;

public interface MemberService {
    List<MemberDTO> findAllMembers();
    Member findById(Long id);
    Member removeMember(Member member);
    Member updateMember(Member member);
    List<BorrowedBook> getBorrowBooks(Long id);

    String returnBooks(Book books);

    // todo move to utility service methods
    void deactivateMember(Member member);

    void activateMember(Member member);
}
