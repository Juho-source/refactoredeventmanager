package org.example.sep_projecta;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller class for creating events.
 */
public class CreateEventController {

    /**
     * The date picker for selecting event dates.
     */
    @FXML
    private DatePicker eventDatePicker;

    /**
     * Text field for the event name.
     */
    @FXML
    private TextField eventNameField;

    /**
     * Text field for the event start time.
     */
    @FXML
    private TextField eventStartField;

    /**
     * Text field for the event end time.
     */
    @FXML
    private TextField eventEndField;

    /**
     * Text field for the event category.
     */
    @FXML
    private TextField eventCategoryField;

    /**
     * Text field for the event location.
     */
    @FXML
    private TextField eventLocationField;

    /**
     * Text field for the maximum attendance.
     */
    @FXML
    private TextField eventMaxAttField;

    /**
     * Text field for the current attendance quantity.
     */
    @FXML
    private TextField eventAttQuantField;

    /**
     * Text field for the event description.
     */
    @FXML
    private TextField eventDescriptionField;

    /**
     * Button to trigger saving the event.
     */
    @FXML
    private Button saveEventButton;

    /**
     * Button to navigate to the home screen.
     */
    @FXML
    private Button homeButton;

    /**
     * Button to navigate to the browse page.
     */
    @FXML
    private Button browseButton;

    /**
     * Button to navigate to the settings page.
     */
    @FXML
    private Button settingsButton;

    /**
     * Button for logging out.
     */
    @FXML
    private Button logoutButton;

    /**
     * Header text element.
     */
    @FXML
    private Label h1;

    /**
     * Data access object for event operations.
     */
    private final EventDao eventDao = new EventDao();

    /**
     * Data access object for user operations.
     */
    private final UserDao userDao = new UserDao();

    /**
     * Main application reference.
     */
    private MainApplication mainApp;

    /**
     * Resource bundle for localization.
     */
    ResourceBundle rb;

    /**
     * Sets the main application instance.
     *
     * @param mainApp the main application instance
     */
    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Configures the UI text elements based on the specified locale.
     *
     * @param locale the locale to set the language for the UI
     */
    public void setLanguage(Locale locale) {
        LocaleManager.getInstance().setCurrentLocale(locale);
        rb = ResourceBundle.getBundle("messages", locale);
        eventNameField.setPromptText(
                rb.getString("eventNameField"));
        eventStartField.setPromptText(
                rb.getString("eventStartField"));
        eventEndField.setPromptText(
                rb.getString("eventEndField"));
        eventCategoryField.setPromptText(
                rb.getString("eventCategoryField"));
        eventLocationField.setPromptText(
                rb.getString("eventLocationField"));
        eventMaxAttField.setPromptText(
                rb.getString("eventMaxAttField"));
        eventAttQuantField.setPromptText(
                rb.getString("eventAttQuantField"));
        eventDescriptionField.setPromptText(
                rb.getString("eventDescriptionField"));
        saveEventButton.setText(rb.getString("saveEventButton"));
        homeButton.setText(rb.getString("homeButton"));
        browseButton.setText(rb.getString("browseButton"));
        settingsButton.setText(rb.getString("settingsButton"));
        logoutButton.setText(rb.getString("logoutButton"));
        h1.setText(rb.getString("h1"));
    }

    /**
     * Initializes the controller by setting the language
     * and configuring the event save action.
     */
    @FXML
    private void initialize() {
        setLanguage(LocaleManager.getInstance().getCurrentLocale());
        saveEventButton.setOnAction(e -> {
            try {
                saveEvent();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Handles the action of logging out.
     */
    @FXML
    private void handleLogout() {
        try {
            mainApp.showLoginScreen();
            UserDao.clearCurrentUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of navigating to the settings screen.
     */
    @FXML
    private void handleSettings() {
        try {
            mainApp.showSettings();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the navigation to the home page.
     */
    @FXML
    private void homePage() {
        try {
            mainApp.showHomePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the navigation to the browse page.
     */
    @FXML
    private void browsePage() {
        try {
            mainApp.showBrowsePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the event details by validating and processing input fields.
     */
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
                Alert errorMessageAlert = new Alert(
                        Alert.AlertType.ERROR);
                errorMessageAlert.setContentText(
                        "Attendee fields must be numeric!");
                errorMessageAlert.showAndWait();
            });
            return;
        }

        if (eventName.isEmpty()
                || startFieldText.isEmpty()
                || endFieldText.isEmpty()
                || eventCategory.isEmpty()
                || eventLocation.isEmpty()
                || eventDescription.isEmpty()
                || eventDate == null) {

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

        LocalDateTime eventDateTime = LocalDateTime
                .of(eventDate, eventStartTime);
        int creatorId = UserDao.getCurrentUserId();
        if (creatorId == 0) {
            Platform.runLater(() -> {
                Alert errorMessageAlert = new Alert(Alert.AlertType.ERROR);
                errorMessageAlert.setContentText(
                        "No user is currently logged in!");
                errorMessageAlert.showAndWait();
            });
            return;
        }

        UserDTO creatorDTO = userDao.getUserById(creatorId);
        User creator = UserMapper.toEntity(creatorDTO);
        String Language = LocaleManager.getInstance().getLanguageCode();

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
        event.setCategory(eventCategory);
        event.setLanguage(Language);

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
