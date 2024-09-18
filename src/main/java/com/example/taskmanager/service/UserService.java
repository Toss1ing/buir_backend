package com.example.taskmanager.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.taskmanager.dto.UserDto;
import com.example.taskmanager.exception.NotFoundException;
import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.RoleRepository;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public UserDto addAdmin(final Long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Message"));

        Role role = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new NotFoundException("Message"));

        user.getRoles().add(role);

        userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }

    public List<UserDto> getAllUsers() throws NotFoundException {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new NotFoundException("Message");
        }

        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
    }

    public UserDto getUserById(final Long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Message"));

        return modelMapper.map(user, UserDto.class);
    }

    public UserDto deleteUserById(final Long id) throws NotFoundException {

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Message"));

        userRepository.delete(user);

        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    public UserDto addTaskInUserById(Long userId, Long taskId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Message"));

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Message"));

        task.setUser(user);

        user.getTasks().add(task);

        userRepository.save(user);
        taskRepository.save(task);

        return modelMapper.map(user, UserDto.class);

    }

}
