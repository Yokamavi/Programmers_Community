package com.programmers.programmers_community.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

// Helper class that opens the connection with the database
public class ObjectDbConnection {

    // It automatically creates the "community.odb" file in the root of the project if it doesn't exist
    // It is static and final, so the factory is created only once and shared by the whole app
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("community.odb");

    // Returns a new EntityManager, which is what the DAO uses to work with the database
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Closes the connection with the database. It should be called when the app closes
    public static void closeFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}