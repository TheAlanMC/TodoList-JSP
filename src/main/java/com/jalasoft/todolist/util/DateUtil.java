package com.jalasoft.todolist.util;

import com.jalasoft.todolist.exception.custom.ResourceBadRequestException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Chris Alan Apaza Aguilar
 */

public class DateUtil {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static LocalDate parse(String date) {
        try {
            return LocalDate.parse(date, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ResourceBadRequestException("Invalid date format. Please use yyyy-MM-dd");
        }
    }
}
