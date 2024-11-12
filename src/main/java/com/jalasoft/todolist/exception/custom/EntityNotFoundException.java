package com.jalasoft.todolist.exception.custom;

/**
 * @author Chris Alan Apaza Aguilar
 */

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
