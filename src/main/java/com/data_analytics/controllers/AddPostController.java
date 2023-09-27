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

public class AddPostController {
    @FXML
    private TextArea contentField;

    @FXML
    private TextField authorField;

    @FXML
    private Spinner<Integer> likesField;

    @FXML
    private Spinner<Integer> sharesField;

    @FXML
    private DatePicker dateTimePicker;

    private User currentUser;

    @FXML
    public void handleSubmit() {

        // Get values from UI
        String content = contentField.getText();
        String author = authorField.getText();
        int likes = likesField.getValue();
        int shares = sharesField.getValue();
        Instant instant = dateTimePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date datetime = Date.from(instant);

        // Create Post
        Post post = new Post();
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
            FxUtils.alert("Post added failed!");
        }
    }

    @FXML
    public void handleReset() {
        // Clear UI field values
        contentField.clear();
        authorField.clear();
        likesField.getValueFactory().setValue(0);
        sharesField.getValueFactory().setValue(0);
        dateTimePicker.setValue(null);

    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
