package dev.odane.bagservice.service;



import dev.odane.bagservice.dtos.FineDTO;
import dev.odane.bagservice.dtos.MostBorrowedBookDTO;
import dev.odane.bagservice.model.BorrowedBook;
import dev.odane.bagservice.model.Member;

import java.util.List;
import java.util.UUID;

public interface UtilityService {

    List<BorrowedBook> findAllTransaction();

    List<MostBorrowedBookDTO> findMostBorrowedBook();
    List<FineDTO> findMembersWhoOwes();

    String deactivateMember(Member member);

    String activateMember(Member member);

    BorrowedBook findTransactionById(UUID id);
}
