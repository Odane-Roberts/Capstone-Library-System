package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.FineDTO;
import dev.odane.capstoneproject.DTOs.MostBorrowedBookDTO;

import java.util.List;

public interface UtilityService {

    List<MostBorrowedBookDTO> findMostBorrowedBook();
    List<FineDTO> findMembersWhoOwes();



}
