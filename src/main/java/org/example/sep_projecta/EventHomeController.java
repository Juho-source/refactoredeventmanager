// Java
package org.example.sep_projecta;

import com.gluonhq.charm.glisten.control.CardPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class EventHomeController {

    private MainApplication mainApp;

    private Stage stage;

    @FXML private Button homeButton;
    @FXML private Button browseButton;
    @FXML private Button settingsButton;
    @FXML private Button logoutButton;
    @FXML private Text helloLabel;
    @FXML private Text myEventsLabel;
    @FXML private Tab myEventsTab;
    @FXML private Tab createdEventsTab;
    @FXML private Button createEventButton;

    @FXML
    public CardPane createdEventsCardPane;


    @FXML
    private Text myEventsCountLabel;

    @FXML
    private Text helloUser;

    @FXML
    public CardPane myEventsCardPane;

    private EventDao eventDao = new EventDao();
    private AttendanceDao attendanceDao = new AttendanceDao();

    ResourceBundle rb;

    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }


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

    @FXML
    private void browsePage() {
        try {
            mainApp.showBrowsePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSettings(){
        try {
            mainApp.showSettings();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void deleteEvent(MouseEvent event) {
        Button deleteButton = (Button) event.getSource();
        VBox eventBox = (VBox) deleteButton.getParent();
        // Retrieve the associated Event from the VBox user data.
        Event eventToDelete = (Event) eventBox.getUserData();
        try {
            // Delete event from the database.
            eventDao.deleteEvent(eventToDelete);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // Remove the event box from the CreatedEventsCardPane.
        createdEventsCardPane.getItems().remove(eventBox);
    }

    @FXML
    public void initialize() {
        setLanguage();
        UserDao userDao = new UserDao();
        String username = userDao.getUserById(UserDao.getCurrentUserId()).getUsername();
        int currentUserId = UserDao.getCurrentUserId();
        if (currentUserId == 0) {
            // Optionally warn or redirect if no user is logged in.
            return;
        }
        helloUser.setText(username + "!");
        // Retrieve events the user is attending.
        // Java
        List<Event> myEvents = userDao.getEventsByUserId(currentUserId);
        myEventsCardPane.getItems().clear();
        for (Event ev : myEvents) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventItem.fxml"));
                VBox eventBox = loader.load();
                // Store the Event in the VBox user data
                eventBox.setUserData(ev);
                // Lookup the nodes by their fx:id and update their properties
                Text eventName = (Text) eventBox.lookup("#eventName");
                Text eventLocation = (Text) eventBox.lookup("#eventLocation");
                Text eventDate = (Text) eventBox.lookup("#eventDate");
                Text eventTime = (Text) eventBox.lookup("#eventTime");
                Text eventDescription = (Text) eventBox.lookup("#eventDescription");
                Button cancelAttendanceButton = (Button) eventBox.lookup("#cancelAttendanceButton");

                eventName.setText(ev.getName());
                eventLocation.setText(rb.getString("locationLabelHome") + ": " + ev.getLocation());
                eventDate.setText(rb.getString("dateLabelHome") + ": " + ev.getEventDate().toString());
                eventTime.setText(rb.getString("timeLabelHome") + ": " + ev.getStartTime().toString());
                eventDescription.setText(rb.getString("descriptionLabelHome") + ": " + ev.getDescription());
                cancelAttendanceButton.setText(rb.getString("cancelAttendanceButtonHome"));
                cancelAttendanceButton.setOnMouseClicked(this::cancelAttendance);

                myEventsCardPane.getItems().add(eventBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        myEventsCountLabel.setText(String.valueOf(myEvents.size()));

        // Retrieve events created by the user.
        List<Event> createdEvents = userDao.getEventsCreatedByUserId(currentUserId);
        createdEventsCardPane.getItems().clear();
        for (Event ev : createdEvents) {
            VBox eventBox = new VBox(5);
            eventBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
            // Store the Event in the VBox user data for deletion.
            eventBox.setUserData(ev);
            Text eventName = new Text(ev.getName());
            Text eventDescription = new Text(rb.getString("descriptionLabelHome") + ": " + ev.getDescription());
            Text eventLocation = new Text(rb.getString("locationLabelHome") + ": " + ev.getLocation());
            Text eventDate = new Text(rb.getString("dateLabelHome") + ": " + ev.getEventDate().toString());
            Text eventTime = new Text(rb.getString("timeLabelHome") + ": " + ev.getStartTime().toString());
            Button deleteButton = new Button(rb.getString("deleteEventButtonHome"));
            deleteButton.setOnMouseClicked(this::deleteEvent);
            eventBox.getChildren().addAll(eventName, eventDescription, eventLocation, eventDate, eventTime, deleteButton);
            createdEventsCardPane.getItems().add(eventBox);
        }
    }

    // Java
    @FXML
    private void cancelAttendance(MouseEvent event) {
        Button cancelAttendanceButton = (Button) event.getSource();
        VBox eventBox = (VBox) cancelAttendanceButton.getParent();
        Event eventToCancel = (Event) eventBox.getUserData(); // Retrieve the Event object from user data
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

    @FXML
    private void handleLogout() {
        try {
            mainApp.showLoginScreen();
            UserDao.clearCurrentUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateEvent() throws IOException {
        UserDao userDao = new UserDao();
        if (userDao.checkIfTeacher(UserDao.getCurrentUserId())){ {
            mainApp.showCreateEventPage();
        }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Create Event", "You are not authorized to create events.");
        }
    }

    @FXML
    private void homePage() {
        // Implement home page logic if needed.
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}