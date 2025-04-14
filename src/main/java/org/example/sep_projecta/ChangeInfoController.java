package org.example.sep_projecta;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Locale;

/**
 * Controller class for handling the change user information screen.
 */
public class ChangeInfoController {

    /**
     * Text field for the first name.
     */
    @FXML
    private TextField firstNameField;

    /**
     * Text field for the last name.
     */
    @FXML
    private TextField lastNameField;

    /**
     * Text field for the email.
     */
    @FXML
    private TextField emailField;

    /**
     * Text field for the phone number.
     */
    @FXML
    private TextField phoneNumberField;

    /**
     * Label for the first name.
     */
    @FXML
    private Label firstNameLabel;

    /**
     * Label for the last name.
     */
    @FXML
    private Label lastNameLabel;

    /**
     * Label for the email.
     */
    @FXML
    private Label emailLabel;

    /**
     * Label for the phone number.
     */
    @FXML
    private Label phoneNumberLabel;

    /**
     * Button to save the changes.
     */
    @FXML
    private Button saveChangesButton;

    /**
     * Button to navigate back to settings.
     */
    @FXML
    private Button backToSettingsButton;

    /**
     * Data access object for user operations.
     */
    private final UserDao userDao = new UserDao();

    /**
     * Resource bundle for locale-specific texts.
     */
    private ResourceBundle rb;

    /**
     * Reference to the main application.
     */
    private MainApplication mainApp;

    /**
     * Sets the main application instance.
     *
     * @param mainApp the main application instance
     */
    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Initializes the controller.
     * Sets the language and loads the current user data if available.
     */
    @FXML
    private void initialize() {
        setLanguage();
        int currentUserId = UserDao.getCurrentUserId();
        if (currentUserId != 0) {
            UserDTO currentUserDTO = userDao.getUserById(currentUserId);
            if (currentUserDTO != null) {
                firstNameField.setText(currentUserDTO.getFirstName());
                lastNameField.setText(currentUserDTO.getLastName());
                emailField.setText(currentUserDTO.getEmail());
                phoneNumberField.setText(currentUserDTO.getPhoneNumber());
            }
        }
    }

    /**
     * Configures the UI text elements using the current locale.
     */
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

    /**
     * Handles the action of saving changes to the user information.
     */
    @FXML
    private void handleSaveChanges() {
        int currentUserId = UserDao.getCurrentUserId();
        if (currentUserId == 0) {
            showAlert(Alert.AlertType.ERROR, "Error", "No logged in user");
            return;
        }

        // Retrieve the current user details including the password.
        UserDTO currentUserDTO = userDao.getUserById(currentUserId);
        if (currentUserDTO != null) {
            // Preserve the original password
            // if the change info form does not provide one.
            String existingPassword = currentUserDTO.getPassword();

            // Update only the editable fields.
            currentUserDTO.setFirstName(firstNameField.getText());
            currentUserDTO.setLastName(lastNameField.getText());
            currentUserDTO.setEmail(emailField.getText());
            currentUserDTO.setPhoneNumber(phoneNumberField.getText());
            currentUserDTO.setPassword(existingPassword);

            userDao.updateUser(currentUserDTO);
            showAlert(Alert.AlertType
                    .INFORMATION, "Success", "User info updated successfully");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "User not found");
        }
    }

    /**
     * Handles the action of navigating back to the settings screen.
     */
    @FXML
    private void handleBackToSettings() {
        try {
            mainApp.showSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays an alert dialog with the specified type, title, and message.
     *
     * @param type the type of alert
     * @param title the title of the alert dialog
     * @param message the message to be displayed in the alert dialog
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
