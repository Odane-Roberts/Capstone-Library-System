package dev.odane.capstoneproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_SEQ")
    @SequenceGenerator(name = "book_SEQ", sequenceName = "book_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String author;   // change this to a class later on! maybe not
    private String isbn;
    @Column(name = "publicationdate")
    private Date publicationDate;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private Status status;


}