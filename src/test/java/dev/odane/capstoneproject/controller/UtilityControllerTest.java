package dev.odane.capstoneproject.controller;

import dev.odane.capstoneproject.DTOs.FineDTO;
import dev.odane.capstoneproject.DTOs.MostBorrowedBookDTO;
import dev.odane.capstoneproject.model.Member;
import dev.odane.capstoneproject.repository.MemberRepository;
import dev.odane.capstoneproject.service.UtilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UtilityControllerTest {

    @Mock
    private UtilityService utilityService;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private UtilityController utilityController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(utilityController).build();
    }

    @Test
    void testFindMostBorrowedBook() throws Exception {
        // Setup
        List<MostBorrowedBookDTO> mostBorrowedBooks = Collections.singletonList(new MostBorrowedBookDTO());
        when(utilityService.findMostBorrowedBook()).thenReturn(mostBorrowedBooks);

        // Test
        mockMvc.perform(get("/api/v1/analytics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        // Verify
        verify(utilityService, times(1)).findMostBorrowedBook();
    }

    @Test
    void testFindMembersWhoOwes() throws Exception {
        // Setup
        List<FineDTO> membersWithFines = Collections.singletonList(new FineDTO());
        when(utilityService.findMembersWhoOwes()).thenReturn(membersWithFines);

        // Test
        mockMvc.perform(get("/api/v1/analytics/fines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        // Verify
        verify(utilityService, times(1)).findMembersWhoOwes();
    }

    @Test
    void testRemoveMember() throws Exception {
        UUID memberId = UUID.randomUUID(); // Generate a random UUID for the member ID
        Member member = new Member();
        member.setId(memberId); // Set the UUID as a string

        // Test
        mockMvc.perform(delete("/api/v1/analytics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"" + memberId + "\"}"))
                .andExpect(status().isOk());

        // Verify
        verify(memberRepository, times(1)).delete(any(Member.class));
    }
}
