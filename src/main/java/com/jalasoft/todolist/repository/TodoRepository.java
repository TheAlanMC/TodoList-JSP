package com.jalasoft.todolist.repository;

import com.jalasoft.todolist.model.Todo;

import java.util.List;
import java.util.Optional;

/**
 * @author Chris Alan Apaza Aguilar
 */

public interface TodoRepository {
    List<Todo> findAll(int page, int size);

    Optional<Todo> findById(Long id);

    void save(Todo todo);

    void update(Todo todo);

    void delete(Long id);

    long count();
}
