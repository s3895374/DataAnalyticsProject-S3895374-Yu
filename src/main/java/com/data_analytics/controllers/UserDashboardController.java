package com.data_analytics.controllers;

import com.data_analytics.HelloApplication;
import com.data_analytics.models.User;
import com.data_analytics.utils.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class UserDashboardController {

    public BorderPane contentPane;
    @FXML
    private Label welcomeMessageLabel;

    // Initialize with the User object after login
    private User loggedInUser;

    @FXML
    public void initialize() {
    }

    public void setLogInUser(User user) {
        this.loggedInUser = user;
        welcomeMessageLabel.setText("Welcome, " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName() + "!");
    }

    @FXML
    public void handleUserProfile() {
        FxUtils.showFxmlDialog("views/user_profile.fxml", "User Profile", 350, 450);
    }

    public void onMyProfileClick(ActionEvent actionEvent) {
        String fxmlPath = "views/user_profile.fxml";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
            Node root = fxmlLoader.load();
            ProfileController controller = fxmlLoader.getController();
            controller.loadUser(loggedInUser);
            contentPane.getChildren().clear();
            contentPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}