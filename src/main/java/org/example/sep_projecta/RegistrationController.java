package org.example.sep_projecta;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import java.io.IOException;

public class RegistrationController {
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneNumberField;
    @FXML private CheckBox isTeacherCheckBox;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Button registerButton;
    @FXML private Button backToLoginButton;

    private UserDao userDao = new UserDao();

    @FXML
    private void handleRegister() throws IOException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        boolean isTeacher = isTeacherCheckBox.isSelected();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (userDao.usernameExists(username)) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Username already exists.");
            return;
        }

        boolean registered = userDao.register(firstName, lastName, email, phoneNumber, isTeacher, username, password);
        if (registered) {
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User registered successfully!");
            MainApplication.showLoginScreen();
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Could not register user.");
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            MainApplication.showLoginScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}