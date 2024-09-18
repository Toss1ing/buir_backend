package com.example.taskmanager.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "login must be not empty")
    private String login;

    @NotBlank(message = "email is empty")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email is not valid")
    private String email;

    @NotBlank(message = "password must be not empty")
    private String password;

    @NotBlank(message = "phone number must be not empty")
    private String phoneNumber;
}
