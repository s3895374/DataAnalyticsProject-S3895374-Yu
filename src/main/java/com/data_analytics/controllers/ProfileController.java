package com.data_analytics.controllers;

import com.data_analytics.dao.UserDAO;
import com.data_analytics.models.User;
import com.data_analytics.utils.FxUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ProfileController {
    private UserDAO userDAO = new UserDAO();

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    public void initialize() {
    }

    // Load user data
    public void loadUser(User user) {
        try {
            user = userDAO.findByUsername(user.getUsername());
        } catch (SQLException e) {

        }

        // Set the text of the usernameTextField to the username of the user
        usernameTextField.setText(user.getUsername());

        // Set the text of the passwordTextField to the password of the user
        passwordTextField.setText(user.getPassword());

        // Set the text of the firstNameTextField to the first name of the user
        firstNameTextField.setText(user.getFirstName());

        // Set the text of the lastNameTextField to the last name of the user
        lastNameTextField.setText(user.getLastName());
    }

    // Save user data
    public void handleSaveUserAction() {
        // Create a new User object
        User user = new User();

        // Set the username of the User object to the value entered in the usernameTextField
        user.setUsername(usernameTextField.getText());

        // Set the password of the User object to the value entered in the passwordTextField
        user.setPassword(passwordTextField.getText());

        // Set the first name of the User object to the value entered in the firstNameTextField
        user.setFirstName(firstNameTextField.getText());

        // Set the last name of the User object to the value entered in the lastNameTextField
        user.setLastName(lastNameTextField.getText());

        // Try to update the user in the database using the userDAO
        try {
            userDAO.update(user);
            // Show a success alert message
            FxUtils.alert("User updated successfully!");
        } catch (SQLException e) {
            // Show an error alert message
            FxUtils.alert("User updated failed!");
        }
    }



}