package com.jalasoft.todolist.repository;

import com.jalasoft.todolist.model.Todo;
import com.jalasoft.todolist.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * @author Chris Alan Apaza Aguilar
 */

public class TodoRepositoryImpl implements TodoRepository {

    @Override
    public List<Todo> findAll() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        TypedQuery<Todo> query = em.createQuery("SELECT t FROM Todo t", Todo.class);
        List<Todo> todos = query.getResultList();
        em.close();
        return todos;
    }

    @Override
    public Todo findById(Long id) {
        return null;
    }

    @Override
    public void save(Todo todo) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(todo);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void update(Todo todo) {

    }

    @Override
    public void delete(Long id) {

    }
}
