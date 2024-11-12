package com.jalasoft.todolist.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
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