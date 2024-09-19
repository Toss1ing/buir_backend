package com.example.taskmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dto.TaskDto;
import com.example.taskmanager.exception.BadRequestException;
import com.example.taskmanager.exception.NotFoundException;
import com.example.taskmanager.exception.ObjectExistException;
import com.example.taskmanager.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1")
public class TaskController {

    private final TaskService taskService;

    @PostMapping(path = "user/add/task")
    public ResponseEntity<TaskDto> addTask(@RequestBody @Valid final TaskDto taskDto) throws ObjectExistException {
        return new ResponseEntity<>(taskService.addTask(taskDto), HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "user/get/task/{userId}")
    public ResponseEntity<List<TaskDto>> getAllTaskInUser(@PathVariable final Long userId)
            throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(taskService.getAllTaskInUser(userId), HttpStatus.OK);
    }

    @DeleteMapping(path = "user/delete/task/id/{id}")
    public ResponseEntity<TaskDto> deleteTaskById(@PathVariable final Long id)
            throws NotFoundException {
        return new ResponseEntity<>(taskService.deleteTaskById(id),
                HttpStatus.OK);
    }

    @PutMapping(path = "user/task/complete/{id}")
    public ResponseEntity<TaskDto> updateComplete(@PathVariable final Long id)
            throws NotFoundException {
        return new ResponseEntity<>(taskService.updateComplete(id), HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "user/task/edit/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable final Long id, final @RequestBody TaskDto taskDto)
            throws NotFoundException {
        return new ResponseEntity<>(taskService.updateTask(id, taskDto), HttpStatus.ACCEPTED);
    }

}
