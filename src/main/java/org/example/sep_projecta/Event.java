package org.example.sep_projecta;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an Event.
 */
@Entity
@Table(name = "event")
public class Event {

    /**
     * The unique identifier for the event.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventid")
    private int eventId;

    /**
     * The name of the event.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The description of the event.
     */
    @Column(name = "description")
    private String description;

    /**
     * The location where the event will be held.
     */
    @Column(name = "location", nullable = false)
    private String location;

    /**
     * The date of the event.
     */
    @Column(name = "date", nullable = false)
    private LocalDate date;

    /**
     * The start time of the event.
     */
    @Column(name = "startTime", nullable = false)
    private LocalTime startTime;

    /**
     * The end time of the event.
     */
    @Column(name = "endTime", nullable = false)
    private LocalTime endTime;

    /**
     * The category of the event.
     */
    @Column(name = "category")
    private String category;

    /**
     * The current attendance quantity of the event.
     */
    @Column(name = "attQuantity")
    private int attQuantity;

    /**
     * The maximum attendance allowed for the event.
     */
    @Column(name = "maxAttendance")
    private int maxAttendance;

    /**
     * The language of the event.
     */
    @Column(name = "language")
    private String language;

    /**
     * The user who created the event.
     */
    @ManyToOne
    @JoinColumn(name = "creatorID")
    private User createdBy;

    /**
     * The set of attendance records linked to the event.
     */
    @OneToMany(mappedBy = "event", cascade = CascadeType
            .REMOVE, orphanRemoval = true)
    private Set<Attendance> attendances = new HashSet<>();

    /**
     * Retrieves the event ID.
     *
     * @return the event ID
     */
    public int getEventId() {
        return eventId;
    }

    /**
     * Sets the event ID.
     *
     * @param eventId the event ID
     */
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    /**
     * Retrieves the event name.
     *
     * @return the event name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the event name.
     *
     * @param name the event name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the event description.
     *
     * @return the event description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the event description.
     *
     * @param description the event description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the event location.
     *
     * @return the event location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the event location.
     *
     * @param location the event location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the event language.
     *
     * @param language the language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Retrieves the event language.
     *
     * @return the event language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Retrieves the event date.
     *
     * @return the event date
     */
    public LocalDate getEventDate() {
        return date;
    }

    /**
     * Sets the event date.
     *
     * @param date the event date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Retrieves the event start time.
     *
     * @return the start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the event start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Retrieves the event end time.
     *
     * @return the end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the event end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Retrieves the event category.
     *
     * @return the event category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the event category.
     *
     * @param category the event category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Retrieves the current attendance quantity.
     *
     * @return the attendance quantity
     */
    public int getAttQuantity() {
        return attQuantity;
    }

    /**
     * Sets the current attendance quantity.
     *
     * @param attQuantity the attendance quantity
     */
    public void setAttQuantity(int attQuantity) {
        this.attQuantity = attQuantity;
    }

    /**
     * Retrieves the maximum attendance allowed.
     *
     * @return the maximum attendance
     */
    public int getMaxAttendance() {
        return maxAttendance;
    }

    /**
     * Sets the maximum attendance allowed.
     *
     * @param maxAttendance the maximum attendance
     */
    public void setMaxAttendance(int maxAttendance) {
        this.maxAttendance = maxAttendance;
    }

    /**
     * Retrieves the user who created the event.
     *
     * @return the creator of the event
     */
    public User getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user who created the event.
     *
     * @param createdBy the creator of the event
     */
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Retrieves an unmodifiable set of attendance records.
     *
     * @return the set of attendance records
     */
    public Set<Attendance> getAttendances() {
        return Collections.unmodifiableSet(attendances);
    }

    /**
     * Sets the attendance records for the event.
     *
     * @param attendances the set of attendance records
     */
    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }
}
