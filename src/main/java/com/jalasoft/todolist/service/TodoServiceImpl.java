package com.jalasoft.todolist.service;

import com.jalasoft.todolist.exception.custom.EntityNotFoundException;
import com.jalasoft.todolist.exception.custom.FormValidationException;
import com.jalasoft.todolist.model.Todo;
import com.jalasoft.todolist.repository.TodoRepository;
import com.jalasoft.todolist.repository.TodoRepositoryImpl;
import com.jalasoft.todolist.type.TodoStatusType;
import com.jalasoft.todolist.util.DateUtil;
import com.jalasoft.todolist.util.ValidationUtil;
import com.jalasoft.todolist.util.constants.ErrorMessages;
import com.jalasoft.todolist.util.constants.FormFieldNames;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Chris Alan Apaza Aguilar
 */

@Slf4j
public class TodoServiceImpl implements TodoService {
    private final TodoRepository repository = new TodoRepositoryImpl();

    @Override
    public List<Todo> getAllTodos(int page, int size) {
        return repository.findAll(page, size);
    }

    @Override
    public Todo getTodoById(Long id) {
        return findTodoByIdOrThrow(id);
    }

    @Override
    public void createNewTodo(String title, String description, String status, String targetDate) {
        validateForm(title, description, status, targetDate);
        Todo todo = new Todo(title, description, TodoStatusType.valueOf(status), DateUtil.parse(targetDate));
        repository.save(todo);
    }

    @Override
    public void updateTodo(Long id, String title, String description, String status, String targetDate) {
        Todo todo = findTodoByIdOrThrow(id);
        validateForm(title, description, status, targetDate);
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setStatus(TodoStatusType.valueOf(status));
        todo.setTargetDate(DateUtil.parse(targetDate));
        repository.update(todo);
    }

    @Override
    public void deleteTodoById(Long id) {
        findTodoByIdOrThrow(id);
        repository.delete(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    private Todo findTodoByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.ERROR_MESSAGE_TODO_NOT_FOUND, id)));
    }

    private void validateForm(String title, String description, String status, String targetDate) {
        ValidationUtil validator = new ValidationUtil();
        validator.validateFieldNotEmpty(FormFieldNames.TITLE, title);
        validator.validateFieldNotEmpty(FormFieldNames.DESCRIPTION, description);
        validator.validateStatusType(FormFieldNames.STATUS, status);
        validator.validateDateRange(FormFieldNames.TARGET_DATE, targetDate);
        if (validator.hasErrors()) {
            throw new FormValidationException(validator.getErrors());
        }
    }
}