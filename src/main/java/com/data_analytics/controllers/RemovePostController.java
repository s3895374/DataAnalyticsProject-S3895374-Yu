package com.data_analytics.controllers;

import com.data_analytics.dao.PostDAO;
import com.data_analytics.models.Post;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

/**
 * The RemovePostController class is responsible for handling the removal of a post.
 */
public class RemovePostController {

    @FXML
    private TextField postIdField;

    @FXML
    private Label resultLabel;

    private PostDAO postDAO = new PostDAO();

    /**
     * Handles the removal of a post.
     */
    @FXML
    public void handleRemove() {
        int postId = Integer.parseInt(postIdField.getText());

        try {
            Post post = postDAO.findById(postId);
            if (post != null) {
                postDAO.deleteById(postId);
                resultLabel.setText("Removed post with id: " + postId);
            } else {
                resultLabel.setText("Removed post failed");
            }
        } catch (SQLException e) {
            resultLabel.setText("Removed post failed");
        }

    }

}