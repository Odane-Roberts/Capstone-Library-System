package dev.odane.capstoneproject.controller;

import dev.odane.capstoneproject.DTOs.BookDTO;
import dev.odane.capstoneproject.DTOs.MemberDTO;
import dev.odane.capstoneproject.mapper.BookMapper;
import dev.odane.capstoneproject.mapper.MemberMapper;
import dev.odane.capstoneproject.model.Member;
import dev.odane.capstoneproject.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService service;
    private final BookMapper bookMapper;
    private final MemberMapper memberMapper;

    public MemberController(MemberService service, BookMapper mapper, MemberMapper memberMapper) {
        this.service = service;
        this.bookMapper = mapper;
        this.memberMapper = memberMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<MemberDTO> getMembers() {
        return service.findAllMembers()
                .stream().map(memberMapper::memberToMemberDTO)
                .toList();
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
    @GetMapping("/{id}/borrowed")
    public List<BookDTO> getBorrowedBooks(@PathVariable Long id) {
        return service.findById(id).getBorrowedBooks()
                .stream().map(bookMapper::bookToBookDTO)
                .toList();
    }

    // TODO: 02/08/2023  implement return of books function

    // TODO: 02/08/2023  implement renewal of borrowed books function
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void removeMember(@RequestBody Member member) {
        service.removeMember(member);
    }

}
