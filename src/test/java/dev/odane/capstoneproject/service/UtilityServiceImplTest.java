package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.FineDTO;
import dev.odane.capstoneproject.DTOs.MostBorrowedBookDTO;
import dev.odane.capstoneproject.exception.NoFineException;
import dev.odane.capstoneproject.model.Member;
import dev.odane.capstoneproject.model.MemberStatus;
import dev.odane.capstoneproject.repository.BorrowedBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UtilityServiceImplTest {

    @Mock
    private BorrowedBookRepository borrowedBookRepository;

    @InjectMocks
    private UtilityServiceImpl utilityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindMostBorrowedBook() {
        // Given
        List<Object[]> queryResult = new ArrayList<>();
        queryResult.add(new Object[]{"Book 1", 10L});
        queryResult.add(new Object[]{"Book 2", 8L});
        when(borrowedBookRepository.findMostBorrowedBook()).thenReturn(queryResult);

        // When
        List<MostBorrowedBookDTO> result = utilityService.findMostBorrowedBook();

        // Then
        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
        assertEquals(10, result.get(0).getBorrowCount());
        assertEquals("Book 2", result.get(1).getTitle());
        assertEquals(8, result.get(1).getBorrowCount());

        verify(borrowedBookRepository, times(1)).findMostBorrowedBook();
    }

    @Test
    void testFindMembersWhoOwes_NoFineException() {
        // Given
        List<Object[]> objectList = new ArrayList<>();
        when(borrowedBookRepository.findMembersWhoOwe()).thenReturn(objectList);

        // When, Then
        assertThrows(NoFineException.class, () -> utilityService.findMembersWhoOwes());

        verify(borrowedBookRepository, times(1)).findMembersWhoOwe();
    }

    @Test
    void testFindMembersWhoOwes() {
        // Given
        List<Object[]> objectList = new ArrayList<>();
        objectList.add(new Object[]{"Member 1", "member1@example.com", new BigDecimal(5)});
        when(borrowedBookRepository.findMembersWhoOwe()).thenReturn(objectList);

        // When
        List<FineDTO> result = utilityService.findMembersWhoOwes();

        // Then
        assertEquals(1, result.size());
        assertEquals("Member 1", result.get(0).getName());
        assertEquals("member1@example.com", result.get(0).getEmail());
        assertEquals(new BigDecimal(5).multiply(new BigDecimal(UtilityServiceImpl.COST_PER_DAY)), result.get(0).getFine());

        verify(borrowedBookRepository, times(1)).findMembersWhoOwe();
    }

    @Test
    void testDeactivateMember() {
        // Given
        Member member = new Member();
        member.setStatus(MemberStatus.ACTIVE);

        // When
        utilityService.deactivateMember(member);

        // Then
        assertEquals(MemberStatus.INACTIVE, member.getStatus());
    }

    @Test
    void testActivateMember() {
        // Given
        Member member = new Member();
        member.setStatus(MemberStatus.INACTIVE);

        // When
        utilityService.activateMember(member);

        // Then
        assertEquals(MemberStatus.ACTIVE, member.getStatus());
    }
}
