package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.MemberDTO;
import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.BorrowedBook;
import dev.odane.capstoneproject.model.Member;

import java.util.List;
import java.util.UUID;

public interface MemberService {
    List<MemberDTO> findAllMembers();
    Member findById(UUID id);
    void removeMember(Member member);
    Member updateMember(Member member);
    List<BorrowedBook> getBorrowBooks(UUID id);

    String returnBooks(Book books);


}
