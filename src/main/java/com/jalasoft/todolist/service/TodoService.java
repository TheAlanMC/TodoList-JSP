package com.jalasoft.todolist.service;

import com.jalasoft.todolist.model.Todo;

import java.util.List;

/**
 * @author Chris Alan Apaza Aguilar
 */

public interface TodoService {
    List<Todo> getAllTodos(int page, int size);

    Todo getTodoById(Long id);

    void createNewTodo(String title, String description, String status, String targetDate);

    void updateTodo(Long id, String title, String description, String status, String targetDate);

    void deleteTodoById(Long id);

    long count();
}
