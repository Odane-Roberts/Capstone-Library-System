package dev.odane.bagservice.controller;


import dev.odane.bagservice.dtos.BookBagDTO;
import dev.odane.bagservice.model.Book;
import dev.odane.bagservice.model.Category;
import dev.odane.bagservice.model.Status;
import dev.odane.bagservice.service.BagService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BagControllerTest {
    private final BagService bagService = mock(BagService.class);
    private final BagController bagController = new BagController(bagService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bagController).build();

    Book bookPojo = Book.builder()
            .id(UUID.randomUUID())
            .title("Book 1")
            .author("John")
            .isbn("234234234234")
            .publicationDate(LocalDateTime.now().minusYears(3))
            .category(Category.FICTION)
            .quantity(5)
            .status(Status.AVAILABLE)
            .build();

    @Test
    void testAddToBag() throws Exception {
        BookBagDTO book = new BookBagDTO();
        MockHttpSession session = new MockHttpSession();

        when(bagService.addToBag(book, session)).thenReturn(new Book());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .session(session))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testViewBag() throws Exception {
        MockHttpSession session = new MockHttpSession();
        List<Book> bag = new ArrayList<>();
        bag.add(bookPojo);

        when(bagService.viewBag(session)).thenReturn(bag);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/bag/view")
                        .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").exists()); // Replace with actual field of Book class
    }

    @Test
    void testRemoveFromBag() throws Exception {
        Book book = new Book();
        MockHttpSession session = new MockHttpSession();

        when(bagService.removeFromBag(book, session)).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/bag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testEmptyBag() throws Exception {
        MockHttpSession session = new MockHttpSession();

        when(bagService.emptyBag(session)).thenReturn("Bag emptied.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/bag/empty")
                        .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testBorrowBooks() throws Exception {
        UUID id = UUID.randomUUID();
        MockHttpSession session = new MockHttpSession();

        when(bagService.borrowBooks(id, session)).thenReturn("Books borrowed.");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bag/"+id+"/borrow")
                        .session(session))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testReserveBook() throws Exception {
        Book book = new Book();

        when(bagService.reserveBook(book)).thenReturn("Book reserved.");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/bag/reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}