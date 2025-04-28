package org.example.sep_projecta;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Access Object (DAO) class for performing CRUD operations on User entities.
 * <p>
 * This class supports operations such as saving, updating, retrieving, and deleting users,
 * as well as handling user authentication and registration.
 * </p>
 */
public class UserDao {

    /**
     * Static field to store the current user's id.
     */
    private static int currentUserId = 0;

    /**
     * Sets the current user's id.
     *
     * @param userId the id of the user to set as the current user
     */
    public static void setCurrentUserId(int userId) {
        currentUserId = userId;
    }

    /**
     * Retrieves the current user's id.
     *
     * @return the current user's id
     */
    public static int getCurrentUserId() {
        return currentUserId;
    }

    /**
     * Clears the current user's id.
     */
    public static void clearCurrentUser() {
        currentUserId = 0;
    }

    /**
     * Saves a new user to the database.
     *
     * @param userDTO the data transfer object representing the user to be saved
     */
    public void saveUser(UserDTO userDTO) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // Convert DTO to entity
            User userEntity = UserMapper.toEntity(userDTO);
            session.persist(userEntity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().
                    isOneOf(org.hibernate.resource
                            .transaction.spi.TransactionStatus.ACTIVE)) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing user in the database.
     * <p>
     * Only editable fields are updated; the password remains unchanged.
     * </p>
     *
     * @param userDTO the data transfer object containing the updated user information
     */
    public void updateUser(UserDTO userDTO) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // Retrieve the persistent user entity
            User persistentUser = session.get(User.class, userDTO.getUserId());
            if (persistentUser != null) {
                // Update only the editable fields (keep the old password)
                persistentUser.setFirstName(userDTO.getFirstName());
                persistentUser.setLastName(userDTO.getLastName());
                persistentUser.setEmail(userDTO.getEmail());
                persistentUser.setPhoneNumber(userDTO.getPhoneNumber());
                // password remains unchanged
                session.update(persistentUser);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().isOneOf(
                    org.hibernate.resource
                            .transaction.spi.TransactionStatus.ACTIVE,
                    org.hibernate.resource
                            .transaction.spi.TransactionStatus.MARKED_ROLLBACK)) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a user based on the user id.
     *
     * @param id the id of the user to retrieve
     * @return a UserDTO representing the user, or null if not found
     */
    public UserDTO getUserById(int id) {
        try (Session session = HibernateUtil.
                getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            return UserMapper.toDTO(user);
        }
    }

    /**
     * Retrieves a list of all users.
     *
     * @return a List of UserDTO objects representing all users in the database
     */
    public List<UserDTO> getAllUsers() {
        try (Session session = HibernateUtil.
                getSessionFactory().openSession()) {
            List<User> users = session.createQuery(
                    "from User", User.class).list();
            return users.stream().map(UserMapper::toDTO).
                    collect(Collectors.toList());
        }
    }

    /**
     * Deletes a user from the database.
     * <p>
     * This method removes the user from the createdBy field of associated events and from attendance records before deletion.
     * </p>
     *
     * @param user the User entity to be deleted
     */
    public void deleteUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // Retrieve the persistent user instance from the session
            User persistentUser = session.get(User.class, user.getUserId());
            if (persistentUser == null) {
                System.err.println("User not found in the database.");
                return;
            }

            // Delete attendance records associated with the persistent user
            List<Attendance> attendances = session
                    .createQuery("from Attendance where user = :user", Attendance.class)
                    .setParameter("user", persistentUser)
                    .list();
            for (Attendance attendance : attendances) {
                session.remove(attendance);
            }

            // Update events created by the user
            List<Event> events = session
                    .createQuery("from Event where createdBy = :user", Event.class)
                    .setParameter("user", persistentUser)
                    .list();
            for (Event event : events) {
                event.setCreatedBy(null);
                session.update(event);
            }

            // Remove the persistent user instance
            session.remove(persistentUser);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the events created by a specific user.
     *
     * @param userId the id of the user whose created events are to be retrieved
     * @return a List of Event objects created by the user
     */
    public List<Event> getEventsCreatedByUserId(int userId) {
        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            return new ArrayList<>(user.getEventsCreated());
        }
    }

    /**
     * Retrieves the events that a user is attending.
     *
     * @param userId the id of the user whose attending events are to be retrieved
     * @return a List of Event objects the user is attending
     */
    public List<Event> getEventsByUserId(int userId) {
        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            return user.getAttendances().stream()
                    .map(Attendance::getEvent)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Helper method to retrieve a user by username.
     *
     * @param username the username of the user to retrieve
     * @return the User entity matching the username, or null if not found
     */
    private User getUserByUsername(String username) {
        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {
            String hql = "from User u where u.username = :username";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        }
    }

    /**
     * Authenticates a user by validating the given username and password.
     * <p>
     * If authentication is successful, the user's id is set as the current user.
     * </p>
     *
     * @param username the username of the user attempting to authenticate
     * @param password the password provided for authentication
     * @return true if authentication is successful, false otherwise
     */
    public boolean auth(String username, String password) {
        try {
            User user = getUserByUsername(username);
            if (user != null) {
                BCrypt.Result result = BCrypt.verifyer()
                        .verify(password.toCharArray(), user.getPassword());
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

    /**
     * Checks if a user is a teacher.
     *
     * @param userId the id of the user to check
     * @return true if the user is a teacher, false otherwise
     */
    public boolean checkIfTeacher(int userId) {
        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            return user.isTeacher();
        }
    }

    /**
     * Checks if a given username already exists.
     *
     * @param username the username to check for existence
     * @return true if the username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        return getUserByUsername(username) != null;
    }

    /**
     * Registers a new user by hashing the password and persisting the user entity.
     *
     * @param userDTO the data transfer object containing the new user's details
     * @return true if registration is successful, false otherwise
     */
    public boolean register(UserDTO userDTO) {
        if (usernameExists(userDTO.getUsername())) return false;
        // Hash the password before converting
        String bcryptHashString = BCrypt.withDefaults()
                .hashToString(12, userDTO.getPassword().toCharArray());
        userDTO.setPassword(bcryptHashString);
        User user = UserMapper.toEntity(userDTO);
        Transaction transaction = null;
        boolean success = false;
        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            success = true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return success;
    }

}
