package org.example.sep_projecta;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import java.util.List;

/**
 * Data Access Object for handling operations on Event entities.
 */
public class EventDao {

    /**
     * Persists the given Event entity to the database.
     *
     * @param event the Event entity to be saved.
     */
    public void saveEvent(Event event) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(event);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Merges the provided Event entity into the current persistence context.
     *
     * @param event the Event entity to be updated.
     */
    public void updateEvent(Event event) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(event);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Retrieves an Event entity by its unique identifier.
     *
     * @param id the unique identifier of the Event.
     * @return the Event entity if found; otherwise, null.
     */
    public Event getEventById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Event.class, id);
        }
    }

    /**
     * Retrieves all Event entities for a given language.
     *
     * @param language the language filter for events.
     * @return a List of Event entities matching the language.
     */
    public List<Event> getAllEvents(String language) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Event e where e.language = :language", Event.class)
                    .setParameter("language", language)
                    .list();
        }
    }

    /**
     * Deletes the specified Event entity from the database.
     *
     * @param event the Event entity to be deleted.
     */
    public void deleteEvent(Event event) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(event);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
