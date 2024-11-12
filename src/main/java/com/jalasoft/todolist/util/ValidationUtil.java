package com.jalasoft.todolist.util;

import com.jalasoft.todolist.type.TodoStatusType;
import com.jalasoft.todolist.util.constants.ErrorMessages;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chris Alan Apaza Aguilar
 */

@Getter
public class ValidationUtil {
    private final Map<String, String> errors = new HashMap<>();

    public void validateFieldNotEmpty(String field, String value) {
        if (value == null || value.trim().isEmpty()) {
            errors.put(field, String.format(ErrorMessages.ERROR_MESSAGE_FIELD_REQUIRED, field));
        }
    }

    public void validateStatusType(String field, String status) {
        try {
            TodoStatusType.valueOf(status);
        } catch (IllegalArgumentException e) {
            errors.put(field, String.format(ErrorMessages.ERROR_MESSAGE_FIELD_REQUIRED, field));
        }
    }

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

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
