package com.example.taskmanager.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "login must not be empty")
    private String login;
    private String email;
    private List<TaskDto> tasks = new ArrayList<>();
    private List<RoleDto> roles = new ArrayList<>();

}
