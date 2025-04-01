package org.example.sep_projecta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


import javafx.scene.control.Alert;

public class SettingsController {

    @FXML private Button homeButton;
    @FXML private Button browseButton;
    @FXML private Button settingsButton;
    @FXML private Button logoutButton;
    @FXML private Button changeInfo;
    @FXML private Button deleteAccount;

    private UserDao userDao = new UserDao();

    ResourceBundle rb;

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
            showAlert(Alert.AlertType.ERROR, "Delete Account", "No logged in user");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Account");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete your account?");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                try {
                    User user = userDao.getUserById(currentUserId);
                    if (user != null) {
                        userDao.deleteUser(user);
                        UserDao.clearCurrentUser();
                        showAlert(Alert.AlertType.INFORMATION, "Delete Account", "Account deleted");
                        MainApplication.showLoginScreen();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Delete Account", "User not found");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Delete Account", "An error occurred");
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
            MainApplication.showLoginScreen();
            UserDao.clearCurrentUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackHome() {
        try {
            MainApplication.showHomePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void browsePage() {
        try {
            MainApplication.showBrowsePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleChangeUserInfo() {
        try {
            MainApplication.showChangeInfoPage();
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
}