package dev.odane.capstoneproject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookBagDTO implements Serializable {
    private Long bookId;
    private Integer quantity;
}
