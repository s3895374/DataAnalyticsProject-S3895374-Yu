package com.data_analytics.controllers;

import com.data_analytics.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserDashboardController {

    @FXML
    private Label welcomeMessageLabel;

    // Initialize with the User object after login
    private User loggedInUser;

    public void initialize() {
        welcomeMessageLabel.setText("Welcome, " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName() + "!");
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

}