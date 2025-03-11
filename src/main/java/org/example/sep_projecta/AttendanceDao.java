package org.example.sep_projecta;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class AttendanceDao {

    public void saveAttendance(Attendance attendance) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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

    public void updateAttendance(Attendance attendance) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(attendance);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Attendance getAttendanceById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Attendance.class, id);
        }
    }

    public List<Attendance> getAllAttendances() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Attendance", Attendance.class).list();
        }
    }

    public void deleteAttendance(Attendance attendance) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(attendance);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}