package com.jalasoft.todolist.repository;

import com.jalasoft.todolist.model.Todo;
import com.jalasoft.todolist.type.TodoStatusType;
import com.jalasoft.todolist.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for TodoRepositoryImpl.
 * Uses Mockito to mock the EntityManager, EntityManagerFactory, ManagedTransaction, and TypedQuery.
 *
 * @author Chris Alan Apaza Aguilar
 */

class TodoRepositoryImplTest {

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private EntityManagerFactory mockEntityManagerFactory;

    @Mock
    private EntityTransaction mockTransaction;

    @Mock
    private TypedQuery<Todo> mockTypedQuery;

    private TodoRepositoryImpl todoRepository = new TodoRepositoryImpl();

    private List<Todo> mockTodoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockEntityManagerFactory.createEntityManager()).thenReturn(mockEntityManager);
        EntityManagerUtil.setEntityManagerFactory(mockEntityManagerFactory);
    }

    @Test
    void findAllShouldReturnTodos() {
        // Arrange
        mockTodoList.add(new Todo(1L, "Task 1", "Task 1 Description", TodoStatusType.PENDING, LocalDate.now()));
        mockTodoList.add(new Todo(2L, "Task 2", "Task 2 Description", TodoStatusType.COMPLETED, LocalDate.now()));

        when(mockEntityManager.createQuery(anyString(), eq(Todo.class))).thenReturn(mockTypedQuery);
        when(mockTypedQuery.setFirstResult(anyInt())).thenReturn(mockTypedQuery);
        when(mockTypedQuery.setMaxResults(anyInt())).thenReturn(mockTypedQuery);
        when(mockTypedQuery.getResultList()).thenReturn(mockTodoList);

        // Act
        List<Todo> todos = todoRepository.findAll(0, 2);

        // Assert
        assertEquals(2, todos.size());
        assertEquals("Task 1", todos.get(0).getTitle());
        assertEquals("Task 2", todos.get(1).getTitle());
    }

    @Test
    void findByIdShouldReturnTodo() {
        // Arrange
        Todo mockTodo = new Todo(1L, "Task 1", "Task 1 Description", TodoStatusType.PENDING, LocalDate.now());
        when(mockEntityManager.find(Todo.class, 1L)).thenReturn(mockTodo);

        // Act
        Optional<Todo> todoOptional = todoRepository.findById(1L);

        // Assert
        assertTrue(todoOptional.isPresent());
        assertEquals("Task 1", todoOptional.get().getTitle());
    }

    @Test
    void findByIdShouldReturnEmptyOptionalWhenNotFound() {
        // Arrange
        when(mockEntityManager.find(Todo.class, 99L)).thenReturn(null);

        // Act
        Optional<Todo> todoOptional = todoRepository.findById(99L);

        // Assert
        assertFalse(todoOptional.isPresent());
    }

    @Test
    void saveShouldPersistTodo() {
        // Arrange
        Todo newTodo = new Todo(3L, "New Task", "New Task Description", TodoStatusType.PENDING, LocalDate.now());
        doNothing().when(mockEntityManager).persist(newTodo);
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
        when(mockTransaction.isActive()).thenReturn(true);

        // Act
        todoRepository.save(newTodo);

        // Assert
        verify(mockEntityManager, times(1)).persist(newTodo);
    }

    @Test
    void updateShouldMergeTodo() {
        // Arrange
        Todo existingTodo = new Todo(1L, "Task 1", "Task 1 Description", TodoStatusType.PENDING, LocalDate.now());
        when(mockEntityManager.merge(existingTodo)).thenReturn(existingTodo);
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
        when(mockTransaction.isActive()).thenReturn(true);

        // Act
        todoRepository.update(existingTodo);

        // Assert
        verify(mockEntityManager, times(1)).merge(existingTodo);
    }

    @Test
    void deleteShouldRemoveTodo() {
        // Arrange
        Todo todoToDelete = new Todo(1L, "Task 1", "Task 1 Description", TodoStatusType.PENDING, LocalDate.now());
        when(mockEntityManager.find(Todo.class, 1L)).thenReturn(todoToDelete);
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
        when(mockTransaction.isActive()).thenReturn(true);

        // Act
        todoRepository.delete(1L);

        // Assert
        verify(mockEntityManager, times(1)).remove(todoToDelete);
    }
}
