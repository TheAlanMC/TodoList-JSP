package com.jalasoft.todolist.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum class that represents the possible status values for a Todo object.
 * Contains the values Pending, In Progress, and Completed.
 *
 * @author Chris Alan Apaza Aguilar
 */

@Getter
@AllArgsConstructor
public enum TodoStatusType {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String value;
}