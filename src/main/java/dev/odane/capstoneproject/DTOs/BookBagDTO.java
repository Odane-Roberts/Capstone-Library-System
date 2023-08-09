package dev.odane.capstoneproject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookBagDTO {
    private Long bookId;
    private Integer quantity;
}
