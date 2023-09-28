package com.data_analytics.controllers;

import com.data_analytics.dao.PostDAO;
import com.data_analytics.models.Post;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controller class for the TopPosts view.
 */
public class TopPostsController {

    @FXML
    private Spinner<Integer> numPostsSpinner;

    @FXML
    private ListView<String> postsListView;

    private PostDAO postDAO = new PostDAO();

    /**
     * Event handler for the search button.
     * Retrieves the top posts from the database and displays them in the ListView.
     */
    @FXML
    public void handleSearch() {
        int numPosts = numPostsSpinner.getValue();
        List<String> result = new ArrayList<>();
        try {
            List<Post> allPosts = postDAO.findAll();
            allPosts.sort(new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    return o2.getLikes() - o1.getLikes();
                }
            });
            for (int i = 0; i < allPosts.size() && i < numPosts; i++) {
                Post post = allPosts.get(i);
                String content = "id: " + post.getId() +
                        "\ncontent: " + post.getContent() +
                        "\nauthor: " + post.getAuthor() +
                        "\nlikes: " + post.getLikes() +
                        "\nshares: " + post.getShares() +
                        "\ndatetime: " + post.getDatetime() +
                        "\ncreateBy: " + post.getCreateBy();

                result.add(content);
            }
        } catch (SQLException e) {
        }
        postsListView.getItems().setAll(result);
    }

}