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

    @FXML private Button homeButton;
    @FXML private Button browseButton;
    @FXML private Button settingsButton;
    @FXML private Button logoutButton;
    @FXML private Button changeInfo;
    @FXML private Button deleteAccount;

    private final UserDao userDao = new UserDao();
    private MainApplication mainApp;
    ResourceBundle rb;

    // Extracted constant for the repeated "Delete Account" text.
    private static final String DELETE_ACCOUNT_TITLE = "Delete Account";

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
        changeInfo.setText(rb.getString("changeInfo"));
        deleteAccount.setText(rb.getString("deleteAccount"));
    }

    @FXML
    private void handleDeleteAccount() {
        int currentUserId = UserDao.getCurrentUserId();
        if (currentUserId == 0) {
            showAlert(Alert.AlertType.ERROR, DELETE_ACCOUNT_TITLE, "No logged in user");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(DELETE_ACCOUNT_TITLE);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete your account?");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                try {
                    UserDTO userDTO = userDao.getUserById(currentUserId);
                    User user = UserMapper.toEntity(userDTO);
                    if (user != null) {
                        userDao.deleteUser(user);
                        UserDao.clearCurrentUser();
                        showAlert(Alert.AlertType.INFORMATION, DELETE_ACCOUNT_TITLE, "Account deleted");
                        mainApp.showLoginScreen();
                    } else {
                        showAlert(Alert.AlertType.ERROR, DELETE_ACCOUNT_TITLE, "User not found");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, DELETE_ACCOUNT_TITLE, "An error occurred");
                }
            }
        });
    }

    public void initialize() {
        setLanguage();
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
    private void handleBackHome() {
        try {
            mainApp.showHomePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void handleChangeUserInfo() {
        try {
            mainApp.showChangeInfoPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(final Alert.AlertType type, final String title, final String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
