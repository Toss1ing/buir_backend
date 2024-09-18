package com.example.taskmanager.exception;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {

    private final String message;
    private final Map<String, String> errors;

}
