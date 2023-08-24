package dev.odane.capstoneproject.DTOs;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MemberDTO implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String phone;
}
