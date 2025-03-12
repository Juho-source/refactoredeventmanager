// Java - src/test/java/org/example/sep_projecta/AttendanceDaoTest.java
package org.example.sep_projecta;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.Query;
import java.util.List;

public class AttendanceDaoTest {

    private AttendanceDao attendanceDao;
    private Event testEvent;
    private User testUser;

    @BeforeEach
    public void setUp() {
        attendanceDao = new AttendanceDao();
        // Create test Event and User and persist them
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            testUser = new User();
            testUser.setUsername("testUser");
            testUser.setPassword("testPassword");
            testUser.setEmail("testUser@example.com");
            testUser.setFirstName("Test");
            testUser.setLastName("User");
            testUser.setPhoneNumber("1234567890");
            // Set required user properties here if needed
            session.persist(testUser);

            testEvent = new Event();
            testEvent.setName("Test Event");
            testEvent.setDescription("Description");
            testEvent.setLocation("Test Location");
            testEvent.setDate(java.time.LocalDate.now());
            testEvent.setStartTime(java.time.LocalTime.now());
            testEvent.setEndTime(java.time.LocalTime.now().plusHours(1));
            testEvent.setCategory("TestCategory");
            testEvent.setAttQuantity(0);
            testEvent.setMaxAttendance(100);
            testEvent.setCreatedBy(testUser);
            session.persist(testEvent);

            tx.commit();
        }
    }

    @AfterEach
    public void tearDown() {
        // Cleanup any persisted data
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("delete from Attendance").executeUpdate();
            session.createQuery("delete from Event").executeUpdate();
            session.createQuery("delete from User").executeUpdate();
            tx.commit();
        }
    }

    @Test
    public void testSaveAttendance() {
        Attendance attendance = new Attendance();
        attendance.setEvent(testEvent);
        attendance.setUser(testUser);

        attendanceDao.saveAttendance(attendance);

        // Verify the attendance was persisted
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Attendance a where a.event = :event and a.user = :user");
            query.setParameter("event", testEvent);
            query.setParameter("user", testUser);
            List results = query.getResultList();

            assertFalse(results.isEmpty(), "Attendance should be saved.");
        }
    }

    @Test
    public void testCancelAttendance() {
        Attendance attendance = new Attendance();
        attendance.setEvent(testEvent);
        attendance.setUser(testUser);

        // Save the attendance first
        attendanceDao.saveAttendance(attendance);

        // Verify it's saved
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Attendance a where a.event = :event");
            query.setParameter("event", testEvent);
            List initialResults = query.getResultList();
            assertFalse(initialResults.isEmpty(), "Attendance should exist before cancellation.");
        }

        // Cancel attendances for the event
        attendanceDao.cancelAttendance(testEvent);

        // Verify attendances have been deleted
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Attendance a where a.event = :event");
            query.setParameter("event", testEvent);
            List resultsAfterCancel = query.getResultList();
            assertTrue(resultsAfterCancel.isEmpty(), "Attendances should be deleted after cancellation.");
        }
    }
}