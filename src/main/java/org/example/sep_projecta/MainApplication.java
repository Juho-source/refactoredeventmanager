package org.example.sep_projecta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main entry point for the Event Manager application.
 * <p>
 * This class extends {@link Application} and serves as the primary controller for navigating between
 * various scenes within the application, such as login, registration, and event management pages.
 * It uses JavaFX for creating and managing the GUI.
 * </p>
 */
public class MainApplication extends Application {
    
    /**
     * The primary stage for the JavaFX application.
     * <p>
     * This stage serves as the main window for displaying different scenes,
     * such as the login screen, registration screen, and other pages
     * within the Event Manager application.
     * </p>
     */
    private Stage primaryStage;

    /**
     * JavaFX application start method.
     * <p>
     * Initializes the primary stage and displays the login screen.
     * </p>
     *
     * @param primaryStage the primary stage for the application
     * @throws IOException if the FXML file for the login screen cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        showLoginScreen();
    }

    /**
     * Displays the login screen.
     *
     * @throws IOException if the FXML file for the login screen cannot be loaded
     */
    public void showLoginScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/eventmanagementlogin.fxml"));
        Parent root = fxmlLoader.load();
        LoginController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        Scene scene = new Scene(root, 600, 450);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Displays the browse page.
     *
     * @throws IOException if the FXML file for the browse page cannot be loaded
     */
    public void showBrowsePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/eventmanagementbrowsetest.fxml"));
        Parent root = fxmlLoader.load();
        EventController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        Scene scene = new Scene(root, 787, 527);
        primaryStage.setTitle("Browse Events");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Displays the registration screen.
     *
     * @throws IOException if the FXML file for the registration screen cannot be loaded
     */
    public void showRegistrationScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/eventmanagementregistration.fxml"));
        Parent root = fxmlLoader.load();
        RegistrationController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Register");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Displays the settings page.
     *
     * @throws IOException if the FXML file for the settings page cannot be loaded
     */
    public void showSettings() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/settings.fxml"));
        Parent root = fxmlLoader.load();
        SettingsController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        Scene scene = new Scene(root, 787, 527);
        primaryStage.setTitle("Settings");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Displays the change information page.
     *
     * @throws IOException if the FXML file for the change information page cannot be loaded
     */
    public void showChangeInfoPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/changeinfo.fxml"));
        Parent root = fxmlLoader.load();
        ChangeInfoController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        Scene scene = new Scene(root, 787, 527);
        primaryStage.setTitle("Change Information");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Displays the home page.
     *
     * @throws IOException if the FXML file for the home page cannot be loaded
     */
    public void showHomePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/eventmanagementhome.fxml"));
        Parent root = fxmlLoader.load();
        EventHomeController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        Scene scene = new Scene(root, 787, 527);
        primaryStage.setTitle("Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Displays the create event page.
     *
     * @throws IOException if the FXML file for the create event page cannot be loaded
     */
    public void showCreateEventPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/CreateEvent.fxml"));
        Parent root = fxmlLoader.load();
        CreateEventController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        Scene scene = new Scene(root, 787, 527);
        primaryStage.setTitle("Create Event");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Main method for launching the application.
     * <p>
     * Initializes the Hibernate session factory and then launches the JavaFX application.
     * </p>
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Initialize Hibernate instead of the old DatabaseConnector.
        HibernateUtil.getSessionFactory();
        launch();
    }
}
