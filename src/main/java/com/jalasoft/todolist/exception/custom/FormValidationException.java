package com.jalasoft.todolist.exception.custom;

import lombok.Getter;

import java.util.Map;

/**
 * Custom exception for handling cases where a form validation fails.
 * It contains a map with the errors found in the form.
 *
 * @author Chris Alan Apaza Aguilar
 */

@Getter
public class FormValidationException extends RuntimeException {
    private final Map<String, String> errors;

    /**
     * Constructor for the exception.
     *
     * @param errors the map with the errors found in the form
     */
    public FormValidationException(Map<String, String> errors) {
        this.errors = errors;
    }
}