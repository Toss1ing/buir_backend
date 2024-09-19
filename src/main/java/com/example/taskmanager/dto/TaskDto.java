package com.example.taskmanager.dto;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "name of task must not be empty")
    private String name;

    @NotBlank(message = "description must not be empty")
    @Size(min = 10, max = 256, message = "A size of description must be between 10 and 256 character")
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean complete;

    private Instant endDate;

}
