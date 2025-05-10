package org.example.sep_projecta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Locale;
import java.io.IOException;
import java.util.ResourceBundle;
import java.text.MessageFormat;

/**
 * Controller class for the login page of the application.
 */
public class LoginController {
    private MainApplication mainApp;

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
    public Label loginPageLabel;
    @FXML
    public Label companyNameLabel;
    @FXML
    private ImageView languageImageView;

    private final UserDao userDao = new UserDao();
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

        languageImageView.setImage(new Image(getClass().getResourceAsStream("/images/globe.png")));
        languageImageView.setOnMouseClicked((MouseEvent event) ->
            showLanguagePopup());
    }

    /**
     * Sets the language for the UI components based on the specified locale.
     *
     * @param locale the locale to set
     */
    public void setLanguage(Locale locale) {
        LocaleManager.getInstance().setCurrentLocale(locale);
        rb = ResourceBundle.getBundle("messages", locale);
        loginUsernameText.setText(rb.getString("loginUsernameText"));
        loginPasswordText.setText(rb.getString("loginPasswordText"));
        loginButton.setText(rb.getString("loginButton"));
        registerButton.setText(rb.getString("registerButton"));
        loginPageLabel.setText(rb.getString("loginHeaderText"));
    }

    /**
     * Handles the action of logging in the user.
     */
    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (userDao.auth(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "loginSuccessTitle", "loginSuccessMessage", username);
            try {
                mainApp.showHomePage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "loginFailedTitle", "loginFailedMessage");
        }
    }

    /**
     * Handles the action of navigating to the registration screen.
     */
    @FXML
    public void handleRegister() {
        try {
            mainApp.showRegistrationScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows an alert dialog with the specified type, title, and message.
     *
     * @param alertType the type of the alert
     * @param titleKey the key for the title in the resource bundle
     * @param messageKey the key for the message in the resource bundle
     * @param args optional arguments for the message
     */
    private void showAlert(Alert.AlertType alertType, String titleKey, String messageKey, Object... args) {
        String title = rb.getString(titleKey);
        String message = MessageFormat.format(rb.getString(messageKey), args);
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows a popup for selecting the language.
     */
    private void showLanguagePopup() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Select Language");

        // Create buttons for languages
        Button enButton = new Button("EN");
        enButton.setOnAction((ActionEvent e) -> {
            onENClick(e);
            popupStage.close();
        });

        Button uaButton = new Button("UA");
        uaButton.setOnAction((ActionEvent e) -> {
            onUAClick(e);
            popupStage.close();
        });

        Button cnButton = new Button("CN");
        cnButton.setOnAction((ActionEvent e) -> {
            onCNClick(e);
            popupStage.close();
        });

        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(20));
        hbox.getChildren().addAll(enButton, uaButton, cnButton);

        Scene scene = new Scene(hbox);
        scene.getStylesheets().add(getClass().getResource("/Styles.css").toExternalForm());
        popupStage.setScene(scene);
        popupStage.sizeToScene();
        popupStage.showAndWait();
    }

    /**
     * Handles the action of selecting English language.
     *
     * @param actionevent the action event
     */
    public void onENClick(ActionEvent actionevent) {
        setLanguage(new Locale("en", "US"));
    }

    /**
     * Handles the action of selecting Ukrainian language.
     *
     * @param actionevent the action event
     */
    public void onUAClick(ActionEvent actionevent) {
        setLanguage(new Locale("uk", "UA"));
    }

    /**
     * Handles the action of selecting Chinese language.
     *
     * @param actionevent the action event
     */
    public void onCNClick(ActionEvent actionevent) {
        setLanguage(new Locale("zh", "CN"));
    }
}
