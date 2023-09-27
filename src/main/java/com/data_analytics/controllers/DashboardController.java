package com.data_analytics.controllers;

import com.data_analytics.HelloApplication;
import com.data_analytics.dao.PostDAO;
import com.data_analytics.dao.UserDAO;
import com.data_analytics.models.Post;
import com.data_analytics.models.User;
import com.data_analytics.utils.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

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
            AddPostController controller = fxmlLoader.getController();
            controller.setCurrentUser(loggedInUser);
            contentPane.getChildren().clear();
            contentPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onViewPostClick(ActionEvent actionEvent) {
        String fxmlPath = "views/view_post.fxml";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
            Node root = fxmlLoader.load();
            contentPane.getChildren().clear();
            contentPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onDeletePostClick(ActionEvent actionEvent) {
        String fxmlPath = "views/remove_post.fxml";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
            Node root = fxmlLoader.load();
            contentPane.getChildren().clear();
            contentPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTopNLikesPostClick(ActionEvent actionEvent) {
        String fxmlPath = "views/top_posts.fxml";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
            Node root = fxmlLoader.load();
            contentPane.getChildren().clear();
            contentPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onExportClick(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = new Stage();

        // Allow user to select target directory
        File targetDir = directoryChooser.showDialog(stage);

        if (targetDir == null) {
            System.out.println("No target directory selected");
            return;
        }

        PostDAO postDAO = new PostDAO();
        UserDAO userDAO = new UserDAO();
        // Get all posts of current user
        try {
            List<Post> posts = postDAO.findAll();
            // posts = posts.stream().filter(x -> Objects.equals(x.getCreateBy(), this.loggedInUser.getUsername())).toList();
            // Export posts to CSV
            exportPosts(posts, new File(targetDir, "posts.csv"));
        } catch (Exception e) {
        }

        try {
            List<User> users = userDAO.findAll();
            // Export users to CSV
            exportUsers(users, new File(targetDir, "users.csv"));
        } catch (Exception e) {
        }

        FxUtils.alert("All data exported successfully");
    }

    private static void exportPosts(List<Post> posts, File file) throws IOException {
        try (PrintWriter writer = new PrintWriter(file)) {

            // Write header
            writer.println("id,content,author,likes,shares,datetime,createBy");

            // Write data
            for (Post post : posts) {
                writer.println(post.getId() + "," + post.getContent() + "," + post.getAuthor() + "," + post.getLikes() + "," + post.getShares() + "," + post.getDatetime() + "," + post.getCreateBy());
            }

        }
    }

    private static void exportUsers(List<User> users, File file) throws Exception {
        try (PrintWriter writer = new PrintWriter(file)) {

            // Write header
            writer.println("username,password,first_name,last_name,is_vip");

            // Write data
            for (User user : users) {
                writer.println(user.getUsername() + "," + user.getPassword() + "," + user.getFirstName() + "," + user.getLastName() + "," + user.isVIP());
            }

        }
    }


    public void onLogoutClick(ActionEvent actionEvent) {
        Stage stage = (Stage) contentPane.getScene().getWindow();
        stage.close();
    }
}