// File: src/test/java/org/example/sep_projecta/EventDaoTest.java
package org.example.sep_projecta;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class EventDaoTest {

    private EventDao eventDao;
    private Event event;
    private User persistedUser;

    @BeforeEach
    void setUp() {
        eventDao = new EventDao();
        event = new Event();
        event.setName("Test Event");
        event.setDescription("Event for testing");
        event.setLocation("Test Location");
        event.setDate(LocalDate.now());
        event.setStartTime(LocalTime.of(10, 0));
        event.setEndTime(LocalTime.of(12, 0));
        event.setCategory("Test Category");
        event.setAttQuantity(0);
        event.setMaxAttendance(100);
        // Generate unique email and username each time to avoid duplicate entries.
        String uniqueEmail = "john.doe" + System.nanoTime() + "@example.com";
        String uniqueUsername = "testuser" + System.nanoTime();
        persistedUser = persistUser(new User("John", "Doe", uniqueEmail,
                "1234567890", false, uniqueUsername, "secret"));
        // Set the User to the Event.
        event.setCreatedBy(persistedUser);
    }

    @AfterEach
    void tearDown() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // Remove any events created by the test user.
            List<Event> events = session.createQuery("from Event where createdBy.userId = :userId", Event.class)
                    .setParameter("userId", persistedUser.getUserId())
                    .list();
            for (Event e : events) {
                session.remove(e);
            }
            // Remove the test user.
            User user = session.get(User.class, persistedUser.getUserId());
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private User persistUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    @Test
    void saveEvent() {
        eventDao.saveEvent(event);
        assertTrue(event.getEventId() > 0, "Event id should be set after saving");

        Event fetchedEvent = eventDao.getEventById(event.getEventId());
        assertNotNull(fetchedEvent, "Fetched event should not be null");
        assertEquals(event.getName(), fetchedEvent.getName(), "Event name should match");
        assertNotNull(fetchedEvent.getCreatedBy(), "CreatedBy should not be null");
        assertEquals(persistedUser.getUserId(), fetchedEvent.getCreatedBy().getUserId(), "User id should match");
    }

    @Test
    void updateEvent() {
        eventDao.saveEvent(event);
        event.setName("Updated Event Name");
        eventDao.updateEvent(event);

        Event fetchedEvent = eventDao.getEventById(event.getEventId());
        assertEquals("Updated Event Name", fetchedEvent.getName(), "Event name should be updated");
    }

    @Test
    void getEventById() {
        eventDao.saveEvent(event);
        Event fetchedEvent = eventDao.getEventById(event.getEventId());
        assertNotNull(fetchedEvent, "Fetched event should not be null");
        assertEquals(event.getEventId(), fetchedEvent.getEventId(), "Event id should match");
    }

    @Test
    void getAllEvents() {
        Event event2 = new Event();
        event2.setName("Second Event");
        event2.setDescription("Another test event");
        event2.setLocation("Another Location");
        event2.setDate(LocalDate.now());
        event2.setStartTime(LocalTime.of(13, 0));
        event2.setEndTime(LocalTime.of(15, 0));
        event2.setCategory("Test");
        event2.setAttQuantity(0);
        event2.setMaxAttendance(50);
        // Reuse the same persisted user.
        event2.setCreatedBy(persistedUser);

        eventDao.saveEvent(event);
        eventDao.saveEvent(event2);

        List<Event> events = eventDao.getAllEvents();
        assertTrue(events.size() >= 2, "There should be at least two events");
    }

    @Test
    void deleteEvent() {
        eventDao.saveEvent(event);
        int eventId = event.getEventId();
        eventDao.deleteEvent(event);

        Event fetchedEvent = eventDao.getEventById(eventId);
        assertNull(fetchedEvent, "Deleted event should be null");
    }
}