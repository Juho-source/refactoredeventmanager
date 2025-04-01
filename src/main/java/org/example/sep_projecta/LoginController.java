package org.example.sep_projecta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.util.Locale;
import java.io.IOException;
import java.util.ResourceBundle;
import java.text.MessageFormat;

public class LoginController {
    @FXML
    public TextField usernameField;
    @FXML
    public TextField passwordField;
    @FXML
    public Button loginButton;
    @FXML
    public Button registerButton;
    @FXML
    public Text loginUsernameText;
    @FXML
    public Text loginPasswordText;
    @FXML
    public Text loginHeaderText;

    private UserDao userDao = new UserDao();

    ResourceBundle rb;

    public void initialize() {
        setLanguage(LocaleManager.getInstance().getCurrentLocale());
    }

    public void setLanguage(Locale locale) {
        LocaleManager.getInstance().setCurrentLocale(locale);
        rb = ResourceBundle.getBundle("messages", locale);
        loginUsernameText.setText(rb.getString("loginUsernameText"));
        loginPasswordText.setText(rb.getString("loginPasswordText"));
        loginButton.setText(rb.getString("loginButton"));
        registerButton.setText(rb.getString("registerButton"));
        loginHeaderText.setText(rb.getString("loginHeaderText"));
    }

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (userDao.auth(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "loginSuccessTitle", "loginSuccessMessage", username);
            try {
                MainApplication.showHomePage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "loginFailedTitle", "loginFailedMessage");
        }
    }

    @FXML
    public void handleRegister() {
        try {
            MainApplication.showRegistrationScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String titleKey, String messageKey, Object... args) {
        String title = rb.getString(titleKey);
        String message = MessageFormat.format(rb.getString(messageKey), args);
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void onENClick(ActionEvent actionevent) {
        setLanguage(new Locale("en", "US"));
    }

    public void onUAClick(ActionEvent actionevent) {
        setLanguage(new Locale("uk", "UA"));
    }

    public void onCNClick(ActionEvent actionevent) {
        setLanguage(new Locale("zh", "CN"));
    }
}