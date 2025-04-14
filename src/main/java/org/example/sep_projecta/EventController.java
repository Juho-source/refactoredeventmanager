package org.example.sep_projecta;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller class for handling event related operations.
 */
public class EventController {
    /**
     * Flowpane for displaying event cards.
     */
    @FXML
    private FlowPane eventFlowPane;
    /**
     * Textfield for inputting search queries.
     */
    @FXML
    private TextField searchField;
    /**
     * ScrollPane for scrolling through event cards.
     */
    @FXML
    private ScrollPane scrollPane;
    /**
     * Category menu for filtering events by category.
     */
    @FXML
    private Menu categoryMenu;
    /**
     * Location menu for filtering events by location.
     */
    @FXML
    private Menu locationMenu;
    /**
     * DatePicker for selecting event dates.
     */
    @FXML
    private DatePicker datePicker;
    /**
     * Settigns button for navigating to settings.
     */
    @FXML
    private Button settingsButton;
    /**
     * Logout button for logging out of the application.
     */
    @FXML
    private Button logoutButton;
    /**
     * Browse button for navigating to the browse events page.
     */
    @FXML
    private Button browseButton;
    /**
     * Home button for navigating to the home page.
     */
    @FXML
    private Button homeButton;
    /**
     * Label for displaying the event title at the top of the page.
     */
    @FXML
    private Text eventLabelTop;

    /**
     * Resource bundle for locale-specific texts.
     */
    ResourceBundle rb;

    /**
     * Reference to the main application.
     */
    private MainApplication mainApp;

    /**
     * Sets the main application instance.
     *
     * @param mainApp the main application instance.
     */
    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Sets the language for UI components using the current locale.
     */
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
    /**
     * List that stores all events.
     */
    private List<Event> eventList;
    /**
     * Observable list of menu items for categories.
     */
    private final ObservableList<MenuItem> categoryMenuItems;
    /**
     * Observable list of menu items for locations.
     */
    private final ObservableList<MenuItem> locationMenuItems;

    /**
     * Event data access object for database operations.
     */
    private final EventDao eventDao = new EventDao();
    /**
     * Attendance data access object for database operations.
     */
    private final AttendanceDao attendanceDao = new AttendanceDao();
    /**
     * User data access object for database operations.
     */
    private final UserDao userDao = new UserDao();

    /**
     * Constructs a new EventController and initializes.
     */
    public EventController() {
        try {
            eventList = eventDao
                    .getAllEvents(LocaleManager.getInstance()
                            .getLanguageCode());
        } catch (Exception e) {
            e.printStackTrace();
            eventList = FXCollections.observableArrayList();
        }
        categoryMenuItems = FXCollections.observableArrayList();
        locationMenuItems = FXCollections.observableArrayList();
    }

