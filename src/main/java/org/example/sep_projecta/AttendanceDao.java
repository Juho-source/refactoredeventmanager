package org.example.sep_projecta;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Data Access Object for the Attendance entity.
 */
public class AttendanceDao {

    /**
     * Saves the given Attendance record into the database.
     *
     * @param attendance the Attendance record to be saved.
     */
    public void saveAttendance(Attendance attendance) {
        Transaction transaction = null;
        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(attendance);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Cancels all Attendance records associated with the given Event.
     *
     * @param event the Event for which Attendance should be cancelled.
     */
    public void cancelAttendance(Event event) {
        Transaction transaction = null;
        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "delete from Attendance where event = :event";
            session.createQuery(hql)
                    .setParameter("event", event)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
