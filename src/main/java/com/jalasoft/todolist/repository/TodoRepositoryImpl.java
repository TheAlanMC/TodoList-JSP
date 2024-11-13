package com.jalasoft.todolist.repository;

import com.jalasoft.todolist.model.Todo;
import com.jalasoft.todolist.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the TodoRepository interface.
 * Provides CRUD operations for Todo entities using an EntityManager.
 *
 * @author Chris Alan Apaza Aguilar
 */

public class TodoRepositoryImpl implements TodoRepository {

    /**
     * Retrieves a list of Todo entities from the database.
     *
     * @param page the page number
     * @param size the number of elements per page
     * @return a list of Todo entities
     */
    @Override
    public List<Todo> findAll(int page, int size) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        TypedQuery<Todo> query = em.createQuery("SELECT t FROM Todo t ORDER BY t.id DESC", Todo.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Todo> todos = query.getResultList();
        em.close();
        return todos;
    }

    /**
     * Retrieves a Todo entity by its ID.
     *
     * @param id the ID of the Todo entity
     * @return an Optional containing the Todo entity, or an empty Optional if the entity is not found
     */
    @Override
    public Optional<Todo> findById(Long id) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        Todo todo = em.find(Todo.class, id);
        em.close();
        return Optional.ofNullable(todo);
    }

    /**
     * Saves a Todo entity to the database.
     *
     * @param todo the Todo entity to save
     */
    @Override
    public void save(Todo todo) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(todo);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Updates a Todo entity in the database.
     *
     * @param todo the Todo entity to update
     */
    @Override
    public void update(Todo todo) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(todo);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Deletes a Todo entity from the database.
     *
     * @param id the ID of the Todo entity to delete
     */
    @Override
    public void delete(Long id) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        em.getTransaction().begin();
        Todo todo = em.find(Todo.class, id);
        em.remove(todo);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Retrieves the total number of Todo entities in the database.
     *
     * @return the total number of Todo entities
     */
    @Override
    public long count() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(t) FROM Todo t", Long.class);
        long count = query.getSingleResult();
        em.close();
        return count;
    }
}
