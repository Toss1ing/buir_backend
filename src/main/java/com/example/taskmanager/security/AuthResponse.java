package com.example.taskmanager.security;

import java.util.List;

import com.example.taskmanager.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private Long id;
    private String login;
    private String email;
    private String phoneNumber;
    private String token;
    private List<Role> roles;

}
