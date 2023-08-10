package dev.odane.capstoneproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "Borrower")
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Serializable {
    @Id
    @SequenceGenerator(name = "member_seq", sequenceName = "member_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @Column(name = "borrowerid")
    private Long id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phone;
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @OneToMany
    @JoinTable(
            name = "borrowed_book", //name of the table that holds the relationship
            joinColumns = @JoinColumn(name = "borrowerid"), // references the member
            inverseJoinColumns = @JoinColumn(name = "book_id") // references the book
    )
    private List<BorrowedBook> borrowedBooks; // list of books borrowed
}
