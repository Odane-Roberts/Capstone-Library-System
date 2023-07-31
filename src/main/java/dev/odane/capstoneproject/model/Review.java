package dev.odane.capstoneproject.model;

import lombok.Data;

@Data
public class Review {
    private Member member;
    private String comment;
}
