package com.jalasoft.todolist.helper;

import com.jalasoft.todolist.exception.custom.FormValidationException;
import com.jalasoft.todolist.util.constants.FormFieldNames;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Helper class to manage requests for Todos.
 * This class contains methods to set attributes for a Todo, handle form errors, and forward to a view.
 *
 * @author Chris Alan Apaza Aguilar
 */

public class TodoRequestHelper {

    /**
     * Sets the attributes for a Todo in the request.
     *
     * @param request HttpServletRequest object containing client request
     * @param title the title of the Todo
     * @param description the description of the Todo
     * @param status the status of the Todo
     * @param targetDate the target date of the Todo
     */
    public static void setTodoAttributes(HttpServletRequest request, String title, String description, String status, String targetDate) {
        request.setAttribute(FormFieldNames.TITLE, title);
        request.setAttribute(FormFieldNames.DESCRIPTION, description);
        request.setAttribute(FormFieldNames.STATUS, status);
        request.setAttribute(FormFieldNames.TARGET_DATE, targetDate);
    }

    /**
     * Handles form errors by setting the errors in the request and forwarding to the view.
     *
     * @param request HttpServletRequest object containing client request
     * @param response HttpServletResponse object containing response data
     * @param e the exception containing the errors
     * @param viewPath the path to the view
     * @throws ServletException if request could not be handled
     * @throws IOException if an I/O error occurs
     */
    public static void handleFormErrors(HttpServletRequest request, HttpServletResponse response, FormValidationException e, String viewPath) throws ServletException, IOException {
        request.setAttribute("errors", e.getErrors());
        forwardToView(request, response, viewPath);
    }

    /**
     * Forwards the request to the view.
     *
     * @param request HttpServletRequest object containing client request
     * @param response HttpServletResponse object containing response data
     * @param viewPath the path to the view
     * @throws ServletException if request could not be handled
     * @throws IOException if an I/O error occurs
     */
    public static void forwardToView(HttpServletRequest request, HttpServletResponse response, String viewPath) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}


