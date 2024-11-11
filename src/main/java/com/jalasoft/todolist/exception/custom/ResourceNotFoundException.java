package com.jalasoft.todolist.exception.custom;

/**
 * @author Chris Alan Apaza Aguilar
 */

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
