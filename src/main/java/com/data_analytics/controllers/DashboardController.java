package com.data_analytics.controllers;

import com.data_analytics.HelloApplication;
import com.data_analytics.models.User;
import com.data_analytics.utils.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class DashboardController {

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

    public void onAddPostClick(ActionEvent actionEvent) {
        String fxmlPath = "views/add_post.fxml";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
            Node root = fxmlLoader.load();
            contentPane.getChildren().clear();
            contentPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onViewPostClick(ActionEvent actionEvent) {
    }

    public void onDeletePostClick(ActionEvent actionEvent) {
    }

    public void onTopNLikesPostClick(ActionEvent actionEvent) {

    }
}