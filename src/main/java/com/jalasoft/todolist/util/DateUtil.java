package com.jalasoft.todolist.util;

import com.jalasoft.todolist.util.constants.GeneralConstants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for date operations.
 *
 * @author Chris Alan Apaza Aguilar
 */

public class DateUtil {

    /**
     * Returns a string representation of the date in a specific format.
     *
     * @param date the date to be formatted
     * @return the formatted date
     */
    public static LocalDate parse(String date) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GeneralConstants.DATE_FORMAT);
        return LocalDate.parse(date, formatter);
    }
}
