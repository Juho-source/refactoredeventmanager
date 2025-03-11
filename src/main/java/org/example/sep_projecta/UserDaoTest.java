package org.example.sep_projecta;

public class UserDaoTest {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        String testUsername = "JuhoS";
        boolean userExists = userDao.usernameExists(testUsername);

        if (!userExists) {
            boolean registered = userDao.register(
                    "Juho",
                    "User",
                    "testuser@example.com",
                    "1234567890",
                    false,
                    testUsername,
                    "password"
            );
            if (registered) {
                System.out.println("User successfully added.");
            } else {
                System.out.println("Failed to add user.");
            }
        } else {
            System.out.println("Test user already exists.");
        }

        // Find and print user details (first name and username)
        for (User user : userDao.getAllUsers()) {
            if (user.getUsername().equals(testUsername)) {
                System.out.println("First Name: " + user.getFirstName()
                                   + ", Username: " + user.getUsername());
                break;
            }
        }
    }
}