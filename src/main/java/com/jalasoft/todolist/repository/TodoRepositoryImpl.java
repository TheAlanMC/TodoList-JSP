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
    public List<Todo> findAll(int page, int size) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        TypedQuery<Todo> query = em.createQuery("SELECT t FROM Todo t ORDER BY t.id", Todo.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Todo> todos = query.getResultList();
        em.close();
        return todos;
    }

    @Override
    public Todo findById(Long id) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        Todo todo = em.find(Todo.class, id);
        em.close();
        return todo;
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
        EntityManager em = EntityManagerUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(todo);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(Long id) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        em.getTransaction().begin();
        Todo todo = em.find(Todo.class, id);
        em.remove(todo);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public long count() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(t) FROM Todo t", Long.class);
        long count = query.getSingleResult();
        em.close();
        return count;
    }
}
