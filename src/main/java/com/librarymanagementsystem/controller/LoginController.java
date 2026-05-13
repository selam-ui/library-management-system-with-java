package com.librarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import com.librarymanagementsystem.service.AuthService;
import com.librarymanagementsystem.exception.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Label errorLabel;

    private AuthService authService = new AuthService();

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username and password are required");
            return;
        }

        try {
            authService.login(username, password);
            errorLabel.setText("");
            logger.info("User logged in successfully: {}", username);
            // TODO: Navigate to Dashboard
        } catch (AuthenticationException e) {
            errorLabel.setText(e.getMessage());
            logger.error("Login failed for user: {}", username);
        }
    }

    @FXML
    public void handleRegister(ActionEvent event) {
        logger.info("Register button clicked");
        // TODO: Navigate to Registration screen
    }

    @FXML
    public void initialize() {
        logger.info("Login screen initialized");
    }
}
