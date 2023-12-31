package com.data_analytics.controllers;

import com.data_analytics.dao.UserDAO;
import com.data_analytics.models.User;
import com.data_analytics.utils.FxUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * The UserLoginController class is responsible for controlling the user login functionality.
 */
public class UserLoginController {
    private UserDAO userDAO = new UserDAO();
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;
    @FXML
    private Button goBackButton;
    @FXML
    private Button registerButton;

    /**
     * Initializes the UserLoginController.
     */
    @FXML
    public void initialize() {
        usernameField.setText("abc");
        passwordField.setText("123");
    }

    /**
     * Handles the login action when the login button is clicked.
     */
    @FXML
    private void handleLoginAction() {
        // Get values from fields
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Authenticate against database
        User user = null;
        try {
            // Find user by username
            user = userDAO.findByUsername(username);
            // Check if user exists and password is correct
            if (user != null && user.getPassword().equals(password)) {
                // Show dashboard view
                DashboardController controller = FxUtils.showFxmlDialog("views/dashboard.fxml", "Dashboard", 900, 600);
                // Pass logged in user to dashboard controller
                controller.setLogInUser(user);
                handleGoBackAction();
            } else {
                // Show alert for invalid username or password
                FxUtils.alert("Invalid username or password");
            }
        } catch (SQLException e) {
            // Show alert for invalid username or password
            FxUtils.alert("Invalid username or password");
        }

    }

    /**
     * Handles the register action when the register button is clicked.
     */
    @FXML
    private void handleRegisterAction() {
        FxUtils.showFxmlDialog("views/user_register.fxml", "Register", 350, 450);
    }

    /**
     * Handles the go back action when the go back button is clicked.
     */
    @FXML
    private void handleGoBackAction() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }

}