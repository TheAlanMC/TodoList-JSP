package com.jalasoft.todolist.helper;

import com.jalasoft.todolist.exception.custom.FormValidationException;
import com.jalasoft.todolist.util.constants.FormFieldNames;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author Chris Alan Apaza Aguilar
 */

public class TodoRequestHelper {
    public static void setTodoAttributes(HttpServletRequest request, String title, String description, String status, String targetDate) {
        request.setAttribute(FormFieldNames.TITLE, title);
        request.setAttribute(FormFieldNames.DESCRIPTION, description);
        request.setAttribute(FormFieldNames.STATUS, status);
        request.setAttribute(FormFieldNames.TARGET_DATE, targetDate);
    }

    public static void handleFormErrors(HttpServletRequest request, HttpServletResponse response, FormValidationException e, String viewPath) throws ServletException, IOException {
        request.setAttribute("errors", e.getErrors());
        forwardToView(request, response, viewPath);
    }

    public static void forwardToView(HttpServletRequest request, HttpServletResponse response, String viewPath) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}


