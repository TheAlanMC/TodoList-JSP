package com.jalasoft.todolist.controller;

import com.jalasoft.todolist.exception.custom.EntityNotFoundException;
import com.jalasoft.todolist.exception.custom.FormValidationException;
import com.jalasoft.todolist.helper.TodoRequestHelper;
import com.jalasoft.todolist.model.Todo;
import com.jalasoft.todolist.service.TodoService;
import com.jalasoft.todolist.service.TodoServiceImpl;
import com.jalasoft.todolist.util.constants.ActionPaths;
import com.jalasoft.todolist.util.constants.FormFieldNames;
import com.jalasoft.todolist.util.constants.ViewPaths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * @author Chris Alan Apaza Aguilar
 */

@WebServlet("/TodoList/*")
public class TodoServlet extends HttpServlet {
    private static final int PAGE_SIZE = 5;
    private static final String TODO = "todo";
    private static final String TODOS = "todos";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String TOTAL_PAGES = "totalPages";


    private final TodoService service = new TodoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action != null ? action : "") {
                case ActionPaths.QUERY_DEFAULT:
                    showTodoList(request, response);
                    break;
                case ActionPaths.QUERY_NEW:
                    showNewTodoForm(request, response);
                    break;
                case ActionPaths.QUERY_EDIT:
                    showEditTodoForm(request, response);
                    break;
                case ActionPaths.QUERY_DELETE:
                    deleteTodoById(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (EntityNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getPathInfo();
        try {
            switch (action != null ? action : "") {
                case ActionPaths.PATH_NEW:
                    createNewTodo(request, response);
                    break;
                case ActionPaths.PATH_EDIT:
                    updateTodo(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (EntityNotFoundException | ServletException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showTodoList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageParam = request.getParameter(TODO);
        int page = pageParam == null ? 0 : Integer.parseInt(pageParam);

        List<Todo> todos = service.getAllTodos(page, PAGE_SIZE);
        long totalTodos = service.count();

        request.setAttribute(TODOS, todos);
        request.setAttribute(CURRENT_PAGE, page);
        request.setAttribute(TOTAL_PAGES, (int) Math.ceil((double) totalTodos / PAGE_SIZE));

        TodoRequestHelper.forwardToView(request, response, ViewPaths.TODO_LIST);
    }

    private void showNewTodoForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TodoRequestHelper.forwardToView(request, response, ViewPaths.TODO_FORM);
    }

    private void showEditTodoForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter(FormFieldNames.ID));
        Todo todo = service.getTodoById(id);
        request.setAttribute(TODO, todo);

        TodoRequestHelper.forwardToView(request, response, ViewPaths.TODO_FORM);
    }

    private void createNewTodo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String title = request.getParameter(FormFieldNames.TITLE);
        String description = request.getParameter(FormFieldNames.DESCRIPTION);
        String status = request.getParameter(FormFieldNames.STATUS);
        String targetDate = request.getParameter(FormFieldNames.TARGET_DATE);

        try {
            service.createNewTodo(title, description, status, targetDate);
            response.sendRedirect(request.getContextPath() + ActionPaths.PATH_TODO_LIST);
        } catch (FormValidationException e) {
            TodoRequestHelper.setTodoAttributes(request, title, description, status, targetDate);
            TodoRequestHelper.handleFormErrors(request, response, e, ViewPaths.TODO_FORM);
        }
    }

    private void updateTodo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(request.getParameter(FormFieldNames.ID));
        String title = request.getParameter(FormFieldNames.TITLE);
        String description = request.getParameter(FormFieldNames.DESCRIPTION);
        String status = request.getParameter(FormFieldNames.STATUS);
        String targetDate = request.getParameter(FormFieldNames.TARGET_DATE);
        try {
            service.updateTodo(id, title, description, status, targetDate);
            response.sendRedirect(request.getContextPath() + ActionPaths.PATH_TODO_LIST);
        } catch (FormValidationException e) {
            TodoRequestHelper.setTodoAttributes(request, title, description, status, targetDate);
            request.setAttribute(FormFieldNames.ID, id);
            TodoRequestHelper.handleFormErrors(request, response, e, ViewPaths.TODO_FORM);
        }
    }

    private void deleteTodoById(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(request.getParameter(FormFieldNames.ID));
        service.deleteTodoById(id);
        TodoRequestHelper.forwardToView(request, response, ViewPaths.TODO_LIST);
    }
}
