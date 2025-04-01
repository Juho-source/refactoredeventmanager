package org.example.sep_projecta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        MainApplication.primaryStage = primaryStage;
        showLoginScreen();
    }

    public static void showLoginScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/eventmanagementlogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showBrowsePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/eventmanagementbrowsetest.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 787, 527);
        primaryStage.setTitle("Event management home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void showRegistrationScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/eventmanagementregistration.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        primaryStage.setTitle("Register");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showSettings() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 787, 527);
        primaryStage.setTitle("Settings");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showChangeInfoPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/changeinfo.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 787, 527);
        primaryStage.setTitle("Change User Info");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showHomePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/eventmanagementhome.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 787, 527);
        primaryStage.setTitle("home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showCreateEventPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/CreateEvent.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 787, 527);
        primaryStage.setTitle("Create Event");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Initialize Hibernate instead of the old DatabaseConnector.
        HibernateUtil.getSessionFactory();
        launch();
    }
}