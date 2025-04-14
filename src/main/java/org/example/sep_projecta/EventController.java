package org.example.sep_projecta;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class EventController {
    @FXML
    private FlowPane eventFlowPane;
    @FXML
    private TextField searchField;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Menu categoryMenu;
    @FXML
    private Menu locationMenu;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button settingsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button browseButton;
    @FXML
    private Button homeButton;
    @FXML
    private Text eventLabelTop;


    ResourceBundle rb;
    private MainApplication mainApp;

    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }



    public void setLanguage() {
        Locale locale = LocaleManager.getInstance().getCurrentLocale();
        rb = ResourceBundle.getBundle("messages", locale);
        categoryMenu.setText(rb.getString("categoryMenu"));
        locationMenu.setText(rb.getString("locationMenu"));
        settingsButton.setText(rb.getString("settingsButton"));
        logoutButton.setText(rb.getString("logoutButton"));
        browseButton.setText(rb.getString("browseButton"));
        homeButton.setText(rb.getString("homeButton"));
        eventLabelTop.setText(rb.getString("eventLabelTop"));

    }


    private List<Event> eventList;
    private ObservableList<MenuItem> categoryMenuItems;
    private ObservableList<MenuItem> locationMenuItems;
    private Stage stage;

    private EventDao eventDao = new EventDao();
    private AttendanceDao attendanceDao = new AttendanceDao();
    private UserDao userDao = new UserDao();

    public EventController() {
        try {
            eventList = eventDao.getAllEvents();
        } catch (Exception e) {
            e.printStackTrace();
            eventList = FXCollections.observableArrayList();
        }
        categoryMenuItems = FXCollections.observableArrayList();
        locationMenuItems = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        setLanguage();
        // Bind the FlowPane wrap length to the ScrollPane width
        scrollPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) ->
                eventFlowPane.prefWrapLengthProperty().set(newValue.getWidth())
        );

        // Populate menu items from eventList
        populateMenuItems(categoryMenuItems, "category");
        populateMenuItems(locationMenuItems, "location");

        Bindings.bindContent(categoryMenu.getItems(), categoryMenuItems);
        Bindings.bindContent(locationMenu.getItems(), locationMenuItems);

        // Add listeners on CheckMenuItems
        categoryMenu.getItems().forEach(item -> {
            if (item instanceof CheckMenuItem) {
                ((CheckMenuItem) item).selectedProperty().addListener((obs, oldVal, newVal) -> searchEvents());
            }
        });
        locationMenu.getItems().forEach(item -> {
            if (item instanceof CheckMenuItem) {
                ((CheckMenuItem) item).selectedProperty().addListener((obs, oldVal, newVal) -> searchEvents());
            }
        });

        // Add event cards
        eventList.forEach(e -> eventFlowPane.getChildren().add(createEventCard(e)));

        // Listeners for search and date filter
        searchField.textProperty().addListener((obs, oldVal, newVal) -> searchEvents());
        datePicker.valueProperty().addListener((obs, oldVal, newVal) -> searchEvents());
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
    private void attendEvent(MouseEvent event) {
        Button attendButton = (Button) event.getSource();
        try {
            int eventId = Integer.parseInt(attendButton.getId());
            int currentUserId = UserDao.getCurrentUserId();
            if (currentUserId == 0) {
                showAlert(Alert.AlertType.ERROR, "Attendance Error", "No user is currently logged in!");
                return;
            }
            User currentUser = userDao.getUserById(currentUserId);
            Event attendedEvent = eventDao.getEventById(eventId);
            if (attendedEvent == null) {
                showAlert(Alert.AlertType.ERROR, "Attendance Error", "Event not found!");
                return;
            }
            Attendance attendance = new Attendance();
            attendance.setUser(currentUser);
            attendance.setEvent(attendedEvent);
            attendanceDao.saveAttendance(attendance);
            showAlert(Alert.AlertType.INFORMATION, "Attendance", "You are now attending the event!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void populateMenuItems(ObservableList<MenuItem> menuItems, String column) {
        // Compute distinct values from eventList based on the requested column.
        // If column is "location", use event location; if "category", use a placeholder since Event has no category field.
        Set<String> values;
        if ("location".equals(column)) {
            values = eventList.stream()
                    .map(Event::getLocation)
                    .collect(Collectors.toSet());
        } else if ("category".equals(column)) {
            values = eventList.stream()
                    .map(Event::getCategory) // Using category
                    .collect(Collectors.toSet());
        } else {
            values = FXCollections.observableSet();
        }
        values.forEach(val -> menuItems.add(new CheckMenuItem(val)));
    }

    private StackPane createEventCard(Event event) {
        StackPane cardPane = new StackPane();
        cardPane.getStyleClass().add("card-pane");
        cardPane.setPrefSize(150, 100);

        VBox vBox = new VBox(5);
        vBox.getStyleClass().add("card-vbox");

        // Use correct getters from Event entity.
        Text nameText = new Text(event.getName());
        Text locationText = new Text(rb.getString("locationLabelBrowse") + ": " + event.getLocation());
        Text dateText = new Text(rb.getString("dateLabelBrowse") + ": " + event.getEventDate().toString());
        Text descriptionText = new Text(rb.getString("descriptionLabelBrowse") + ": " + event.getDescription());
        descriptionText.setWrappingWidth(130);
        TextFlow descriptionFlow = new TextFlow(descriptionText);

        // Display event time using the startTime field.
        HBox timeBox = new HBox(5);
        timeBox.getStyleClass().add("time-box");
        StackPane timePane = new StackPane();
        timePane.getStyleClass().add("time-pane");
        Text timeText = new Text(event.getStartTime().toString() + "-" + event.getEndTime().toString());
        timeText.getStyleClass().add("time-text");
        timePane.getChildren().add(timeText);
        timeBox.getChildren().add(timePane);

        Button attendButton = new Button(rb.getString("attendButtonBrowse"));
        attendButton.setId(String.valueOf(event.getEventId()));
        attendButton.setOnMouseClicked(this::attendEvent);

        vBox.getChildren().addAll(nameText, locationText, dateText, descriptionFlow, timeBox, attendButton);
        cardPane.getChildren().add(vBox);
        return cardPane;
    }

    public List<Event> filterEvents(String searchText, LocalDate selectedDate) {
        // Filter events by search text, selected date and selected menu items.
        List<String> selectedCategories = categoryMenu.getItems().stream()
                .filter(item -> item instanceof CheckMenuItem && ((CheckMenuItem) item).isSelected())
                .map(MenuItem::getText)
                .collect(Collectors.toList());

        List<String> selectedLocations = locationMenu.getItems().stream()
                .filter(item -> item instanceof CheckMenuItem && ((CheckMenuItem) item).isSelected())
                .map(MenuItem::getText)
                .collect(Collectors.toList());

        return eventList.stream()
                .filter(e -> (searchText.isEmpty() || e.getName().toLowerCase().contains(searchText)
                        || e.getLocation().toLowerCase().contains(searchText)
                        || e.getDescription().toLowerCase().contains(searchText)))
                .filter(e -> selectedDate == null || e.getEventDate().equals(selectedDate))
                .filter(e -> selectedCategories.isEmpty() || selectedCategories.contains(e.getCategory()))
                .filter(e -> selectedLocations.isEmpty() || selectedLocations.contains(e.getLocation()))
                .collect(Collectors.toList());
    }

    @FXML
    public void searchEvents() {
        String searchText = searchField.getText().toLowerCase();
        LocalDate selectedDate = datePicker.getValue();
        List<Event> filteredEvents = filterEvents(searchText, selectedDate);
        eventFlowPane.getChildren().clear();
        filteredEvents.forEach(e -> eventFlowPane.getChildren().add(createEventCard(e)));
    }

    @FXML
    public void handleLogout() {
        try {
            mainApp.showLoginScreen();
            UserDao.clearCurrentUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void homePage() {
        try {
            mainApp.showHomePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public ObservableList<MenuItem> getCategoryMenuItems() {
        return categoryMenuItems;
    }

    public ObservableList<MenuItem> getLocationMenuItems() {
        return locationMenuItems;
    }
}