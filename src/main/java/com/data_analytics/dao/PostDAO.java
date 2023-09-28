package com.data_analytics.dao;

import com.data_analytics.models.Post;
import com.data_analytics.utils.SQLUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The PostDAO class provides methods to interact with the database and perform CRUD operations on the Post entity.
 */
public class PostDAO {

    /**
     * Saves a Post object to the database.
     *
     * @param post The Post object to be saved.
     * @throws SQLException If an error occurs while saving the Post object.
     */
    public void save(Post post) throws SQLException {
        // Define the SQL query for inserting data into the "posts" table
        String sql = "INSERT INTO posts (id, content, author, likes, shares, datetime, createBy) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Execute the SQL query with the provided parameters
        long id = SQLUtils.executeInsert(sql, post.getId(), post.getContent(), post.getAuthor(), post.getLikes(), post.getShares(), post.getDatetime(), post.getCreateBy());
        post.setId((int) id);
    }

    /**
     * Retrieves a Post object from the database based on the specified id.
     *
     * @param id The id of the Post object to be retrieved.
     * @return The retrieved Post object.
     * @throws SQLException If an error occurs while retrieving the Post object.
     */
    public Post findById(int id) throws SQLException {
        // Define the SQL query to select all columns from the "posts" table where the id matches a parameter
        String sql = "SELECT * FROM posts WHERE id = ?";

        // Execute the SQL query and retrieve a single row as a map of column names to values
        Map<String, Object> result = SQLUtils.executeQuerySingle(sql, id);

        // Convert the result map into a Post object
        if (result.size() == 0) {
            return null;
        }
        return mapToPost(result);
    }

    /**
     * Retrieves all Post objects from the database.
     *
     * @return A list of all Post objects.
     * @throws SQLException If an error occurs while retrieving the Post objects.
     */
    public List<Post> findAll() throws SQLException {
        // Define the SQL query to select all rows from the "posts" table
        String sql = "SELECT * FROM posts";

        // Execute the SQL query and store the results in a list of maps
        List<Map<String, Object>> results = SQLUtils.executeQuery(sql);

        // Create a list to store the converted Post objects
        List<Post> posts = new ArrayList<>();

        // Iterate over each map in the results list and convert it to a Post object
        for (Map<String, Object> result : results) {
            posts.add(mapToPost(result));
        }

        // Return the list of Post objects
        return posts;
    }

    /**
     * Updates a Post object in the database.
     *
     * @param post The Post object to be updated.
     * @throws SQLException If an error occurs while updating the Post object.
     */
    public void update(Post post) throws SQLException {
        // SQL update statement to update the 'posts' table
        String sql = "UPDATE posts SET content = ?, author = ?, likes = ?, shares = ?, datetime = ?, createBy = ?" + "WHERE id = ?";

        // Execute the SQL update statement with the given parameters
        SQLUtils.executeUpdate(sql, post.getContent(), post.getAuthor(), post.getLikes(), post.getShares(), post.getDatetime(), post.getCreateBy(), post.getId());
    }

    /**
     * Deletes a Post object from the database based on the specified id.
     *
     * @param id The id of the Post object to be deleted.
     * @throws SQLException If an error occurs while deleting the Post object.
     */
    public void deleteById(int id) throws SQLException {
        // SQL statement to delete a row from the "posts" table based on the id
        String sql = "DELETE FROM posts WHERE id = ?";

        // Execute the SQL statement with the given id parameter
        SQLUtils.executeUpdate(sql, id);
    }

    /**
     * Maps a database result set to a Post object.
     *
     * @param map The map containing the database result set.
     * @return The mapped Post object.
     */
    private Post mapToPost(Map<String, Object> map) {
        Post post = new Post();

        post.setId((Integer) map.get("id"));
        post.setContent((String) map.get("content"));
        post.setAuthor((String) map.get("author"));
        post.setLikes((Integer) map.get("likes"));
        post.setShares((Integer) map.get("shares"));
        post.setDatetime(new Date((Long) map.get("datetime")));
        post.setCreateBy((String) map.get("createBy"));

        return post;
    }
}