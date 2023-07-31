package dev.odane.capstoneproject.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "Borrowed_Book")
public class BorrowedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date dateBorrowed;
    private Date dueDate;
    private Date dateReturned;

    @ManyToOne
    @JoinColumn(name = "borrowerid")
    private Member member;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Book> book;
}
