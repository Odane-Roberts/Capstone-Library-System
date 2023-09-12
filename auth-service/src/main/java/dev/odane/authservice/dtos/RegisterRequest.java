package dev.odane.authservice.dtos;


import dev.odane.authservice.model.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {

    @NotNull(message = "First name is required")
    private String firstname;
    @NotNull(message = "Last name is required")
    private String lastname;
    @Pattern(regexp = "^\\d{10}$", message = "Invalid phone number")
    private String phone;
    @NotNull
    @Past
    private LocalDate dob;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Email(message = "Not a valid email address")
    private String email;
    @NotNull(message = "Password is required")
    private String password;
}
