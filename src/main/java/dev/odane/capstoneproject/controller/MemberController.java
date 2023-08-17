package dev.odane.capstoneproject.controller;

import dev.odane.capstoneproject.DTOs.MemberDTO;
import dev.odane.capstoneproject.model.Book;
import dev.odane.capstoneproject.model.BorrowedBook;
import dev.odane.capstoneproject.model.Member;
import dev.odane.capstoneproject.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
@Slf4j
public class MemberController {
    private final MemberService service;


    public MemberController(MemberService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<MemberDTO> getMembers() {
        return service.findAllMembers();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable Long id) {
        return service.findById(id);
    }


    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    public Member updateMember(@RequestBody Member member){
        return service.updateMember(member);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/borrowed")
    public List<BorrowedBook> getBorrowedBooks(@PathVariable Long id) {
        return service.getBorrowBooks(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/return")
    public String returnBooks(@RequestBody Book books){
        return service.returnBooks(books);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void removeMember(@RequestBody Member member) {
        service.removeMember(member);
    }

}
