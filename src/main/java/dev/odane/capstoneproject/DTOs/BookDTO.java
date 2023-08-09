package dev.odane.capstoneproject.DTOs;


import dev.odane.capstoneproject.model.Status;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BookDTO implements Serializable {
    private Long id;
    private String title;
    private String author;
    private Status status;
}
