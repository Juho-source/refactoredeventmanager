package org.example.sep_projecta;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDao {

    // Static field to store the current user's id.
    private static int currentUserId = 0;

    public static void setCurrentUserId(int userId) {
        currentUserId = userId;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void clearCurrentUser() {
        currentUserId = 0;
    }

    // Save a new user to the database.
    public void saveUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Update an existing user.
    public void updateUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Get a user by id.
    public User getUserById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        }
    }

    // Get a list of all users.
    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    // Delete a user.
    public void deleteUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Get events created by the user (assuming a one-to-many relation).
    public List<Event> getEventsCreatedByUserId(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            return new ArrayList<>(user.getEventsCreated());
        }
    }

    // Get events the user is attending via attendances.
    public List<Event> getEventsByUserId(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            return user.getAttendances().stream()
                    .map(Attendance::getEvent)
                    .collect(Collectors.toList());
        }
    }

    // Helper method to retrieve a user by username.
    private User getUserByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from User u where u.username = :username";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        }
    }

    // Authenticate user using username and password.
    public boolean auth(String username, String password) {
        try {
            User user = getUserByUsername(username);
            if (user != null) {
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (result.verified) {
                    // Set the user id statically for use elsewhere.
                    setCurrentUserId(user.getUserId());
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Check if a username already exists.
    public boolean usernameExists(String username) {
        return getUserByUsername(username) != null;
    }

    // Register a new user.
    public boolean register(String firstName, String lastName, String email, String phoneNumber, boolean isTeacher,
                            String username, String password) {
        if (usernameExists(username)) return false;
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        User newUser = new User(firstName, lastName, email, phoneNumber, isTeacher, username, bcryptHashString);
        Transaction transaction = null;
        boolean success = false;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(newUser);
            transaction.commit();
            success = true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return success;
    }
}