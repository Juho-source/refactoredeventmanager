// Java
package org.example.sep_projecta;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CreateEventController {
    @FXML
    private DatePicker eventDatePicker;
    @FXML
    private TextField eventNameField;
    @FXML
    private TextField eventStartField;
    @FXML
    private TextField eventEndField;
    @FXML
    private TextField eventCategoryField;
    @FXML
    private TextField eventLocationField;
    @FXML
    private TextField eventMaxAttField;
    @FXML
    private TextField eventAttQuantField;
    @FXML
    private TextField eventDescriptionField;
    @FXML
    private Button saveEventButton;

    private EventDao eventDao = new EventDao();
    private UserDao userDao = new UserDao();

    @FXML
    private void initialize() {
        saveEventButton.setOnAction(e -> {
            try {
                saveEvent();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    private void handleLogout() {
        try {
            MainApplication.showLoginScreen();
            UserDao.clearCurrentUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Java
    @FXML
    private void saveEvent() {
        String eventName = eventNameField.getText();
        String startFieldText = eventStartField.getText();
        String endFieldText = eventEndField.getText();
        String eventCategory = eventCategoryField.getText();
        String eventLocation = eventLocationField.getText();
        String eventDescription = eventDescriptionField.getText();
        LocalDate eventDate = eventDatePicker.getValue();

        int maxAttendance;
        int attQuantity;
        try {
            maxAttendance = Integer.parseInt(eventMaxAttField.getText());
            attQuantity = Integer.parseInt(eventAttQuantField.getText());
        } catch (NumberFormatException nfe) {
            Platform.runLater(() -> {
                Alert errorMessageAlert = new Alert(Alert.AlertType.ERROR);
                errorMessageAlert.setContentText("Attendee fields must be numeric!");
                errorMessageAlert.showAndWait();
            });
            return;
        }

        if (eventName.isEmpty() || startFieldText.isEmpty() || endFieldText.isEmpty() ||
                eventCategory.isEmpty() || eventLocation.isEmpty() || eventDescription.isEmpty() ||
                eventDate == null) {

            Platform.runLater(() -> {
                Alert errorMessageAlert = new Alert(Alert.AlertType.ERROR);
                errorMessageAlert.setContentText("Please fill all fields!");
                errorMessageAlert.showAndWait();
            });
            return;
        }

        LocalTime eventStartTime;
        LocalTime eventEndTime;
        try {
            eventStartTime = LocalTime.parse(startFieldText);
            eventEndTime = LocalTime.parse(endFieldText);
        } catch (Exception e) {
            Platform.runLater(() -> {
                Alert errorMessageAlert = new Alert(Alert.AlertType.ERROR);
                errorMessageAlert.setContentText("Invalid time format!");
                errorMessageAlert.showAndWait();
            });
            return;
        }

        LocalDateTime eventDateTime = LocalDateTime.of(eventDate, eventStartTime);
        int creatorId = UserDao.getCurrentUserId();
        if (creatorId == 0) {
            Platform.runLater(() -> {
                Alert errorMessageAlert = new Alert(Alert.AlertType.ERROR);
                errorMessageAlert.setContentText("No user is currently logged in!");
                errorMessageAlert.showAndWait();
            });
            return;
        }

        User creator = userDao.getUserById(creatorId);

        Event event = new Event();
        event.setName(eventName);
        event.setDescription(eventDescription);
        event.setLocation(eventLocation);
        event.setDate(LocalDate.from(eventDateTime));
        event.setStartTime(eventStartTime);
        event.setEndTime(eventEndTime);
        event.setCreatedBy(creator);
        event.setMaxAttendance(maxAttendance);
        event.setAttQuantity(attQuantity);
        // Add the call to set the category field
        event.setCategory(eventCategory);

        eventDao.saveEvent(event);

        eventNameField.clear();
        eventStartField.clear();
        eventEndField.clear();
        eventCategoryField.clear();
        eventLocationField.clear();
        eventDescriptionField.clear();
        eventMaxAttField.clear();
        eventAttQuantField.clear();
        eventDatePicker.setValue(null);
    }
}