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

public class UserRegisterController {
    private UserDAO userDAO = new UserDAO();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private Button signupButton;

    @FXML
    private Button goBackButton;

    @FXML
    public void initialize() {
    }

    @FXML
    private boolean validateInput() {
        // Validate required fields 
        if (usernameField.getText().isEmpty()) {
            return false;
        }

        if (passwordField.getText().isEmpty()) {
            return false;
        }

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            return false;
        }

        if (firstNameField.getText().isEmpty()) {
            return false;
        }
        if (lastNameField.getText().isEmpty()) {
            return false;
        }

        return true;
    }

    @FXML
    private void handleSignupAction() {

        if (!validateInput()) {
            // Show error alert
            FxUtils.alert("Input validation error, please try again!");
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        // Save user data to database
        User user = new User(username, password, firstName, lastName);

        UserDAO userDAO = new UserDAO();

        try {
            userDAO.save(user);
            // Close dialog and return to main view
            handleGoBackAction();
        } catch (SQLException e) {
            FxUtils.alert("An error occurred! Please try again");
        }

    }

    @FXML
    private void handleGoBackAction() {
        Stage stage = (Stage) signupButton.getScene().getWindow();
        stage.close();
    }

}