    /**
     * Initializes the controller, binds UI properties,
     * and sets up menu items and listeners.
     */
    @FXML
    public void initialize() {
        setLanguage();
        // Bind the FlowPane wrap length to the ScrollPane width
        scrollPane.viewportBoundsProperty()
                .addListener((observable, oldValue, newValue) ->
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
                ((CheckMenuItem) item).selectedProperty().addListener(
                        (obs, oldVal, newVal) -> searchEvents());
            }
        });
        locationMenu.getItems().forEach(item -> {
            if (item instanceof CheckMenuItem) {
                ((CheckMenuItem) item).selectedProperty().addListener(
                        (obs, oldVal, newVal) -> searchEvents());
            }
        });

        // Add event cards
        eventList.forEach(e -> eventFlowPane.getChildren().add(
                createEventCard(e)));

        // Listeners for search and date filter
        searchField.textProperty().addListener(
                (obs, oldVal, newVal) -> searchEvents());
        datePicker.valueProperty().addListener(
                (obs, oldVal, newVal) -> searchEvents());
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
     * Handles the action of attending an event.
     *
     * @param event the mouse event triggering the attendance.
     */
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
            UserDTO currentUserDTO = userDao.getUserById(currentUserId);
            User currentUser = UserMapper.toEntity(currentUserDTO);
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

    /**
     * Populates the provided menu items list with
     * distinct values from the event list.
     *
     * @param menuItems the observable list of menu items to populate.
     * @param column the column name based on which values are extracted.
     */
    public void populateMenuItems(
            ObservableList<MenuItem> menuItems, String column) {
        // Compute distinct values from eventList based
        // on the requested column.
        // If column is "location", use event location;
        // if "category", use a placeholder since Event has no category field.
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

    /**
     * Creates a card UI element for the given event.
     *
     * @param event the event for which the card is created.
     * @return a StackPane representing the event card.
     */
    private StackPane createEventCard(Event event) {
        final int vBoxv = 5;
        final int timeBoxv = 5;
        final int cardWidth = 150;
        final int cardHeight = 100;
        final int wrapWidth = 130;
        StackPane cardPane = new StackPane();
        cardPane.getStyleClass().add("card-pane");
        cardPane.setPrefSize(cardWidth, cardHeight);

        VBox vBox = new VBox(vBoxv);
        vBox.getStyleClass().add("card-vbox");

        // Use correct getters from Event entity.
        Text nameText = new Text(event.getName());
        Text locationText = new Text(
                rb.getString(
                        "locationLabelBrowse")
                        + ": " + event.getLocation());
        Text dateText = new Text(
                rb.getString(
                        "dateLabelBrowse")
                        + ": " + event.getEventDate(
                                ).toString());
        Text descriptionText = new Text(
                rb.getString(
                        "descriptionLabelBrowse")
                        + ": " + event.getDescription());
        descriptionText.setWrappingWidth(wrapWidth);
        TextFlow descriptionFlow = new TextFlow(descriptionText);

        // Display event time using the startTime field.
        HBox timeBox = new HBox(timeBoxv);
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

        vBox.getChildren().addAll(
                nameText, locationText, dateText, descriptionFlow, timeBox, attendButton);
        cardPane.getChildren().add(vBox);
        return cardPane;
    }

    /**
     * Filters events based on search text,
     * selected date, and selected menu items.
     *
     * @param searchText the text to search for.
     * @param selectedDate the selected date filter.
     * @return a list of filtered events.
     */
    public List<Event> filterEvents(String searchText, LocalDate selectedDate) {
        List<String> selectedCategories = categoryMenu.getItems().stream()
                .filter(item -> item instanceof CheckMenuItem
                        && ((CheckMenuItem) item).isSelected())
                .map(MenuItem::getText)
                .collect(Collectors.toList());

        List<String> selectedLocations = locationMenu.getItems().stream()
                .filter(item -> item instanceof CheckMenuItem
                        && ((CheckMenuItem) item).isSelected())
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

    /**
     * Searches for events based on current filter criteria and updates the UI.
     */
    @FXML
    public void searchEvents() {
        String searchText = searchField.getText().toLowerCase();
        LocalDate selectedDate = datePicker.getValue();
        List<Event> filteredEvents = filterEvents(searchText, selectedDate);
        eventFlowPane.getChildren().clear();
        filteredEvents.forEach(e -> eventFlowPane.getChildren().add(createEventCard(e)));
    }

    /**
     * Handles the logout action, navigates to the login screen, and clears the current user.
     */
    @FXML
    public void handleLogout() {
        try {
            mainApp.showLoginScreen();
            UserDao.clearCurrentUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the home page.
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
     * Displays an alert dialog with the provided type, title, and message.
     *
     * @param type the type of alert.
     * @param title the title of the alert.
     * @param message the content message of the alert.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Retrieves an unmodifiable list of events.
     *
     * @return the list of events.
     */
    public List<Event> getEventList() {
        return Collections.unmodifiableList(eventList);
    }

    /**
     * Retrieves the observable list of category menu items.
     *
     * @return the observable list of category menu items.
     */
    public ObservableList<MenuItem> getCategoryMenuItems() {
        return categoryMenuItems;
    }

    /**
     * Retrieves the observable list of location menu items.
     *
     * @return the observable list of location menu items.
     */
    public ObservableList<MenuItem> getLocationMenuItems() {
        return locationMenuItems;
    }
}
