// File: src/main/java/org/example/sep_projecta/MainApplication.java
package org.example.sep_projecta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class MainApplication extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        MainApplication.primaryStage = primaryStage;
        try {
            URL loginFxml = MainApplication.class.getResource("eventmanagementlogin.fxml");
            if (loginFxml == null) {
                throw new RuntimeException("Cannot find resource eventmanagementlogin.fxml");
            }
            FXMLLoader fxmlLoader = new FXMLLoader(loginFxml);
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            primaryStage.setTitle("Login");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading Login screen", e);
        }
    }

    public static void showLoginScreen() throws IOException {
        URL loginFxml = MainApplication.class.getResource("eventmanagementlogin.fxml");
        if (loginFxml == null) {
            throw new RuntimeException("Cannot find resource eventmanagementlogin.fxml");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(loginFxml);
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showRegistrationScreen() throws IOException {
        URL regFxml = MainApplication.class.getResource("eventmanagementregistration.fxml");
        if (regFxml == null) {
            throw new RuntimeException("Cannot find resource eventmanagementregistration.fxml");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(regFxml);
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setTitle("Register");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showHomePage() throws IOException {
        URL homeFxml = MainApplication.class.getResource("eventmanagementhome.fxml");
        if (homeFxml == null) {
            throw new RuntimeException("Cannot find resource eventmanagementhome.fxml");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(homeFxml);
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setTitle("Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showCreateEventPage() throws IOException {
        URL createEventFxml = MainApplication.class.getResource("CreateEvent.fxml");
        if (createEventFxml == null) {
            throw new RuntimeException("Cannot find resource CreateEvent.fxml");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(createEventFxml);
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setTitle("Create Event");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        // Shutdown Hibernate when the application stops
        HibernateUtil.shutdown();
    }

    public static void main(String[] args) {
        // Initialize Hibernate
        HibernateUtil.getSessionFactory();
        launch();
    }
}