package com.jalasoft.todolist.exception.custom;

import lombok.Getter;

import java.util.Map;

/**
 * @author Chris Alan Apaza Aguilar
 */

@Getter
public class FormValidationException extends RuntimeException {
    private final Map<String, String> errors;

    public FormValidationException(Map<String, String> errors) {
        this.errors = errors;
    }
}