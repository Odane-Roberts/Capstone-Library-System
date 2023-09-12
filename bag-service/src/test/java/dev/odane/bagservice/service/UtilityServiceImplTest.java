package dev.odane.bagservice.service;

import dev.odane.bagservice.dtos.FineDTO;
import dev.odane.bagservice.dtos.MostBorrowedBookDTO;
import dev.odane.bagservice.exception.NoFineException;
import dev.odane.bagservice.model.Member;
import dev.odane.bagservice.model.MemberStatus;
import dev.odane.bagservice.repository.BorrowedBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

 class UtilityServiceImplTest {

    @Mock
    private BorrowedBookRepository borrowedBookRepository;

    private UtilityService utilityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        utilityService = new UtilityServiceImpl(borrowedBookRepository);
    }

    @Test
     void testFindMostBorrowedBook() {
        // Arrange
        List<Object[]> queryResult = new ArrayList<>();
        queryResult.add(new Object[]{"Book1", 5L});
        queryResult.add(new Object[]{"Book2", 8L});

        when(borrowedBookRepository.findMostBorrowedBook()).thenReturn(queryResult);

        // Act
        List<MostBorrowedBookDTO> mostBorrowedBooks = utilityService.findMostBorrowedBook();

        // Assert
        assertNotNull(mostBorrowedBooks);
        assertEquals(2, mostBorrowedBooks.size());
        assertEquals("Book1", mostBorrowedBooks.get(0).getTitle());
        assertEquals(5, mostBorrowedBooks.get(0).getBorrowCount());
        assertEquals("Book2", mostBorrowedBooks.get(1).getTitle());
        assertEquals(8, mostBorrowedBooks.get(1).getBorrowCount());
    }

    @Test
     void testFindMembersWhoOwes() {
        // Arrange
        List<Object[]> objectList = new ArrayList<>();
        objectList.add(new Object[]{"John Doe", "john@example.com", new BigDecimal(5)});
        objectList.add(new Object[]{"Alice Smith", "alice@example.com", new BigDecimal(3)});

        when(borrowedBookRepository.findMembersWhoOwe()).thenReturn(objectList);

        // Act
        List<FineDTO> fineDTOList = utilityService.findMembersWhoOwes();

        // Assert
        assertNotNull(fineDTOList);
        assertEquals(2, fineDTOList.size());
        assertEquals("John Doe", fineDTOList.get(0).getName());
        assertEquals("john@example.com", fineDTOList.get(0).getEmail());
        assertEquals(new BigDecimal(500), fineDTOList.get(0).getFine());
        assertEquals("Alice Smith", fineDTOList.get(1).getName());
        assertEquals("alice@example.com", fineDTOList.get(1).getEmail());
        assertEquals(new BigDecimal(300), fineDTOList.get(1).getFine());
    }

    @Test
    void testFindMembersWhoOwes_NoFineException() {
        // Arrange
        when(borrowedBookRepository.findMembersWhoOwe()).thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(NoFineException.class, () -> utilityService.findMembersWhoOwes());
    }

    @Test
    void testDeactivateMember() {
        // Arrange
        Member member = new Member();
        member.setId(UUID.randomUUID());
        member.setStatus(MemberStatus.ACTIVE);

        // Act
        String result = utilityService.deactivateMember(member);

        // Assert
        assertEquals("Member with id "+member.getId()+" Deactivated", result);
        assertEquals(MemberStatus.INACTIVE, member.getStatus());
    }

    @Test
     void testActivateMember() {
        // Arrange
        Member member = new Member();
        member.setId(UUID.randomUUID());
        member.setStatus(MemberStatus.INACTIVE);

        // Act
        String result = utilityService.activateMember(member);

        // Assert
        assertEquals("Member with id "+member.getId()+" Activated", result);
        assertEquals(MemberStatus.ACTIVE, member.getStatus());
    }
}
