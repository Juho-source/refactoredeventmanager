package org.example.sep_projecta;

import jakarta.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user entity in the system.
 * <p>
 * This entity stores personal information
 * as well as relationships to events and attendance records
 * associated with the user.
 * </p>
 */
@Entity
@Table(name = "user")
public class User {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private int userId;

    /**
     * The first name of the user.
     */
    @Column(name = "firstName", nullable = false)
    private String firstName;

    /**
     * The last name of the user.
     */
    @Column(name = "lastName", nullable = false)
    private String lastName;

    /**
     * The email address of the user.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * The phone number of the user.
     */
    @Column(name = "phoneNumber")
    private String phoneNumber;

    /**
     * Flag indicating whether
     * the user is a teacher.
     */
    @Column(name = "isTeacher", nullable = false)
    private boolean isTeacher;

    /**
     * The username of the user.
     */
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    /**
     * The password of the user.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * The set of events created
     * by the user.
     */
    @OneToMany(mappedBy = "createdBy")
    private Set<Event> eventsCreated = new HashSet<>();

    /**
     * The set of attendance records associated with the user.
     */
    @OneToMany(mappedBy = "user")
    private Set<Attendance> attendances = new HashSet<>();

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Constructs a new User with the specified attributes.
     *
     * @param firstName   the first name of the user
     * @param lastName    the last name of the user
     * @param email       the email address of the user
     * @param phoneNumber the phone number of the user
     * @param isTeacher   flag indicating if the user is a teacher
     * @param username    the username for the user account
     * @param password    the password for the user account
     */
    public User(String firstName, String lastName, String email, String phoneNumber,
                boolean isTeacher, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isTeacher = isTeacher;
        this.username = username;
        this.password = password;
    }

    /**
     * Copy constructor for defensive copying.
     *
     * @param source the source User object to copy from
     */
    public User(User source) {
        this.firstName = source.firstName;
        this.lastName = source.lastName;
        this.email = source.email;
        this.phoneNumber = source.phoneNumber;
        this.isTeacher = source.isTeacher;
        this.username = source.username;
        this.password = source.password;
    }

    /**
     * Retrieves the unique identifier of the user.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param userId the new user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the first name of the user.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the last name of the user.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the email address of the user.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the phone number of the user.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phoneNumber the new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Determines whether the user is a teacher.
     *
     * @return true if the user
     * is a teacher, false otherwise
     */
    public boolean isTeacher() {
        return isTeacher;
    }

    /**
     * Sets whether the user is a teacher.
     *
     * @param teacher true if the user
     * should be marked as a teacher, false otherwise
     */
    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the user.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves an unmodifiable set of
     * events created by the user.
     *
     * @return the set of events created
     */
    public Set<Event> getEventsCreated() {
        return Collections.unmodifiableSet(eventsCreated);
    }

    /**
     * Sets the events created by the user.
     * <p>
     * If the provided set is null,
     * an empty set will be used.
     * </p>
     *
     * @param eventsCreated the new set of
     *                      events created
     */
    public void setEventsCreated(Set<Event> eventsCreated) {
        this.eventsCreated = (eventsCreated == null) ?
                new HashSet<>() : new HashSet<>(eventsCreated);
    }

    /**
     * Retrieves an unmodifiable set of
     * attendance records associated with the user.
     *
     * @return the set of attendance records
     */
    public Set<Attendance> getAttendances() {
        return Collections.unmodifiableSet(attendances);
    }

    /**
     * Sets the attendance records for the user.
     * <p>
     * If the provided set is null,
     * an empty set will be used.
     * </p>
     *
     * @param attendances the new set
     * of attendance records
     */
    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = (attendances == null) ?
                new HashSet<>() : new HashSet<>(attendances);
    }
}
