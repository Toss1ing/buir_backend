package com.example.taskmanager.service;

import static com.example.taskmanager.utilities.Constants.NOT_FOUND_MSG;
import static com.example.taskmanager.utilities.Constants.OBJECT_EXIST_MSG;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.taskmanager.dto.TaskDto;
import com.example.taskmanager.exception.BadRequestException;
import com.example.taskmanager.exception.NotFoundException;
import com.example.taskmanager.exception.ObjectExistException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public TaskDto addTask(@Valid final TaskDto taskDto) throws ObjectExistException {
        Task task = new Task(taskDto.getName(), taskDto.getDescription(), taskDto.getEndDate());

        if (taskRepository.findByName(taskDto.getName()).isPresent()) {
            throw new ObjectExistException(OBJECT_EXIST_MSG);
        }

        taskRepository.save(task);

        return modelMapper.map(task, TaskDto.class);

    }

    @Transactional
    public List<TaskDto> getAllTaskInUser(final Long userId) throws BadRequestException, NotFoundException {

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG));

        List<Task> tasks = user.getTasks();

        if (tasks.isEmpty()) {
            throw new BadRequestException(NOT_FOUND_MSG);
        }

        return tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).toList();

    }

    public TaskDto updateComplete(final Long id) throws NotFoundException {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG));

        task.setComplete(!task.isComplete());

        taskRepository.save(task);

        return modelMapper.map(task, TaskDto.class);
    }

    public TaskDto updateTask(Long id, TaskDto taskDto) throws NotFoundException {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG));

        task.setEndDate(taskDto.getEndDate());
        task.setDescription(taskDto.getDescription());
        task.setName(taskDto.getName());

        taskRepository.save(task);

        return modelMapper.map(task, TaskDto.class);

    }

}
