package dev.odane.capstoneproject.controller;

import dev.odane.capstoneproject.model.Member;
import dev.odane.capstoneproject.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<Member> getMembers() {
        return service.findAllMembers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public Member addMember(@RequestBody Member member) {
        return service.addMember(member);
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/update")
    public Member updateMember(@RequestBody Member member){
        return service.updateMember(member);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/remove")
    public void removeMember(@RequestBody Member member) {
        service.removeMember(member);
    }

}
