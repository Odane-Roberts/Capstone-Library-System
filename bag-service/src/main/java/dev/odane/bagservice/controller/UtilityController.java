package dev.odane.bagservice.controller;


import dev.odane.bagservice.client.MemberClient;
import dev.odane.bagservice.dtos.FineDTO;
import dev.odane.bagservice.dtos.MostBorrowedBookDTO;
import dev.odane.bagservice.model.BorrowedBook;
import dev.odane.bagservice.model.Member;
import dev.odane.bagservice.service.UtilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/utility")
@RequiredArgsConstructor
public class UtilityController {

    private final UtilityService utilityService;
    private final MemberClient memberRepository;
    private final Logger log = LoggerFactory.getLogger(UtilityController.class);

    @GetMapping
    public List<MostBorrowedBookDTO> findMostBorrowedBook() {
        log.info("Find most borrowed book request received");
        List<MostBorrowedBookDTO> mostBorrowedBooks = utilityService.findMostBorrowedBook();
        log.info("Most borrowed books retrieved");
        return mostBorrowedBooks;
    }

    @GetMapping("/fines")
    public List<FineDTO> findMembersWhoOwes() {
        log.info("Find members who owe fines request received");
        List<FineDTO> membersWithFines = utilityService.findMembersWhoOwes();
        log.info("Members who owe fines retrieved");
        return membersWithFines;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void removeMember(@RequestBody @Valid Member member) {
        log.info("Remove member request received");
        memberRepository.delete(member);
        log.info("Member removed");
    }

    @GetMapping("transactions")
    public List<BorrowedBook> getAllTransaction(){
        return utilityService.findAllTransaction();
    }

    @GetMapping("transactions/{id}")
    public BorrowedBook getBorrowedBookById(@PathVariable("id")UUID id) {
        return utilityService.findTransactionById(id);
    }

                                            @PutMapping("/deactivate")
    public String deactivateMember(@RequestBody Member member) {
       return utilityService.deactivateMember(member);
    }

    @PutMapping("/activate")
    public String activateMember(@RequestBody Member member){
        return utilityService.activateMember(member);
    }


}
