package org.example.sep_projecta;

import jakarta.persistence.*;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendanceID")
    private int attendanceId;

    @ManyToOne
    @JoinColumn(name = "eventID", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    // Getters and setters

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}