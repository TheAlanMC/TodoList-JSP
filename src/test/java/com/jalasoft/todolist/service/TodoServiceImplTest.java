package com.jalasoft.todolist.service;

import com.jalasoft.todolist.exception.custom.EntityNotFoundException;
import com.jalasoft.todolist.model.Todo;
import com.jalasoft.todolist.repository.TodoRepositoryImpl;
import com.jalasoft.todolist.type.TodoStatusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for TodoServiceImpl.
 * Uses Mockito to mock the TodoRepositoryImpl.
 *
 * @author Chris Alan Apaza Aguilar
 */

class TodoServiceImplTest {
    @Mock
    private TodoRepositoryImpl mockRepository;

    private TodoServiceImpl todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        todoService = new TodoServiceImpl(mockRepository);
    }

    @Test
    void getAllTodosShouldReturnList() {
        // Arrange
        Todo todo = new Todo("Task 1", "Task 1 Description", TodoStatusType.PENDING, LocalDate.now());
        when(mockRepository.findAll(0, 10)).thenReturn(List.of(todo));

        // Act
        List<Todo> todos = todoService.getAllTodos(0, 10);

        // Assert
        assertEquals(1, todos.size());
        assertEquals("Task 1", todos.get(0).getTitle());
    }

    @Test
    void getTodoByIdShouldReturnTodo() {
        // Arrange
        Todo todo = new Todo("Task 1", "Task 1 Description", TodoStatusType.PENDING, LocalDate.now());
        when(mockRepository.findById(1L)).thenReturn(Optional.of(todo));

        // Act
        Todo result = todoService.getTodoById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Task 1", result.getTitle());
    }

    @Test
    void getTodoByIdShouldThrowEntityNotFoundException() {
        // Arrange
        when(mockRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> todoService.getTodoById(1L));
        assertEquals("The todo with id 1 was not found.", exception.getMessage());
    }

    @Test
    void createNewTodoShouldSaveTodo() {
        // Arrange
        String title = "Task 1";
        String description = "Task 1 Description";
        String status = "PENDING";
        String targetDate = LocalDate.now().toString();

        // Act
        todoService.createNewTodo(title, description, status, targetDate);

        // Assert
        verify(mockRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void updateTodoShouldModifyTodo() {
        // Arrange
        Todo existingTodo = new Todo("Task 1", "Task 1 Description", TodoStatusType.PENDING, LocalDate.now());
        when(mockRepository.findById(1L)).thenReturn(Optional.of(existingTodo));

        // Act
        todoService.updateTodo(1L, "New Title", "New Description", "COMPLETED", "2024-12-31");

        // Assert
        verify(mockRepository, times(1)).update(any(Todo.class));
        assertEquals("New Title", existingTodo.getTitle());
        assertEquals("New Description", existingTodo.getDescription());
        assertEquals(TodoStatusType.COMPLETED, existingTodo.getStatus());
    }

    @Test
    void deleteTodoByIdShouldDeleteTodo() {
        // Arrange
        Todo todo = new Todo("Task 1", "Task 1 Description", TodoStatusType.PENDING, LocalDate.now());
        when(mockRepository.findById(1L)).thenReturn(Optional.of(todo));

        // Act
        todoService.deleteTodoById(1L);

        // Assert
        verify(mockRepository, times(1)).delete(1L);
    }

    @Test
    void deleteTodoByIdShouldThrowEntityNotFoundException() {
        // Arrange
        when(mockRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> todoService.deleteTodoById(1L));
        assertEquals("The todo with id 1 was not found.", exception.getMessage());
    }

    @Test
    void countShouldReturnNumberOfTodos() {
        // Arrange
        when(mockRepository.count()).thenReturn(5L);

        // Act
        long count = todoService.count();

        // Assert
        assertEquals(5L, count);
    }
}