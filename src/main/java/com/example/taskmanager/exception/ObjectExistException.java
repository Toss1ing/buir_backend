package com.example.taskmanager.exception;

public class ObjectExistException extends RuntimeException {

    public ObjectExistException(final String message) {
        super(message);
    }

}
