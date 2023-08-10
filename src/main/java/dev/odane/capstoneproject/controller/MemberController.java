package dev.odane.capstoneproject.controller;

import dev.odane.capstoneproject.DTOs.MemberDTO;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Member addMember(@RequestBody Member member) {
        return service.addMember(member);
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

    // TODO: 02/08/2023  implement return of books function
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/return")
    public String returnBooks(@PathVariable Long id, @RequestBody BorrowedBook books){
        return service.returnBooks(id, books);
    }

    // TODO: 02/08/2023  implement renewal of borrowed books function
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void removeMember(@RequestBody Member member) {
        service.removeMember(member);
    }

}
