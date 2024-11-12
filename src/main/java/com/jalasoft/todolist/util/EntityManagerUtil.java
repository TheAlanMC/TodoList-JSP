package com.jalasoft.todolist.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * @author Chris Alan Apaza Aguilar
 */

public class EntityManagerUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("defaultPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}