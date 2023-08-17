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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "borrowed_book_seq")
    @SequenceGenerator(name = "borrowed_book_seq", sequenceName = "borrowed_book_seq", allocationSize = 1)
    private Long id;
    @Column(name = "dateborrowed")
    private LocalDateTime dateBorrowed;
    @Column(name = "duedate")
    private LocalDateTime dueDate;
    @Column(name = "datereturned")
    private LocalDateTime dateReturned;

    @OneToOne
    @JoinColumn(name = "borrowerid")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
