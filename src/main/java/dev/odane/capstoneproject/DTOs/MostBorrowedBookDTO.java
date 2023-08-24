package dev.odane.capstoneproject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MostBorrowedBookDTO implements Serializable {
    private String title ;
    private Integer borrowCount;
}
