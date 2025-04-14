package org.example.sep_projecta;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.util.Locale;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Controller class for the registration page of the application.
 */
public class RegistrationController {
    /**
     * The input text field for the first name.
     */
    @FXML private TextField firstNameField;

    /**
     * The input text field for the last name.
     */
    @FXML private TextField lastNameField;

    /**
     * The input text field for the email address.
     */
    @FXML private TextField emailField;

    /**
     * The input text field for the phone number.
     */
    @FXML private TextField phoneNumberField;

    /**
     * The checkbox to indicate if the user is a teacher.
     */
    @FXML private CheckBox isTeacherCheckBox;

    /**
     * The input text field for the username.
     */
    @FXML private TextField usernameField;

    /**
     * The input text field for the password.
     */
    @FXML private TextField passwordField;

    /**
     * The button to trigger the registration process.
     */
    @FXML private Button registerButton;

    /**
     * The button to navigate back to the login screen.
     */
    @FXML private Button backToLoginButton;

    /**
     * The text label for the first name.
     */
    @FXML private Text firstNameText;

    /**
     * The text label for the last name.
     */
    @FXML private Text lastNameText;

    /**
     * The text label for the email address.
     */
    @FXML private Text emailText;

    /**
     * The text label for the phone number.
     */
    @FXML private Text phoneNumberText;

    /**
     * The text label for indicating teacher status.
     */
    @FXML private Text isTeacherText;

    /**
     * The text label for the username during registration.
     */
    @FXML private Text registrationUsernameText;

    /**
     * The text label for the password during registration.
     */
    @FXML private Text registrationPasswordText;

    /**
     * The header text displayed on the registration page.
     */
    @FXML private Text registerHeaderText;
    /**
     * User data access object for handling user-related operations.
     */
    private final UserDao userDao = new UserDao();
    /**
     * Main application instance for navigating between screens.
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
     * Initializes the controller and sets the language for the UI components.
     */
    public void initialize() {
        setLanguage(LocaleManager.getInstance().getCurrentLocale());
    }

    /**
     * Sets the language for the UI components based on the specified locale.
     *
     * @param locale the locale to set
     */
    public void setLanguage(Locale locale) {
        LocaleManager.getInstance().setCurrentLocale(locale);
        rb = ResourceBundle.getBundle("messages", locale);
        firstNameText.setText(
                rb.getString("firstNameText"));
        lastNameText.setText(
                rb.getString("lastNameText"));
        emailText.setText(
                rb.getString("emailText"));
        phoneNumberText.setText(
                rb.getString("phoneNumberText"));
        isTeacherText.setText(
                rb.getString("isTeacherText"));
        registrationUsernameText.setText(
                rb.getString("registrationUsernameText"));
        registrationPasswordText.setText(
                rb.getString("registrationPasswordText"));
        registerHeaderText.setText(
                rb.getString("registerHeaderText"));
        registerButton.setText(
                rb.getString("registerButton"));
        backToLoginButton.setText(
                rb.getString("backToLoginButton"));
    }

    /**
     * Handles the action of registering a new user.
     *
     * @throws IOException if an I/O error occurs
     */
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
            showAlert(Alert.AlertType.ERROR,
                    "Registration Failed", "Username already exists.");
            return;
        }
        UserDTO newUser = new UserDTO();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setTeacher(isTeacher);
        newUser.setUsername(username);
        newUser.setPassword(password);

        boolean registered = userDao.register(newUser);
        if (registered) {
            showAlert(Alert.AlertType.INFORMATION,
                    "Registration Successful", "User registered successfully!");
            mainApp.showLoginScreen();
        } else {
            showAlert(Alert.AlertType.ERROR,
                    "Registration Failed", "Could not register user.");
        }
    }

    /**
     * Handles the action of navigating
     * back to the login screen.
     */
    @FXML
    private void handleBackToLogin() {
        try {
            mainApp.showLoginScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows an alert dialog with the
     * specified type, title, and message.
     *
     * @param alertType the type of the alert
     * @param title the title of the alert
     * @param message the message of the alert
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
