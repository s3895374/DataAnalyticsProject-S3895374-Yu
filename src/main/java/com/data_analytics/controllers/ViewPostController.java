package com.data_analytics.controllers;

import com.data_analytics.dao.PostDAO;
import com.data_analytics.models.Post;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

/**
 * Controller class for the ViewPost.fxml file.
 */
public class ViewPostController {

    @FXML
    private TextField postIdField;

    @FXML
    private Label resultLabel;

    private PostDAO postDAO = new PostDAO();

    /**
     * Event handler for the lookup button.
     */
    @FXML
    public void handleLookup() {
        int postId = Integer.parseInt(postIdField.getText());

        Post post = null;
        try {
            post = postDAO.findById(postId);
        } catch (SQLException e) {
        }

        if (post != null) {
            String content = "id: " + post.getId() +
                    "\ncontent: " + post.getContent() +
                    "\nauthor: " + post.getAuthor() +
                    "\nlikes: " + post.getLikes() +
                    "\nshares: " + post.getShares() +
                    "\ndatetime: " + post.getDatetime() +
                    "\ncreateBy: " + post.getCreateBy();

            resultLabel.setText(content);
        } else {
            resultLabel.setText("No post found for id: " + postId);
        }
    }


}
