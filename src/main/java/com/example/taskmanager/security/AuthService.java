package com.example.taskmanager.security;

import java.util.ArrayList;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.taskmanager.config.JwtService;
import com.example.taskmanager.exception.NotFoundException;
import com.example.taskmanager.exception.ObjectExistException;
import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.RoleRepository;
import com.example.taskmanager.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final ModelMapper modelMapper;
        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final PasswordEncoder passwordEncoder;

        @Transactional
        public AuthResponse registerUser(@Valid final RegisterRequest request)
                        throws NotFoundException, ObjectExistException {

                Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new NotFoundException("NotFound"));

                User user = new User(request.getLogin(), request.getEmail(),
                                passwordEncoder.encode(request.getPassword()), request.getPhoneNumber(),
                                Arrays.asList(role), new ArrayList<>());

                if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                        throw new ObjectExistException("Message");
                }

                userRepository.save(user);
                var jwtToken = jwtService.generateToken(user);

                AuthResponse authResponse = modelMapper.map(user, AuthResponse.class);

                authResponse.setToken(jwtToken);

                return authResponse;
        }

        public AuthResponse loginUser(@Valid final AuthRequest request) throws NotFoundException {
                authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));

                User user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new NotFoundException("Message"));

                var jwtToken = jwtService.generateToken(user);

                AuthResponse authResponse = modelMapper.map(user, AuthResponse.class);

                authResponse.setToken(jwtToken);

                return authResponse;
        }

}