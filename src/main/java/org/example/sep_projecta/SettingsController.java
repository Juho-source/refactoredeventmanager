package org.example.sep_projecta;


import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;

/**
 * Controller class for the settings page of the application.
 */
public class SettingsController {

    /**
     * Button to navigate to the home page.
     */
    @FXML private Button homeButton;

    /**
     * Button to navigate to the browse page.
     */
    @FXML private Button browseButton;

    /**
     * Button to navigate to the settings page.
     */
    @FXML private Button settingsButton;

    /**
     * Button to log out the user.
     */
    @FXML private Button logoutButton;

    /**
     * Button to change user information.
     */
    @FXML private Button changeInfo;

    /**
     * Button to delete the user account.
     */
    @FXML private Button deleteAccount;



    /**
     * Data access object for user operations.
     */

    private final UserDao userDao = new UserDao();


    /**
     * Main application instance.
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
     * Sets the language for the UI components based on the current locale.
     */
    public void setLanguage() {
        Locale locale = LocaleManager.getInstance().getCurrentLocale();
        rb = ResourceBundle.getBundle("messages", locale);
        homeButton.setText(rb.getString("homeButton"));
        browseButton.setText(rb.getString("browseButton"));
        settingsButton.setText(rb.getString("settingsButton"));
        logoutButton.setText(rb.getString("logoutButton"));
        changeInfo.setText(rb.getString("changeInfo"));
        deleteAccount.setText(rb.getString("deleteAccount"));
    }

    /**
     * Handles the action of deleting the user account.
     */
    @FXML
    private void handleDeleteAccount() {
        int currentUserId = UserDao.getCurrentUserId();
        if (currentUserId == 0) {
            showAlert(Alert.AlertType.ERROR,
                    "Delete Account",
                    "No logged in user");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(
                "Delete Account");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(
                "Are you sure you want to delete your account?");

        ButtonType yesButton = new ButtonType(
                "Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType(
                "No", ButtonBar.ButtonData.NO);
        confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                try {
                    UserDTO userDTO = userDao.getUserById(currentUserId);
                    User user = UserMapper.toEntity(userDTO);
                    if (user != null) {
                        userDao.deleteUser(user);
                        UserDao.clearCurrentUser();
                        showAlert(
                                Alert.AlertType.INFORMATION,
                                "Delete Account", "Account deleted");
                        mainApp.showLoginScreen();
                    } else {
                        showAlert(
                                Alert.AlertType.ERROR,
                                "Delete Account", "User not found");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert(
                            Alert.AlertType.ERROR,
                            "Delete Account", "An error occurred");
                }
            }
        });
    }

    /**
     * Initializes the controller and sets the language for the UI components.
     */
    public void initialize() {
        setLanguage();
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
     * Handles the action of navigating back to the home page.
     */
    @FXML
    private void handleBackHome() {
        try {
            mainApp.showHomePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of navigating to the browse page.
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
     * Handles the action of changing user information.
     */
    @FXML
    private void handleChangeUserInfo() {
        try {
            mainApp.showChangeInfoPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows an alert dialog with the specified type, title, and message.
     *
     * @param type the type of the alert
     * @param title the title of the alert
     * @param message the message of the alert
     */
    private void showAlert(final Alert.AlertType type, final String title, final String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
