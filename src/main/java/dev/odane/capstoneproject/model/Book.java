package dev.odane.capstoneproject.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;


@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_SEQ")
    @SequenceGenerator(name = "book_SEQ", sequenceName = "book_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String author;   // change this to a class later on
    private String isbn;
    @Column(name = "publicationdate")
    private Date publicationDate;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private Status status;


}