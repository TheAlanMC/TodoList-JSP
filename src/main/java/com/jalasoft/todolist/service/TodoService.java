package com.jalasoft.todolist.service;

import com.jalasoft.todolist.model.Todo;
import com.jalasoft.todolist.repository.TodoRepository;
import com.jalasoft.todolist.repository.TodoRepositoryImpl;

import java.util.List;

/**
 * @author Chris Alan Apaza Aguilar
 */

public class TodoService {
    private final TodoRepository repository = new TodoRepositoryImpl();

    public List<Todo> getAllTodos() {
        return repository.findAll();
    }
}