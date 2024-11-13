package com.jalasoft.todolist.util;

import com.jalasoft.todolist.type.TodoStatusType;
import com.jalasoft.todolist.util.constants.ErrorMessages;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for validation operations.
 * It provides methods to validate fields in the application.
 *
 * @author Chris Alan Apaza Aguilar
 */

@Getter
public class ValidationUtil {
    private final Map<String, String> errors = new HashMap<>();

    /**
     * Validates if a field is empty.
     *
     * @param field the field to be validated
     * @param value the value of the field
     */
    public void validateFieldNotEmpty(String field, String value) {
        if (value == null || value.trim().isEmpty()) {
            errors.put(field, String.format(ErrorMessages.ERROR_MESSAGE_FIELD_REQUIRED, field));
        }
    }

    /**
     * Validates if the status type is valid.
     *
     * @param field the field to be validated
     * @param status the status to be validated
     */
    public void validateStatusType(String field, String status) {
        try {
            TodoStatusType.valueOf(status);
        } catch (IllegalArgumentException e) {
            errors.put(field, String.format(ErrorMessages.ERROR_MESSAGE_FIELD_REQUIRED, field));
        }
    }

    /**
     * Validates if the date format is valid.
     *
     * @param field the field to be validated
     * @param date the date to be validated
     */
    public void validateDateRange(String field, String date) {
        try {
            LocalDate targetDate = DateUtil.parse(date);
            LocalDate currentDate = LocalDate.now();
            if (targetDate.isBefore(currentDate)) {
                errors.put(field, ErrorMessages.ERROR_MESSAGE_DATE_RANGE);
            }
        } catch (DateTimeParseException e) {
            errors.put(field, ErrorMessages.ERROR_MESSAGE_DATE_FORMAT);
        }
    }

    /**
     * Checks if there are errors in the validation.
     *
     * @return true if there are errors, false otherwise
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
