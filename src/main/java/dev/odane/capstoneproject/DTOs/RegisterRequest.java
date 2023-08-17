package dev.odane.capstoneproject.DTOs;

import dev.odane.capstoneproject.model.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String email;
    private String password;
}
