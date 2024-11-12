package com.jalasoft.todolist.util;

import com.jalasoft.todolist.util.constants.GeneralConstants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Chris Alan Apaza Aguilar
 */

public class DateUtil {

    public static LocalDate parse(String date) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GeneralConstants.DATE_FORMAT);
        return LocalDate.parse(date, formatter);
    }
}
