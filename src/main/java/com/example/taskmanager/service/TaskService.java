package com.example.taskmanager.service;

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
        Task task = new Task(taskDto.getTaskName(), taskDto.getDescription(), taskDto.getEndDate());

        if (taskRepository.findByName(taskDto.getTaskName()).isPresent()) {
            throw new ObjectExistException("Message");
        }

        taskRepository.save(task);

        return modelMapper.map(task, TaskDto.class);

    }

    @Transactional
    public List<TaskDto> getAllTaskInUser(final Long userId) throws BadRequestException, NotFoundException {

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("message"));

        List<Task> tasks = user.getTasks();

        if (tasks.isEmpty()) {
            throw new BadRequestException("Message");
        }

        return tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).toList();

    }

    public TaskDto deleteTaskById(final Long id) throws NotFoundException {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("message"));

        taskRepository.delete(task);

        return modelMapper.map(task, TaskDto.class);
    }

    public TaskDto updateComplete(final Long id) throws NotFoundException {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Message"));

        task.setComplete(!task.isComplete());

        taskRepository.save(task);

        return modelMapper.map(task, TaskDto.class);
    }

    public TaskDto updateTitle(final Long id, @Valid final TaskDto task) throws NotFoundException {
        Task taskFromRepository = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Message"));

        taskFromRepository.setTaskName(task.getTaskName());

        taskRepository.save(taskFromRepository);

        return modelMapper.map(taskFromRepository, TaskDto.class);
    }

}
