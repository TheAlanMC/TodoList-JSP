package com.jalasoft.todolist.repository;

import com.jalasoft.todolist.model.Todo;

import java.util.List;

/**
 * @author Chris Alan Apaza Aguilar
 */

public interface TodoRepository {
    List<Todo> findAll(int page, int size);

    Todo findById(Long id);

    void save(Todo todo);

    void update(Todo todo);

    void delete(Long id);

    long count();
}
