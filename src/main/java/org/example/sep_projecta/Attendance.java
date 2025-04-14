package org.example.sep_projecta;

import jakarta.persistence.*;

/**
 * Represents an Attendance record that links a user to an event.
 */
@Entity
@Table(name = "attendance")
public class Attendance {

    /**
     * The unique identifier for the attendance record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendanceID")
    private int attendanceId;

    /**
     * The event associated with this attendance.
     */
    @ManyToOne
    @JoinColumn(name = "eventID", nullable = false)
    private Event event;

    /**
     * The user associated with this attendance.
     */
    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    /**
     * Retrieves the attendance ID.
     *
     * @return the attendance ID
     */
    public int getAttendanceId() {
        return attendanceId;
    }

    /**
     * Sets the attendance ID.
     *
     * @param attendanceId the new attendance ID
     */
    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    /**
     * Retrieves the event associated with this attendance.
     *
     * @return the event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets the event associated with this attendance.
     *
     * @param event the event to set
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Retrieves the user associated with this attendance.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with this attendance.
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
