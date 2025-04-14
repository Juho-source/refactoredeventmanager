// File: `src/test/java/org/example/sep_projecta/UserDaoTest.java`
/*package org.example.sep_projecta;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDaoTest {

    private UserDao userDao;
    // A test user used for most tests.
    private UserDTO testUser;

    @BeforeEach
    void setUp() {
        userDao = new UserDao();
        // Create a new user instance. Depending on your mappings, userId
        // may be generated when the user is persisted.
        testUser = new UserDTO("John", "Doe", "john@example.com", "1234567890", false, "johndoe", "password");
        // Save for testing; if the user already exists, delete first.
        userDao.saveUser(testUser);
    }

    @AfterEach
    void tearDown() {
        // Remove test user and clear current user state.
        if (testUser.getUserId() > 0) {
            userDao.deleteUser(testUser);
        }
        UserDao.clearCurrentUser();
    }

    @Test
    void testSaveAndGetUserById() {
        // Retrieve the user by its id.
        User retrieved = userDao.getUserById(testUser.getUserId());
        assertNotNull(retrieved);
        assertEquals(testUser.getUsername(), retrieved.getUsername());
    }

    @Test
    void testUpdateUser() {
        // Update user email.
        testUser.setEmail("newemail@example.com");
        userDao.updateUser(testUser);
        User updated = userDao.getUserById(testUser.getUserId());
        assertEquals("newemail@example.com", updated.getEmail());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = userDao.getAllUsers();
        assertNotNull(users);
        // The list should contain at least our test user.
        assertTrue(users.stream().anyMatch(u -> u.getUserId() == testUser.getUserId()));
    }

    @Test
    void testDeleteUser() {
        // Create a user specifically for deletion.
        User deleteUser = new User("Jane", "Doe", "jane@example.com", "0987654321", true, "janedoe", "password");
        userDao.saveUser(deleteUser);
        // Delete the user.
        userDao.deleteUser(deleteUser);
        User deleted = userDao.getUserById(deleteUser.getUserId());
        assertNull(deleted);
    }

    @Test
    void testRegisterAndUsernameExists() {
        String username = "newuser";
        // Register a new user.
        boolean registered = userDao.register("Alice", "Smith", "alice@example1.com", "5551234", false, username, "password");
        assertTrue(registered);
        // Verify that the username exists.
        assertTrue(userDao.usernameExists(username));

        // Cleanup: remove the registered user.
        List<User> users = userDao.getAllUsers();
        users.stream()
                .filter(u -> u.getUsername().equals("newuser"))
                .findFirst()
                .ifPresent(u -> userDao.deleteUser(u));
    }

    @Test
    void testAuth() {
        String username = "authuser";
        // Register a user for authentication.
        boolean registered = userDao.register("Bob", "Marley", "bob@example.com", "5556789", false, username, "password123");
        assertTrue(registered);

        boolean authSuccess = userDao.auth(username, "password123");
        assertTrue(authSuccess);
        // Verify that the current user id is set after authentication.
        List<User> users = userDao.getAllUsers();
        users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .ifPresent(u -> assertEquals(u.getUserId(), UserDao.getCurrentUserId()));

        // Cleanup: remove the authenticated user.
        users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .ifPresent(u -> userDao.deleteUser(u));
    }

    @Test
    void testGetEventsByUserId() {
        // Assuming the test user has attendances, the returned list should not be null.
        List<Event> events = userDao.getEventsByUserId(testUser.getUserId());
        assertNotNull(events);
    }

    @Test
    void testGetEventsCreatedByUserId() {
        // Assuming the test user has created events, the returned list should not be null.
        List<Event> events = userDao.getEventsCreatedByUserId(testUser.getUserId());
        assertNotNull(events);
    }
}*/