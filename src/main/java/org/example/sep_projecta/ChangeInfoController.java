package org.example.sep_projecta;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Locale;

public class ChangeInfoController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Button saveChangesButton;
    @FXML
    private Button backToSettingsButton;

    private UserDao userDao = new UserDao();
    private ResourceBundle rb;

    @FXML
    private void initialize() {
        setLanguage();
        int currentUserId = UserDao.getCurrentUserId();
        if (currentUserId != 0) {
            User currentUser = userDao.getUserById(currentUserId);
            if (currentUser != null) {
                firstNameField.setText(currentUser.getFirstName());
                lastNameField.setText(currentUser.getLastName());
                emailField.setText(currentUser.getEmail());
                phoneNumberField.setText(currentUser.getPhoneNumber());
            }
        }
    }
    private void setLanguage() {
        Locale locale = LocaleManager.getInstance().getCurrentLocale();
        rb = ResourceBundle.getBundle("messages", locale);
        firstNameLabel.setText(rb.getString("firstNameLabel"));
        lastNameLabel.setText(rb.getString("lastNameLabel"));
        emailLabel.setText(rb.getString("emailLabel"));
        phoneNumberLabel.setText(rb.getString("phoneNumberLabel"));
        saveChangesButton.setText(rb.getString("saveChangesButton"));
        backToSettingsButton.setText(rb.getString("backToSettingsButton"));
    }

    @FXML
    private void handleSaveChanges() {
        int currentUserId = UserDao.getCurrentUserId();
        if (currentUserId == 0) {
            showAlert(Alert.AlertType.ERROR, "Error", "No logged in user");
            return;
        }

        User currentUser = userDao.getUserById(currentUserId);
        if (currentUser != null) {
            currentUser.setFirstName(firstNameField.getText());
            currentUser.setLastName(lastNameField.getText());
            currentUser.setEmail(emailField.getText());
            currentUser.setPhoneNumber(phoneNumberField.getText());
            userDao.updateUser(currentUser);
            showAlert(Alert.AlertType.INFORMATION, "Success", "User info updated successfully");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "User not found");
        }
    }

    @FXML
    private void handleBackToSettings() {
        try {
            MainApplication.showSettings();
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