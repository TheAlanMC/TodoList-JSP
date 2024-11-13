package com.jalasoft.todolist.controller;

import com.jalasoft.todolist.exception.custom.EntityNotFoundException;
import com.jalasoft.todolist.exception.custom.FormValidationException;
import com.jalasoft.todolist.helper.TodoRequestHelper;
import com.jalasoft.todolist.model.Todo;
import com.jalasoft.todolist.repository.TodoRepositoryImpl;
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
 * Servlet class to manage all CRUD operations for Todos.
 * This controller handles requests for creating, updating, viewing, and deleting todo items.
 *
 * @author Chris Alan Apaza Aguilar
 */

@WebServlet("/TodoList/*")
public class TodoServlet extends HttpServlet {
    private static final int PAGE_SIZE = 5;
    private static final String PAGE = "page";
    private static final String TODO = "todo";
    private static final String TODOS = "todos";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String TOTAL_PAGES = "totalPages";


    private final TodoService service = new TodoServiceImpl(new TodoRepositoryImpl());

    /**
     * Processes GET requests to perform actions like showing the todo list, displaying the form for a new todo,
     * and editing or deleting existing todos.
     *
     * @param request HttpServletRequest object containing client request
     * @param response HttpServletResponse object containing response data
     * @throws ServletException if request could not be handled
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Processes POST requests for actions like creating or updating a todo item.
     *
     * @param request HttpServletRequest object containing client request
     * @param response HttpServletResponse object containing response data
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Displays the list of todos with pagination.
     *
     * @param request HttpServletRequest object containing client request
     * @param response HttpServletResponse object containing response data
     * @throws ServletException if request could not be handled
     * @throws IOException if an I/O error occurs
     */
    private void showTodoList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageParam = request.getParameter(PAGE);
        int page = pageParam == null ? 0 : Integer.parseInt(pageParam);

        List<Todo> todos = service.getAllTodos(page, PAGE_SIZE);
        long totalTodos = service.count();

        request.setAttribute(TODOS, todos);
        request.setAttribute(CURRENT_PAGE, page);
        request.setAttribute(TOTAL_PAGES, (int) Math.ceil((double) totalTodos / PAGE_SIZE));

        TodoRequestHelper.forwardToView(request, response, ViewPaths.TODO_LIST);
    }

    /**
     * Displays the form for creating a new todo item.
     *
     * @param request HttpServletRequest object containing client request
     * @param response HttpServletResponse object containing response data
     * @throws ServletException if request could not be handled
     * @throws IOException if an I/O error occurs
     */
    private void showNewTodoForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TodoRequestHelper.forwardToView(request, response, ViewPaths.TODO_FORM);
    }

    /**
     * Displays the form for editing an existing todo item.
     *
     * @param request HttpServletRequest object containing client request
     * @param response HttpServletResponse object containing response data
     * @throws ServletException if request could not be handled
     * @throws IOException if an I/O error occurs
     */
    private void showEditTodoForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter(FormFieldNames.ID));
        Todo todo = service.getTodoById(id);
        request.setAttribute(TODO, todo);

        TodoRequestHelper.forwardToView(request, response, ViewPaths.TODO_FORM);
    }

    /**
     * Creates a new todo item with the data provided in the request.
     *
     * @param request HttpServletRequest object containing client request
     * @param response HttpServletResponse object containing response data
     * @throws ServletException if request could not be handled
     * @throws IOException if an I/O error occurs
     */
    private void createNewTodo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    /**
     * Updates an existing todo item with the data provided in the request.
     *
     * @param request HttpServletRequest object containing client request
     * @param response HttpServletResponse object containing response data
     * @throws ServletException if request could not be handled
     * @throws IOException if an I/O error occurs
     */
    private void updateTodo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    /**
     * Deletes a todo item by its ID.
     *
     * @param request HttpServletRequest object containing client request
     * @param response HttpServletResponse object containing response data
     * @throws ServletException if request could not be handled
     * @throws IOException if an I/O error occurs
     */
    private void deleteTodoById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter(FormFieldNames.ID));
        service.deleteTodoById(id);
        TodoRequestHelper.forwardToView(request, response, ViewPaths.TODO_LIST);
    }
}
