package com.jalasoft.todolist.controller;

import com.jalasoft.todolist.model.Todo;
import com.jalasoft.todolist.repository.TodoRepositoryImpl;
import com.jalasoft.todolist.service.TodoService;
import com.jalasoft.todolist.service.TodoServiceImpl;
import jakarta.servlet.RequestDispatcher;
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
    public static final String VIEW_TODO_LIST = "/views/todo-list.jsp";
    public static final String VIEW_TODO_FORM = "/views/todo-form.jsp";
    public static final String TODO_LIST_PATH = "/TodoList";

    private final TodoService service = new TodoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action != null ? action : "") {
            case "":
                showTodoList(request, response);
                break;
            case "new":
                showNewTodoForm(request, response);
                break;
            case "edit":
                showEditTodoForm(request, response);
                break;
            case "delete":
                deleteTodoById(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getPathInfo();
        switch (action != null ? action : "") {
            case "/new":
                createNewTodo(request, response);
                break;
            case "/edit":
                updateTodo(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void showTodoList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageParam = request.getParameter("page");
        int page = pageParam == null ? 0 : Integer.parseInt(pageParam);

        List<Todo> todos = service.getAllTodos(page, PAGE_SIZE);
        long totalTodos = service.count();

        request.setAttribute("todos", todos);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", (int) Math.ceil((double) totalTodos / PAGE_SIZE));

        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TODO_LIST);
        dispatcher.forward(request, response);
    }

    private void showNewTodoForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TODO_FORM);
        dispatcher.forward(request, response);
    }

    private void showEditTodoForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Todo todo = service.getTodoById(id);
        request.setAttribute("todo", todo);

        RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW_TODO_FORM);
        dispatcher.forward(request, response);
    }

    private void createNewTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.createNewTodo(
                request.getParameter("title"),
                request.getParameter("description"),
                request.getParameter("status"),
                request.getParameter("targetDate")
        );
        response.sendRedirect(request.getContextPath() + TODO_LIST_PATH);
    }

    private void updateTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.updateTodo(
                Long.parseLong(request.getParameter("id")),
                request.getParameter("title"),
                request.getParameter("description"),
                request.getParameter("status"),
                request.getParameter("targetDate")
        );
        response.sendRedirect(request.getContextPath() + TODO_LIST_PATH);
    }

    private void deleteTodoById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        service.deleteTodoById(id);
        response.sendRedirect(request.getContextPath() + TODO_LIST_PATH);
    }
}
