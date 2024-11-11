package com.jalasoft.todolist.exception.custom;

/**
 * @author Chris Alan Apaza Aguilar
 */

public class ResourceBadRequestException extends RuntimeException {
    public ResourceBadRequestException(String message) {
        super(message);
    }
}
