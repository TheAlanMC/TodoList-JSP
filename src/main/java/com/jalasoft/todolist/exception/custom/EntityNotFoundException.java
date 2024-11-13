package com.jalasoft.todolist.exception.custom;

/**
 * Custom exception for handling cases where an entity is not found in the database.
 *
 * @author Chris Alan Apaza Aguilar
 */

public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructor for the exception.
     *
     * @param message the message to be displayed when the exception is thrown
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
