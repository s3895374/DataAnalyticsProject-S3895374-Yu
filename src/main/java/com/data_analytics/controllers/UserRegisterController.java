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
 * Controller class for the user registration view.
 */
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

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
    }

    /**
     * Validates the input fields.
     *
     * @return true if all required fields are filled, false otherwise.
     */
    @FXML
    private boolean validateInput() {
        // Validate required fields 
        // Check if username field is empty
        if (usernameField.getText().isEmpty()) {
            // Return false if username field is empty
            return false;
        }

        // Check if password field is empty
        if (passwordField.getText().isEmpty()) {
            // Return false if password field is empty
            return false;
        }

        // Check if password and confirm password fields match
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            // Return false if password and confirm password fields do not match
            return false;
        }

        // Check if first name field is empty
        if (firstNameField.getText().isEmpty()) {
            // Return false if first name field is empty
            return false;
        }
        // Check if last name field is empty
        if (lastNameField.getText().isEmpty()) {
            // Return false if last name field is empty
            return false;
        }

        // Return true if all required fields are filled
        return true;
    }

    /**
     * Handles the sign up action.
     */
    @FXML
    private void handleSignupAction() {

        // Check if input is valid
        if (!validateInput()) {
            // Show error alert
            FxUtils.alert("Input validation error, please try again!");
            return;
        }

        // Get values from input fields
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        // Create a new User object with the input values
        User user = new User(username, password, firstName, lastName);

        // Create a new UserDAO object
        UserDAO userDAO = new UserDAO();

        try {
            // Save the user data to the database
            userDAO.save(user);
            // Close the dialog and return to the main view
            handleGoBackAction();
        } catch (SQLException e) {
            // Show error alert if an exception occurs
            FxUtils.alert("An error occurred! Please try again");
        }

    }

    /**
     * Handles the go back action.
     */
    @FXML
    private void handleGoBackAction() {
        Stage stage = (Stage) signupButton.getScene().getWindow();
        stage.close();
    }

}