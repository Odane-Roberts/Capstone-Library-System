package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.FineDTO;
import dev.odane.capstoneproject.DTOs.MostBorrowedBookDTO;
import dev.odane.capstoneproject.model.Member;

import java.util.List;

public interface UtilityService {

    List<MostBorrowedBookDTO> findMostBorrowedBook();
    List<FineDTO> findMembersWhoOwes();

    void deactivateMember(Member member);

    void activateMember(Member member);

}
