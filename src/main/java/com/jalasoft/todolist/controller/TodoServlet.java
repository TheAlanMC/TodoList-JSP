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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chris Alan Apaza Aguilar
 */

@WebServlet("/TodoList/*")
public class TodoServlet extends HttpServlet {
    private final TodoService service = new TodoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Todo> todos = service.getAllTodos();
        request.setAttribute("todos", todos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/todo-list.jsp");
        dispatcher.forward(request, response);
    }
}
