package com.jalasoft.todolist.service;

import com.jalasoft.todolist.exception.custom.ResourceNotFoundException;
import com.jalasoft.todolist.model.Todo;
import com.jalasoft.todolist.repository.TodoRepository;
import com.jalasoft.todolist.repository.TodoRepositoryImpl;
import com.jalasoft.todolist.util.DateUtil;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Chris Alan Apaza Aguilar
 */

public class TodoServiceImpl implements TodoService {
    private final TodoRepository repository = new TodoRepositoryImpl();

    @Override
    public List<Todo> getAllTodos(int page, int size) {
        return repository.findAll(page, size);
    }

    @Override
    public Todo getTodoById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void createNewTodo(String title, String description, String status, String targetDate) {
        LocalDate date = DateUtil.parse(targetDate);
        Todo todo = new Todo(title, description, status, date);
        repository.save(todo);
    }

    @Override
    public void updateTodo(Long id, String title, String description, String status, String targetDate) {
        LocalDate date = DateUtil.parse(targetDate);
        Todo todo = repository.findById(id);
        if (todo == null) {
            throw new ResourceNotFoundException("Todo not found with id: " + id);
        }

        todo.setTitle(title);
        todo.setDescription(description);
        todo.setStatus(status);
        todo.setTargetDate(date);
        repository.update(todo);
    }

    @Override
    public void deleteTodoById(Long id) {
        repository.delete(id);
    }

    public long count() {
        return repository.count();
    }
}