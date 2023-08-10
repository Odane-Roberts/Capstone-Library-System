package dev.odane.capstoneproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import dev.odane.capstoneproject.DTOs.BookDTO;
import dev.odane.capstoneproject.DTOs.MemberDTO;
import dev.odane.capstoneproject.model.*;
import dev.odane.capstoneproject.repository.MemberRepository;
import dev.odane.capstoneproject.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MemberService service;
    @Mock
    private MemberRepository repository;

    @InjectMocks
    private MemberController memberController;

    ObjectMapper objectMapper = new ObjectMapper(); // to convert object to string content
    ObjectWriter objectWriter = objectMapper.writer(); // can be implemented better

    MemberDTO member1  = MemberDTO.builder()
            .id(1L)
            .name("O'dane")
            .phone("876-486-6195")
            .build();
    MemberDTO member2  = MemberDTO.builder()
            .id(2L)
            .name("Jordan")
            .phone("876-486-1234")
            .build();
    MemberDTO member3  = MemberDTO.builder()
            .id(3L)
            .name("Andrew")
            .phone("876-486-5678")
            .build();

    Member member = Member.builder()
            .id(5L)
            .name("John")
            .email("john@gmail.com")
            .phone("876-486-1234")
            .status(MemberStatus.ACTIVE)
            .gender(Gender.MALE)
            .borrowedBooks(new ArrayList<>())
            .build();

    //bookDTO's
    BookDTO book1 = BookDTO.builder()
            .id(1L)
            .title("Book 1")
            .author("John")
            .status(Status.AVAILABLE)
            .build();
    BookDTO book2 = BookDTO.builder()
            .title("Book 2")
            .author("Joe")
            .status(Status.AVAILABLE)
            .build();
    BookDTO book3 = BookDTO.builder()
            .title("Book 3")
            .author("Job")
            .status(Status.AVAILABLE)
            .build();
    Book bookPojo = Book.builder()
            .id(1L)
            .title("Book 1")
            .author("John")
            .isbn("234234234234")
            .publicationDate(LocalDateTime.now().minusYears(3))
            .category(Category.FICTION)
            .quantity(5)
            .status(Status.AVAILABLE)
            .build();

    BorrowedBook borrowedBook = BorrowedBook.builder()
            .id(1L)
            .book(bookPojo)
            .dateBorrowed(LocalDateTime.now())
            .dueDate(LocalDateTime.now().plusDays(7))
            .dateReturned(null)
            .member(member)
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }


    @Test
    void getMembers() throws Exception {
        //given
        List<MemberDTO> members = new ArrayList<>(List.of(member1, member2, member3));

        //when
        Mockito.when(service.findAllMembers()).thenReturn(members);

        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/member")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("O'dane")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].phone", is("876-486-5678")));
    }

    @Test
    void getMemberById() throws Exception {
        //given
        Member mem = member;

        //when
        Mockito.when(service.findById(anyLong())).thenReturn(mem);

        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/member/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("John")));
    }

    @Test
    void addMember() throws Exception {
        //given
        Member newMember = member;

        //when
        Mockito.when(service.addMember(newMember)).thenReturn(newMember);

        //performing some utility methods
        objectMapper.findAndRegisterModules(); // update the module for object mapper to use local time and date
        String content = objectWriter.writeValueAsString(newMember); // convert object to json/string


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
    }

    @Test
    void updateMember() throws Exception {
        //given
        Member updatedMember = member;

        //when
        Mockito.when(service.updateMember(updatedMember)).thenReturn(updatedMember);

        objectMapper.findAndRegisterModules();
        String content = objectWriter.writeValueAsString(updatedMember);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isAccepted());
    }

    @Test
    void getBorrowedBooks() throws Exception {
        //given
        List<BorrowedBook> books = List.of(borrowedBook); // create BorrowedBook mock objects

        //when
        Mockito.when(service.getBorrowBooks(anyLong())).thenReturn(books); // refactor

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/member/5/borrowed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].book.title", is("Book 1")));

    }

    @Test
    void removeMember() throws Exception {
        //given
        Member memberToBeDeleted = member;

        //When
        Mockito.when(service.removeMember(memberToBeDeleted)).thenReturn(memberToBeDeleted);


        //then
        String content = objectWriter.writeValueAsString(memberToBeDeleted);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }
}