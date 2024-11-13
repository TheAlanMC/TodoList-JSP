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
 * Implementation of the TodoService interface.
 * Handles business logic for Todo CRUD operations and validation.
 *
 * @author Chris Alan Apaza Aguilar
 */

@Slf4j
public class TodoServiceImpl implements TodoService {
    private final TodoRepository repository;

    public TodoServiceImpl(TodoRepositoryImpl repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all Todos with pagination.
     *
     * @param page the page number.
     * @param size the number of items per page.
     * @return a list of Todo items.
     */
    @Override
    public List<Todo> getAllTodos(int page, int size) {
        log.info("Getting all todos from page {} with size {}", page, size);
        return repository.findAll(page, size);
    }

    /**
     * Retrieves a Todo by its ID.
     *
     * @param id the ID of the Todo.
     * @return the Todo item.
     */
    @Override
    public Todo getTodoById(Long id) {
        log.info("Getting todo with ID {}", id);
        return findTodoByIdOrThrow(id);
    }

    /**
     * Creates a new Todo item.
     *
     * @param title the title of the Todo.
     * @param description the description of the Todo.
     * @param status the status of the Todo.
     * @param targetDate the target date of the Todo.
     */
    @Override
    public void createNewTodo(String title, String description, String status, String targetDate) {
        log.info("Creating new todo with title: {}, description: {}, status: {}, target date: {}", title, description, status, targetDate);
        validateForm(title, description, status, targetDate);
        Todo todo = new Todo(title, description, TodoStatusType.valueOf(status), DateUtil.parse(targetDate));
        repository.save(todo);
        log.info("Todo created successfully with ID {}", todo.getId());
    }

    /**
     * Updates a Todo item.
     *
     * @param id the ID of the Todo.
     * @param title the title of the Todo.
     * @param description the description of the Todo.
     * @param status the status of the Todo.
     * @param targetDate the target date of the Todo.
     */
    @Override
    public void updateTodo(Long id, String title, String description, String status, String targetDate) {
        log.info("Updating todo with ID {}", id);
        Todo todo = findTodoByIdOrThrow(id);
        validateForm(title, description, status, targetDate);
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setStatus(TodoStatusType.valueOf(status));
        todo.setTargetDate(DateUtil.parse(targetDate));
        repository.update(todo);
        log.info("Todo updated successfully with ID {}", id);
    }

    /**
     * Deletes a Todo item by its ID.
     *
     * @param id the ID of the Todo.
     */
    @Override
    public void deleteTodoById(Long id) {
        log.info("Deleting todo with ID {}", id);
        findTodoByIdOrThrow(id);
        repository.delete(id);
        log.info("Todo deleted successfully with ID {}", id);
    }

    /**
     * Counts all Todo items.
     *
     * @return the total number of Todo items.
     */
    @Override
    public long count() {
        log.info("Counting all todos");
        return repository.count();
    }

    /**
     * Finds a Todo by its ID or throws an exception if not found.
     *
     * @param id the ID of the Todo.
     * @return the Todo item.
     */
    private Todo findTodoByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.ERROR_MESSAGE_TODO_NOT_FOUND, id)));
    }

    /**
     * Validates the form fields for creating or updating a Todo item.
     *
     * @param title the title of the Todo.
     * @param description the description of the Todo.
     * @param status the status of the Todo.
     * @param targetDate the target date of the Todo.
     */
    private void validateForm(String title, String description, String status, String targetDate) {
        log.info("Validating form fields");
        ValidationUtil validator = new ValidationUtil();
        validator.validateFieldNotEmpty(FormFieldNames.TITLE, title);
        validator.validateFieldNotEmpty(FormFieldNames.DESCRIPTION, description);
        validator.validateStatusType(FormFieldNames.STATUS, status);
        validator.validateDateRange(FormFieldNames.TARGET_DATE, targetDate);
        if (validator.hasErrors()) {
            log.error("Validation errors found: {}", validator.getErrors());
            throw new FormValidationException(validator.getErrors());
        }
        log.info("Form fields validated successfully");
    }
}