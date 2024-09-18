package com.example.taskmanager.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.exception.NotFoundException;
import com.example.taskmanager.exception.ObjectExistException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterRequest user)
            throws NotFoundException, ObjectExistException {
        return new ResponseEntity<>(authService.registerUser(user), HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody AuthRequest user) throws NotFoundException {
        return new ResponseEntity<>(authService.loginUser(user), HttpStatus.ACCEPTED);
    }

}
