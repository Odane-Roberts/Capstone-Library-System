package dev.odane.capstoneproject.controller;

import dev.odane.capstoneproject.DTOs.MemberDTO;
import dev.odane.capstoneproject.model.Admin;
import dev.odane.capstoneproject.model.BorrowedBook;
import dev.odane.capstoneproject.model.Member;
import dev.odane.capstoneproject.model.Role;
import dev.odane.capstoneproject.repository.AdminRepository;
import dev.odane.capstoneproject.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

 class MemberControllerTest {

    @Mock
    private MemberService service;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private MemberController memberController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    void testGetMembers() throws Exception {
        when(adminRepository.findAll()).thenReturn(Collections.singletonList(new Admin("authorized@example.com", Role.ADMIN)));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        List<MemberDTO> members = Collections.singletonList(new MemberDTO());
        when(service.findAllMembers()).thenReturn(members);

        mockMvc.perform(get("/api/v1/member"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        verify(service, times(1)).findAllMembers();
    }


     @Test
     void testGetMemberById() throws Exception {
         UUID memberId = UUID.randomUUID();
         Member member = new Member();
         member.setId(memberId);
         member.setName("John Doe");
         member.setRole(Role.MEMBER); // Set the role in the member object

         when(service.findById(memberId)).thenReturn(member);

         mockMvc.perform(get("/api/v1/member/" + memberId))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.id").value(memberId.toString()))
                 .andExpect(jsonPath("$.name").value("John Doe"))
                 .andExpect(jsonPath("$.role").value("MEMBER")); // Assert the role name

         verify(service, times(1)).findById(memberId);
     }

    @Test
    void testUpdateMember() throws Exception {
        Member member = new Member();
        when(service.updateMember(member)).thenReturn(member);

        mockMvc.perform(put("/api/v1/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // Provide appropriate JSON content here
                .andExpect(status().isAccepted());

        verify(service, times(1)).updateMember(any(Member.class));
    }

    @Test
    void testGetBorrowedBooks() throws Exception {
        UUID memberId = UUID.randomUUID();
        List<BorrowedBook> borrowedBooks = Collections.singletonList(new BorrowedBook());
        when(service.getBorrowBooks(memberId)).thenReturn(borrowedBooks);

        mockMvc.perform(get("/api/v1/member/" + memberId + "/borrowed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        verify(service, times(1)).getBorrowBooks(memberId);
    }

    @Test
    void testGetSecurityContextAuthorized() throws Exception {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("authorized@example.com");

        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/api/v1/member"))
                .andExpect(status().isOk());

    }

     @Test
     void testGetSecurityContextUnauthorized() throws Exception {
         when(securityContext.getAuthentication()).thenReturn(authentication);
         when(authentication.getName()).thenReturn("unauthorized@example.com");


         SecurityContextHolder.setContext(securityContext);

         mockMvc.perform(get("/api/v1/member"))
                 .andExpect(status().isOk());


     }


     // Additional tests for other methods can be added here
}
