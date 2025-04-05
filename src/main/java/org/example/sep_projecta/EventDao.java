// Java
package org.example.sep_projecta;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import java.util.List;

public class EventDao {

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

    public Event getEventById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Event.class, id);
        }
    }

    public List<Event> getAllEvents(String language) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Event e where e.language = :language", Event.class)
                    .setParameter("language", language)
                    .list();
        }
    }


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