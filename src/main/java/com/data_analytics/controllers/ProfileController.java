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
        usernameTextField.setText(user.getUsername());
        passwordTextField.setText(user.getPassword());
        firstNameTextField.setText(user.getFirstName());
        lastNameTextField.setText(user.getLastName());
    }

    // Save user data
    public void handleSaveUserAction() {
        User user = new User();
        user.setUsername(usernameTextField.getText());
        user.setUsername(passwordTextField.getText());
        user.setFirstName(firstNameTextField.getText());
        user.setFirstName(lastNameTextField.getText());

        // use dao to save user to db and show alert message
        try {
            userDAO.update(user);
            FxUtils.alert("User updated successfully!");
        } catch (SQLException e) {
            // Show error alert
            FxUtils.alert("User updated failed!");
        }
    }

    private void handleGoBackAction() {
//        Stage stage = (Stage) loginButton.getScene().getWindow();
//        stage.close();
    }


}