package org.example.sep_projecta;

/**
 * Data Transfer Object (DTO) for the {@link User} entity.
 * <p>
 * This class is used to transfer user-related data between different layers
 * of the application, such as between the service layer and the presentation layer.
 * It separates the domain model from the transport or presentation logic.
 * </p>
 * <p>
 * <b>Note:</b> The password field is included only for registration purposes and
 * should be handled securely when used.
 * </p>
 */
public class UserDTO {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean isTeacher;
    private String username;
    private String password; // Password may be included only for registration purposes

    /**
     * Gets the user ID.
     *
     * @return the unique identifier for the user
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the unique identifier for the user
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the first name of the user.
     *
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the first name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email of the user.
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the email address of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the user.
     *
     * @return the phone number of the user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phoneNumber the phone number of the user
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Checks if the user is a teacher.
     *
     * @return {@code true} if the user is a teacher, {@code false} otherwise
     */
    public boolean isTeacher() {
        return isTeacher;
    }

    /**
     * Sets whether the user is a teacher.
     *
     * @param teacher {@code true} if the user is a teacher, {@code false} otherwise
     */
    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     * <p>
     * <b>Warning:</b> The password should be securely handled and never exposed
     * in plain text. This field is only used for registration or authentication purposes.
     * </p>
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * <p>
     * <b>Warning:</b> The password should be securely handled and hashed before storage.
     * </p>
     *
     * @param password the password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
