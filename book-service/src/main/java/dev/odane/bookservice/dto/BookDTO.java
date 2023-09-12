package dev.odane.bookservice.dto;



import dev.odane.bookservice.model.Status;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class BookDTO implements Serializable {
    private UUID id;
    private String title;
    private String author;
    private Status status;
}
