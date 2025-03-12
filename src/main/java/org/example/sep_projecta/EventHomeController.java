// Java
package org.example.sep_projecta;

import com.gluonhq.charm.glisten.control.CardPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class EventHomeController {

    private Stage stage;

    @FXML
    public CardPane CreatedEventsCardPane;

    @FXML
    private Text myEventsCountLabel;

    @FXML
    public CardPane MyEventsCardPane;

    private UserDao userDao = new UserDao();
    private EventDao eventDao = new EventDao();

    @FXML
    private void browsePage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/eventmanagementbrowsetest.fxml"));
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Event Management Home");
        stage.show();
    }

    @FXML
    private void settingsPage() {
        // Add settings logic if required.
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
        CreatedEventsCardPane.getItems().remove(eventBox);
    }

    @FXML
    public void initialize() {
        int currentUserId = UserDao.getCurrentUserId();
        if (currentUserId == 0) {
            // Optionally warn or redirect if no user is logged in.
            return;
        }
        // Retrieve events the user is attending.
        List<Event> myEvents = userDao.getEventsByUserId(currentUserId);
        MyEventsCardPane.getItems().clear();
        for (Event ev : myEvents) {
            VBox eventBox = new VBox(5);
            eventBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
            Text eventName = new Text(ev.getName());
            Text eventLocation = new Text("Location: " + ev.getLocation());
            Text eventDate = new Text("Date: " + ev.getEventDate().toString());
            Text eventTime = new Text("Time: " + ev.getStartTime().toString());
            eventBox.getChildren().addAll(eventName, eventLocation, eventDate, eventTime);
            MyEventsCardPane.getItems().add(eventBox);
        }
        myEventsCountLabel.setText(String.valueOf(myEvents.size()));

        // Retrieve events created by the user.
        List<Event> createdEvents = userDao.getEventsCreatedByUserId(currentUserId);
        CreatedEventsCardPane.getItems().clear();
        for (Event ev : createdEvents) {
            VBox eventBox = new VBox(5);
            eventBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
            // Store the Event in the VBox user data for deletion.
            eventBox.setUserData(ev);
            Text eventName = new Text(ev.getName());
            Text eventDescription = new Text("Description: " + ev.getDescription());
            Text eventLocation = new Text("Location: " + ev.getLocation());
            Text eventDate = new Text("Date: " + ev.getEventDate().toString());
            Text eventTime = new Text("Time: " + ev.getStartTime().toString());
            Button deleteButton = new Button("Delete");
            deleteButton.setOnMouseClicked(this::deleteEvent);
            eventBox.getChildren().addAll(eventName, eventDescription, eventLocation, eventDate, eventTime, deleteButton);
            CreatedEventsCardPane.getItems().add(eventBox);
        }
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

    @FXML
    private void handleCreateEvent() throws IOException {
        MainApplication.showCreateEventPage();
    }

    @FXML
    private void homePage() {
        // Implement home page logic if needed.
    }
}