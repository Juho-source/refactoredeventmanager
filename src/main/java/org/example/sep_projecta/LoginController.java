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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Locale;
import java.io.IOException;
import java.util.ResourceBundle;
import java.text.MessageFormat;

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

    private UserDao userDao = new UserDao();
    ResourceBundle rb;

    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    public void initialize() {
        setLanguage(LocaleManager.getInstance().getCurrentLocale());

        languageImageView.setImage(new Image(getClass().getResourceAsStream("/images/globe.png")));
        languageImageView.setOnMouseClicked((MouseEvent event) -> {
            showLanguagePopup();
        });
    }

    public void setLanguage(Locale locale) {
        LocaleManager.getInstance().setCurrentLocale(locale);
        rb = ResourceBundle.getBundle("messages", locale);
        loginUsernameText.setText(rb.getString("loginUsernameText"));
        loginPasswordText.setText(rb.getString("loginPasswordText"));
        loginButton.setText(rb.getString("loginButton"));
        registerButton.setText(rb.getString("registerButton"));
        loginPageLabel.setText(rb.getString("loginHeaderText"));
    }

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

    @FXML
    public void handleRegister() {
        try {
            mainApp.showRegistrationScreen();
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