package com.data_analytics.controllers;

import com.data_analytics.MyApplication;
import com.data_analytics.dao.PostDAO;
import com.data_analytics.dao.UserDAO;
import com.data_analytics.models.Post;
import com.data_analytics.models.User;
import com.data_analytics.utils.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * The DashboardController class is responsible for controlling the dashboard view of the application.
 * It handles user interactions and performs necessary actions based on those interactions.
 */
public class DashboardController {

    @FXML
    private Button vipButton;
    @FXML
    private Separator vipSep;
    @FXML
    private Button vipShare;
    @FXML
    private Button vipStat;
    @FXML
    private BorderPane contentPane;
    @FXML
    private Label welcomeMessageLabel;

    // Initialize with the User object after login
    private User loggedInUser;
    private UserDAO userDAO = new UserDAO();
    private PostDAO postDAO = new PostDAO();

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
    }

    /**
     * Handles the click event when the user wants to become a VIP.
     * @param actionEvent The action event triggered by the user.
     */
    public void onBecameVIPClick(ActionEvent actionEvent) {
        if (!loggedInUser.isVIP()) {
            boolean confirm = FxUtils.confirm("Become a VIP user?");
            if (confirm) {
                // Upgrade user to VIP
                loggedInUser.setVIP(true);
                try {
                    userDAO.update(loggedInUser);
                    FxUtils.alert("Upgrade to vip successfully.\nPlease log out and log in again to access VIP functionalities.");
                } catch (SQLException e) {
                }
            }
        }
    }

    /**
     * Sets the logged in user.
     * @param user The logged in user.
     */
    public void setLogInUser(User user) {
        this.loggedInUser = user;
        welcomeMessageLabel.setText("Welcome, " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName() + "!");
        if (user.isVIP()) {
            vipSep.setVisible(true);
            vipShare.setVisible(true);
            vipStat.setVisible(true);
            vipButton.setVisible(false);
        } else {
            vipSep.setVisible(false);
            vipShare.setVisible(false);
            vipStat.setVisible(false);
            vipButton.setVisible(true);
        }
    }

    /**
     * Handles the click event when the user wants to view their profile.
     * @param actionEvent The action event triggered by the user.
     */
    public void onMyProfileClick(ActionEvent actionEvent) {
        String fxmlPath = "views/user_profile.fxml";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource(fxmlPath));
            Node root = fxmlLoader.load();
            ProfileController controller = fxmlLoader.getController();
            controller.loadUser(loggedInUser);
            contentPane.getChildren().clear();
            contentPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the click event when the user wants to add a new post.
     * @param actionEvent The action event triggered by the user.
     */
    public void onAddPostClick(ActionEvent actionEvent) {
        String fxmlPath = "views/add_post.fxml";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource(fxmlPath));
            Node root = fxmlLoader.load();
            AddPostController controller = fxmlLoader.getController();
            controller.setCurrentUser(loggedInUser);
            contentPane.getChildren().clear();
            contentPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the click event when the user wants to view a post.
     * @param actionEvent The action event triggered by the user.
     */
    public void onViewPostClick(ActionEvent actionEvent) {
        String fxmlPath = "views/view_post.fxml";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource(fxmlPath));
            Node root = fxmlLoader.load();
            contentPane.getChildren().clear();
            contentPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Handles the click event when the user wants to delete a post.
     * @param actionEvent The action event triggered by the user.
     */
    public void onDeletePostClick(ActionEvent actionEvent) {
        String fxmlPath = "views/remove_post.fxml";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource(fxmlPath));
            Node root = fxmlLoader.load();
            contentPane.getChildren().clear();
            contentPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the click event when the user wants to view the top N liked posts.
     * @param actionEvent The action event triggered by the user.
     */
    public void onTopNLikesPostClick(ActionEvent actionEvent) {
        String fxmlPath = "views/top_posts.fxml";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource(fxmlPath));
            Node root = fxmlLoader.load();
            contentPane.getChildren().clear();
            contentPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Handles the click event when the user wants to export posts.
     * @param actionEvent The action event triggered by the user.
     */
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
            List<Post> userPosts = new ArrayList<>();
            for (Post post: posts) {
                if(Objects.equals(post.getCreateBy(), loggedInUser.getUsername())){
                    userPosts.add(post);
                }
            }
            exportPosts(userPosts, new File(targetDir, "posts.csv"));
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

    /**
     * Exports the given list of posts to the specified file.
     * @param posts The list of posts to export.
     * @param file The file to export the posts to.
     * @throws IOException If an I/O error occurs while exporting the posts.
     */
    private static void exportPosts(List<Post> posts, File file) throws IOException {
        try (PrintWriter writer = new PrintWriter(file)) {

            // Write header
            writer.println("id,content,author,likes,shares,datetime");
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            // Write data
            for (Post post : posts) {
                writer.println(post.getId() + "," + post.getContent() + "," + post.getAuthor() + "," + post.getLikes() + "," + post.getShares() + "," + dateFormat.format(post.getDatetime()));
            }

        }
    }

    /**
     * Exports the given list of users to the specified file.
     * @param users The list of users to export.
     * @param file The file to export the users to.
     * @throws Exception If an error occurs while exporting the users.
     */
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

    /**
     * Handles the click event when the user wants to view statistics.
     * @param actionEvent The action event triggered by the user.
     */
    public void onStatsClick(ActionEvent actionEvent) {
        String fxmlPath = "views/share_stats.fxml";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource(fxmlPath));
            Node root = fxmlLoader.load();
            contentPane.getChildren().clear();
            contentPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the click event when the user wants to view their profile.
     * @param actionEvent The action event triggered by the user.
     */
    public void onLogoutClick(ActionEvent actionEvent) {
        Stage stage = (Stage) contentPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the click event when the user wants to import data.
     * @param actionEvent The action event triggered by the user.
     */
    public void onImportClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                importPosts(file);
                // Show success alert
                FxUtils.alert("Import successful!");
            } catch (IOException e) {
                // Show error alert
                FxUtils.alert("Error importing data!");
            }
        }

    }

    /**
     * Imports posts from the specified file.
     * @param file The file to import posts from.
     * @throws IOException If an I/O error occurs while importing the posts.
     */
    private void importPosts(File file) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(file));
        PostDAO postDao = new PostDAO();

        String header = reader.readLine(); // read header row
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");

            Post post = new Post();
            post.setId(Integer.parseInt(values[0]));
            post.setContent(values[1]);
            post.setAuthor(values[2]);
            post.setLikes(Integer.parseInt(values[3]));
            post.setShares(Integer.parseInt(values[4]));
            post.setCreateBy(loggedInUser.getUsername());
            // Parse date
            Date date = null;
            try {
                date = dateFormat.parse(values[5]);
            } catch (ParseException e) {
            }
            post.setDatetime(date);


            try {
                Post origin = postDao.findById(post.getId());
                if (origin != null) {
                    origin.setContent(post.getContent());
                    origin.setAuthor(post.getAuthor());
                    origin.setLikes(post.getLikes());
                    origin.setShares(post.getShares());
                    postDao.update(origin);
                    continue;
                }
            } catch (SQLException e) {
            }

            // Save post
            try {
                postDao.save(post);
            } catch (SQLException e) {
            }
        }

        reader.close();

    }
}