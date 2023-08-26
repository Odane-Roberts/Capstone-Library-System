package dev.odane.capstoneproject.controller;

import dev.odane.capstoneproject.DTOs.FineDTO;
import dev.odane.capstoneproject.DTOs.MostBorrowedBookDTO;
import dev.odane.capstoneproject.model.Member;
import dev.odane.capstoneproject.repository.MemberRepository;
import dev.odane.capstoneproject.service.UtilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/analytics")
@RequiredArgsConstructor
public class UtilityController {

    private final UtilityService utilityService;
    private final MemberRepository memberRepository;
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

    // TODO: Generate reports and analytics

    // TODO: Add more methods as needed

}
