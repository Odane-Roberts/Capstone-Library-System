package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.FineDTO;
import dev.odane.capstoneproject.DTOs.MostBorrowedBookDTO;
import dev.odane.capstoneproject.exception.NoFineException;
import dev.odane.capstoneproject.model.Member;
import dev.odane.capstoneproject.model.MemberStatus;
import dev.odane.capstoneproject.repository.BorrowedBookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilityServiceImpl implements UtilityService {

    static final  int COST_PER_DAY = 100;
    private static final Logger logger = LoggerFactory.getLogger(UtilityServiceImpl.class);

    private final BorrowedBookRepository borrowedBookRepository;

    @Override
    public List<MostBorrowedBookDTO> findMostBorrowedBook() {
        logger.info("Finding most borrowed books");
        List<Object[]> queryResult = borrowedBookRepository.findMostBorrowedBook();
        List<MostBorrowedBookDTO> mostBorrowedBooks = new ArrayList<>();

        for (Object[] row : queryResult) {
            String title = (String) row[0];
            Long borrowCount = (Long) row[1];

            MostBorrowedBookDTO bookDTO = new MostBorrowedBookDTO();
            bookDTO.setTitle(title);
            bookDTO.setBorrowCount(Math.toIntExact(borrowCount));

            mostBorrowedBooks.add(bookDTO);
        }

        return mostBorrowedBooks;
    }

    @Override
    public List<FineDTO> findMembersWhoOwes() {
        logger.info("Finding members who owe fines");
        List<Object[]> objectList = borrowedBookRepository.findMembersWhoOwe();

        List<FineDTO> fineDTOList = new ArrayList<>();
        for (Object[] objectArray : objectList) {
            String name = (String) objectArray[0];
            String email = (String) objectArray[1];
            BigDecimal daysOutstanding = (BigDecimal) objectArray[2]; // Days outstanding

            FineDTO fineDTO = new FineDTO();
            fineDTO.setName(name);
            fineDTO.setEmail(email);
            fineDTO.setFine(daysOutstanding.multiply(new BigDecimal(COST_PER_DAY)));

            fineDTOList.add(fineDTO);
        }
        if (fineDTOList.isEmpty()) {
            logger.info("No members owe fines");
            throw new NoFineException("No fine owed");
        }
        return fineDTOList;
    }
    @Override
    public void deactivateMember(Member member) {
        member.setStatus(MemberStatus.INACTIVE);
    }
    @Override
    public void activateMember(Member member) {
        member.setStatus(MemberStatus.ACTIVE);
    }
}
