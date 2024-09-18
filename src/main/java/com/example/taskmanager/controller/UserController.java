package com.example.taskmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dto.UserDto;
import com.example.taskmanager.exception.NotFoundException;
import com.example.taskmanager.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping(path = "admin/add/{id}")
    public ResponseEntity<UserDto> addAdmin(final @PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(userService.addAdmin(id), HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "admin/all")
    public ResponseEntity<List<UserDto>> getAllUsers() throws NotFoundException {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping(path = "user/add/task/{userId}/{taskId}")
    public ResponseEntity<UserDto> addTaskInUserById(@PathVariable final Long userId, @PathVariable final Long taskId)
            throws NotFoundException {
        return new ResponseEntity<>(userService.addTaskInUserById(userId, taskId), HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "user/id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable final Long id) throws NotFoundException {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "user/delete/{id}")
    public ResponseEntity<UserDto> deleteUserById(@PathVariable final Long id) throws NotFoundException {
        return new ResponseEntity<>(userService.deleteUserById(id), HttpStatus.NO_CONTENT);
    }

}
