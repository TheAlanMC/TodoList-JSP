package com.jalasoft.todolist.controller;

import com.jalasoft.todolist.model.Todo;
import com.jalasoft.todolist.service.TodoService;
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
    private final TodoService service = new TodoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.equals("/")) {
            showTodoList(request, response);
        } else if (action.equals("new")) {
            showNewTodoForm(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getPathInfo();
        if (action.equals("/new")) {
            createNewTodo(request, response);
        }
    }

    private void showTodoList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Todo> todos = service.getAllTodos();
        request.setAttribute("todos", todos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/todo-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewTodoForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/todo-new.jsp");
        dispatcher.forward(request, response);
    }

    private void createNewTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        String targetDate = request.getParameter("targetDate");
        service.createNewTodo(title, description, status, targetDate);
        response.sendRedirect(request.getContextPath() + "/TodoList/");
    }
}
