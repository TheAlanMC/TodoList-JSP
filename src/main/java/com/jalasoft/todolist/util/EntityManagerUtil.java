package com.jalasoft.todolist.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Setter;

/**
 * Utility class for entity manager operations.
 * It provides an entity manager factory and a method to get an entity manager.
 *
 * @author Chris Alan Apaza Aguilar
 */

public class EntityManagerUtil {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("defaultPU");

    /**
     * Returns an entity manager.
     *
     * @return the entity manager
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void setEntityManagerFactory(EntityManagerFactory emf) {
        EntityManagerUtil.emf = emf;
    }
}