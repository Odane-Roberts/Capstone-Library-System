package dev.odane.capstoneproject.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;



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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)// Specify the format for serialization and deserialization
    private LocalDateTime publicationDate;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private Status status;


}