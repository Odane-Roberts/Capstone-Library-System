package dev.odane.capstoneproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "Borrowed_Book")
@AllArgsConstructor
public class BorrowedBook implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dateBorrowed;
    private LocalDateTime dueDate;
    private LocalDateTime dateReturned;

    @ManyToOne
    @JoinColumn(name = "borrowerid")
    private Member member;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Book> book;
}
