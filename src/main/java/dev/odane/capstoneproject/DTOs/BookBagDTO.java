package dev.odane.capstoneproject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookBagDTO implements Serializable {
    private UUID bookId;
    private Integer quantity;
}
