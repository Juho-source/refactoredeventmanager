package org.example.sep_projecta;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;

public class SettingsController {

    private UserDao userDao = new UserDao();

    // Java
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