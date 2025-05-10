package org.example.sep_projecta;

import com.gluonhq.charm.glisten.control.CardPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller class for the event home page of the application.
 */
public class EventHomeController {

    @FXML private Button homeButton;
    @FXML private Button browseButton;
    @FXML private Button settingsButton;
    @FXML private Button logoutButton;
    @FXML private Text helloLabel;
    @FXML private Text myEventsLabel;
    @FXML private Tab myEventsTab;
    @FXML private Tab createdEventsTab;
    @FXML private Button createEventButton;
    @FXML public CardPane createdEventsCardPane;
    @FXML private Text myEventsCountLabel;
    @FXML private Text helloUser;
    @FXML public CardPane myEventsCardPane;

    private final EventDao eventDao = new EventDao();
    private final AttendanceDao attendanceDao = new AttendanceDao();
    private MainApplication mainApp;

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
     * Sets the language for the UI components based on the current locale.
     */
    public void setLanguage() {
        Locale locale = LocaleManager.getInstance().getCurrentLocale();
        rb = ResourceBundle.getBundle("messages", locale);
        homeButton.setText(rb.getString("homeButton"));
        browseButton.setText(rb.getString("browseButton"));
        settingsButton.setText(rb.getString("settingsButton"));
        logoutButton.setText(rb.getString("logoutButton"));
        helloLabel.setText(rb.getString("helloLabel"));
        myEventsLabel.setText(rb.getString("myEventsLabel"));
        myEventsTab.setText(rb.getString("myEventsTab"));
        createdEventsTab.setText(rb.getString("createdEventsTab"));
        createEventButton.setText(rb.getString("createEventButton"));
    }

    /**
     * Handles the action of navigating to the browse page.
     *
     * @param event the action event
     * @throws IOException if an I/O error occurs
     */
    @FXML
    private void browsePage(ActionEvent event) throws IOException {
        try {
            mainApp.showBrowsePage();
        } catch (IOException e) {
            throw new BrowsePageException("Failed to show browse page", e);
        }
    }

    /**
     * Handles the action of navigating to the settings page.
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
     * Handles the action of deleting an event.
     *
     * @param event the mouse event
     */
    @FXML
    private void deleteEvent(MouseEvent event) {
        Button deleteButton = (Button) event.getSource();
        VBox eventBox = (VBox) deleteButton.getParent();
        Event eventToDelete = (Event) eventBox.getUserData();
        try {
            eventDao.deleteEvent(eventToDelete);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        createdEventsCardPane.getItems().remove(eventBox);
    }

    /**
     * Initializes the controller and sets the language for the UI components.
     */
    @FXML
    public void initialize() {
        setLanguage();
        UserDao userDao = new UserDao();
        String username = userDao.getUserById(
                UserDao.getCurrentUserId()).getUsername();
        int currentUserId = UserDao.getCurrentUserId();
        if (currentUserId == 0) {
            return;
        }
        helloUser.setText(username + "!");
        List<Event> myEvents = userDao.getEventsByUserId(currentUserId);
        myEventsCardPane.getItems().clear();
        for (Event ev : myEvents) {
            VBox eventBox = new VBox(5);
            eventBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10;" +
                    " -fx-border-radius: 5; -fx-background-radius: 5;");
            eventBox.setUserData(ev);
            Text eventName = new Text(ev.getName());
            Text eventLocation = new Text(
                    rb.getString("locationLabelHome") + ": " + ev.getLocation());
            Text eventDate = new Text(
                    rb.getString("dateLabelHome") + ": " + ev.getEventDate().toString());
            Text eventTime = new Text(
                    rb.getString("timeLabelHome") + ": " + ev.getStartTime().toString());
            Text eventDescription = new Text(
                    rb.getString("descriptionLabelHome") + ": " + ev.getDescription());
            Button cancelAttendanceButton = new Button(
                    rb.getString("cancelAttendanceButtonHome"));
            cancelAttendanceButton.setOnMouseClicked(this::cancelAttendance);
            eventBox.getChildren().addAll(
                    eventName, eventLocation, eventDate, eventTime, eventDescription, cancelAttendanceButton);
            myEventsCardPane.getItems().add(eventBox);
        }
        myEventsCountLabel.setText(String.valueOf(myEvents.size()));

        List<Event> createdEvents = userDao.getEventsCreatedByUserId(currentUserId);
        createdEventsCardPane.getItems().clear();
        for (Event ev : createdEvents) {
            VBox eventBox = new VBox(5);
            eventBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10;" +
                    " -fx-border-radius: 5; -fx-background-radius: 5;");
            eventBox.setUserData(ev);
            Text eventName = new Text(ev.getName());
            Text eventDescription = new Text(
                    rb.getString("descriptionLabelHome") + ": " + ev.getDescription());
            Text eventLocation = new Text(
                    rb.getString("locationLabelHome") + ": " + ev.getLocation());
            Text eventDate = new Text(
                    rb.getString("dateLabelHome") + ": " + ev.getEventDate().toString());
            Text eventTime = new Text(
                    rb.getString("timeLabelHome") + ": " + ev.getStartTime().toString());
            Button deleteButton = new Button(
                    rb.getString("deleteEventButtonHome"));
            deleteButton.setOnMouseClicked(
                    this::deleteEvent);
            eventBox.getChildren().addAll(
                    eventName, eventDescription, eventLocation, eventDate, eventTime, deleteButton);
            createdEventsCardPane.getItems().add(eventBox);
        }
    }

    /**
     * Handles the action of canceling attendance for an event.
     *
     * @param event the mouse event
     */
    @FXML
    private void cancelAttendance(MouseEvent event) {
        Button cancelAttendanceButton = (Button) event.getSource();
        VBox eventBox = (VBox) cancelAttendanceButton.getParent();
        Event eventToCancel = (Event) eventBox.getUserData();
        if (eventToCancel == null) {
            System.err.println("Event to cancel is null");
            return;
        }
        try {
            attendanceDao.cancelAttendance(eventToCancel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        myEventsCardPane.getItems().remove(eventBox);
    }

    /**
     * Handles the action of logging out the user.
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
     * Handles the action of navigating to the create event page.
     *
     * @throws IOException if an I/O error occurs
     */
    @FXML
    private void handleCreateEvent() throws IOException {
        UserDao userDao = new UserDao();
        if (userDao.checkIfTeacher(UserDao.getCurrentUserId())) {
            mainApp.showCreateEventPage();
        } else {
            showAlert(Alert.AlertType.ERROR,
                    "Create Event", "You are not authorized to create events.");
        }
    }

    /**
     * Handles the action of navigating to the home page.
     */
    @FXML
    private void homePage() {
        // Implement home page logic if needed.
    }

    /**
     * Shows an alert dialog with the specified type, title, and message.
     *
     * @param alertType the type of the alert
     * @param title the title of the alert
     * @param message the message of the alert
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
