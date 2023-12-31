package dev.odane.capstoneproject.controller;

import dev.odane.capstoneproject.DTOs.MemberDTO;
import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.BorrowedBook;
import dev.odane.capstoneproject.model.Member;
import dev.odane.capstoneproject.model.Role;
import dev.odane.capstoneproject.repository.AdminRepository;
import dev.odane.capstoneproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Slf4j
public class MemberController {

    private final MemberService service;
    private final AdminRepository adminRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<MemberDTO> getMembers() {
//        getSecurityContext();

        log.info("Get all members request received");
        List<MemberDTO> members = service.findAllMembers();
        log.info("Members retrieved");
        return members;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable UUID id) {
//        getSecurityContext();
        log.info("Get member by ID request received");
        Member member = service.findById(id);
        log.info("Member retrieved");
        return member;
    }

    void getSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        if (adminRepository.findAll().stream().
                filter(admin -> admin.getEmail().equals(email))
                .noneMatch(role -> role.getRole().equals(Role.ADMIN))) {
            log.info("User with email " + email + " is not authorized");
            throw new AccessDeniedException("Not authorized");
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    public Member updateMember(@RequestBody Member member) {
        log.info("Update member request received");
        Member updatedMember = service.updateMember(member);
        log.info("Member updated");
        return updatedMember;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/borrowed")
    public List<BorrowedBook> getBorrowedBooks(@PathVariable UUID id) {
        log.info("Get borrowed books request received");
        List<BorrowedBook> borrowedBooks = service.getBorrowBooks(id);
        log.info("Borrowed books retrieved");
        return borrowedBooks;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/return")
    public String returnBooks(@RequestBody Book books) {
        log.info("Return books request received");
        String returnMessage = service.returnBooks(books);
        log.info("Books returned");
        return returnMessage;
    }


}
