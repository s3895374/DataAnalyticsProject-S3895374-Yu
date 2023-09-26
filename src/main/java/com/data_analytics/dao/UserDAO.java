package com.data_analytics.dao;

import com.data_analytics.models.User;
import com.data_analytics.utils.SQLUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class provides data access methods for the User model.
 */
public class UserDAO {

    /**
     * Saves a user to the database.
     *
     * @param user The user to be saved.
     * @throws SQLException If an error occurs while saving the user.
     */
    public void save(User user) throws SQLException {
        // Define the SQL query for inserting user data into the database
        String sql = "INSERT INTO users (username, password, first_name, last_name, is_vip) " +
                "VALUES (?, ?, ?, ?, ?)";

        // Execute the SQL query with the provided user data
        SQLUtils.executeUpdate(sql, user.getUsername(), user.getPassword(),
                user.getFirstName(), user.getLastName(), user.isVIP());
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to be found.
     * @return The found user, or null if no user is found.
     * @throws SQLException If an error occurs while finding the user.
     */
    public User findByUsername(String username) throws SQLException {
        // Define the SQL query to select all columns from the "users" table where the username matches a parameter
        String sql = "SELECT * FROM users WHERE username = ?";

        // Execute the SQL query with the provided username parameter and store the result in a map
        Map<String, Object> result = SQLUtils.executeQuerySingle(sql, username);

        // Check if the result map is empty, indicating that no user was found with the given username
        if (result.size() == 0) {
            return null;
        }

        // Convert the result map to a User object and return it
        return mapToUser(result);
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all users.
     * @throws SQLException If an error occurs while retrieving the users.
     */
    public List<User> findAll() throws SQLException {
        // Define the SQL query to select all rows from the "users" table
        String sql = "SELECT * FROM users";

        // Execute the SQL query and store the results in a list of maps
        List<Map<String, Object>> results = SQLUtils.executeQuery(sql);

        // Create a list to store the User objects
        List<User> users = new ArrayList<>();

        // Iterate through each map in the results list and convert it to a User object
        for (Map<String, Object> result : results) {
            users.add(mapToUser(result));
        }

        // Return the list of User objects
        return users;
    }

    /**
     * Updates a user in the database.
     *
     * @param user The user to be updated.
     * @throws SQLException If an error occurs while updating the user.
     */
    public void update(User user) throws SQLException {

        // SQL query to update user information in the database
        String sql = "UPDATE users SET password = ?, first_name = ?, last_name = ?, is_vip = ? " +
                "WHERE username = ?";

        // Execute the SQL query with the provided user information
        SQLUtils.executeUpdate(sql, user.getPassword(), user.getFirstName(), user.getLastName(),
                user.isVIP(), user.getUsername());

    }

    /**
     * Deletes a user by their username.
     *
     * @param username The username of the user to be deleted.
     * @throws SQLException If an error occurs while deleting the user.
     */
    public void deleteByUsername(String username) throws SQLException {
        // SQL statement to delete a user from the database
        String sql = "DELETE FROM users WHERE username = ?";

        // Execute the SQL statement with the given username parameter
        SQLUtils.executeUpdate(sql, username);
    }

    /**
     * Maps a database result set to a User object.
     *
     * @param map The map containing the database result set.
     * @return The User object mapped from the result set.
     */
    private User mapToUser(Map<String, Object> map) {
        User user = new User();

        user.setUsername((String) map.get("username"));
        user.setPassword((String) map.get("password"));
        user.setFirstName((String) map.get("first_name"));
        user.setLastName((String) map.get("last_name"));
        int v = (int) map.get("is_vip");
        user.setVIP(v == 1);

        return user;

    }
}