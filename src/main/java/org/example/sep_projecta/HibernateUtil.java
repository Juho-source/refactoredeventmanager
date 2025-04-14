package org.example.sep_projecta;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Utility class for managing Hibernate Sessions.
 */
public class HibernateUtil {

    /**
     * The singleton SessionFactory instance.
     */
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /**
     * Builds the SessionFactory from hibernate.cfg.xml configuration.
     *
     * @return the built SessionFactory.
     * @throws ExceptionInInitializerError if the SessionFactory creation fails.
     */
    private static SessionFactory buildSessionFactory() {
        try {
            // Load configuration from hibernate.cfg.xml
            Configuration configuration = new Configuration().configure();

            // Register all annotated classes
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Event.class);
            configuration.addAnnotatedClass(Attendance.class);

            // Build ServiceRegistry
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            // Build the SessionFactory from configuration and service registry
            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retrieves the singleton SessionFactory.
     *
     * @return the SessionFactory.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Shuts down the SessionFactory, releasing all resources.
     */
    public static void shutdown() {
        getSessionFactory().close();
    }
}
