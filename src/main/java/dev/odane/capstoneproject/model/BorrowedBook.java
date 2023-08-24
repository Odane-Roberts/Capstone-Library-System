package dev.odane.capstoneproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "borrowed_book")
@AllArgsConstructor
@JsonIgnoreProperties("member") // Exclude the member property from JSON serialization
public class BorrowedBook implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dateborrowed")
    private LocalDateTime dateBorrowed;
    @Column(name = "duedate")
    private LocalDateTime dueDate;
    @Column(name = "datereturned")
    private LocalDateTime dateReturned;

    @ManyToOne
    @JoinColumn(name = "borrowerid")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
