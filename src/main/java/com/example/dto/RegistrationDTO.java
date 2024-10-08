package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationDTO {
    @Size(min = 3, max = 20, message = "Name should be at least 3 chars and at most 20 chars")
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Surname is required")
    @Size(min = 3, max = 20, message = "Surname should be at least 3 chars and at most 20 chars")
    private String surname;
    @NotBlank(message = "Phone is required")
    @Size(min = 12, max = 12, message = "Phone should be 12 chars")
    private String phone;
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
