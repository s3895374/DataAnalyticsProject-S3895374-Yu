package com.data_analytics.controllers;

import com.data_analytics.dao.PostDAO;
import com.data_analytics.models.Post;
import com.data_analytics.models.User;
import com.data_analytics.utils.FxUtils;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

/**
 * Controller class for adding a new post.
 */
public class AddPostController {
    @FXML
    private TextField idField;
    @FXML
    private TextArea contentField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField likesField;

    @FXML
    private TextField sharesField;

    @FXML
    private DatePicker dateTimePicker;

    private User currentUser;

    /**
     * Handles the submit button action.
     */
    @FXML
    public void handleSubmit() {

        // Get values from UI
        String idStr = idField.getText();
        int id = 0;
        try{
            id = Integer.parseInt(idStr);
        }catch (Exception e) {
            FxUtils.alert("Please input an integer for post id");
            return;
        }
        String content = contentField.getText();
        String author = authorField.getText();
        int likes = Integer.parseInt(likesField.getText());
        int shares = Integer.parseInt(sharesField.getText());
        Instant instant = dateTimePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date datetime = Date.from(instant);

        // Create Post
        Post post = new Post();
        post.setId(id);
        post.setContent(content);
        post.setAuthor(author);
        post.setLikes(likes);
        post.setShares(shares);
        post.setDatetime(datetime);
        post.setCreateBy(this.currentUser.getUsername());

        // Save via service
        PostDAO postDAO = new PostDAO();
        try {
            postDAO.save(post);
            FxUtils.alert("Post added successfully! The post id is " + post.getId() + "!");
        } catch (SQLException e) {
            FxUtils.alert("Post added failed! The post id already exists!");
        }
    }

    /**
     * Handles the reset button action.
     */
    @FXML
    public void handleReset() {
        // Clear UI field values
        contentField.clear();
        authorField.clear();
        likesField.clear();
        sharesField.clear();
        dateTimePicker.setValue(null);
    }

    /**
     * Sets the current user.
     *
     * @param currentUser The current user.
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
