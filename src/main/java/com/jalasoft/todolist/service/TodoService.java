package com.jalasoft.todolist.service;

import com.jalasoft.todolist.model.Todo;
import com.jalasoft.todolist.repository.TodoRepository;
import com.jalasoft.todolist.repository.TodoRepositoryImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Chris Alan Apaza Aguilar
 */

public class TodoService {
    private final TodoRepository repository = new TodoRepositoryImpl();

    public List<Todo> getAllTodos() {
        return repository.findAll();
    }

    public void createNewTodo(String title, String description, String status, String targetDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(targetDate, formatter);

        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setStatus(status);
        todo.setTargetDate(date);
        repository.save(todo);
    }
}