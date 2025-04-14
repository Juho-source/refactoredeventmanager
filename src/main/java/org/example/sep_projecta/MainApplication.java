package org.example.sep_projecta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private Stage primaryStage;



    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        showLoginScreen();

    }

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

    public void showBrowsePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/eventmanagementbrowsetest.fxml"));
        Parent root = fxmlLoader.load();
        EventController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        Scene scene = new Scene(root, 787, 527);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void showRegistrationScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/eventmanagementregistration.fxml"));
        Parent root = fxmlLoader.load();
        RegistrationController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showSettings() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/settings.fxml"));
        Parent root = fxmlLoader.load();
        SettingsController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        Scene scene = new Scene(root, 787, 527);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showChangeInfoPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/changeinfo.fxml"));
        Parent root = fxmlLoader.load();
        ChangeInfoController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        Scene scene = new Scene(root, 787, 527);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showHomePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/eventmanagementhome.fxml"));
        Parent root = fxmlLoader.load();
        EventHomeController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        Scene scene = new Scene(root, 787, 527);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showCreateEventPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/CreateEvent.fxml"));
        Parent root = fxmlLoader.load();
        CreateEventController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        Scene scene = new Scene(root, 787, 527);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Initialize Hibernate instead of the old DatabaseConnector.
        HibernateUtil.getSessionFactory();
        launch();

    }
}