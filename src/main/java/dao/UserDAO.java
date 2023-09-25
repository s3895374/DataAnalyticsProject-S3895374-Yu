package dao;

import models.User;
import utils.SQLUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDAO {

    public void save(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, first_name, last_name, is_vip) " +
                "VALUES (?, ?, ?, ?, ?)";

        SQLUtils.executeUpdate(sql, user.getUsername(), user.getPassword(),
                user.getFirstName(), user.getLastName(), user.isVIP());
    }

    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";

        Map<String, Object> result = SQLUtils.executeQuerySingle(sql, username);

        return mapToUser(result);
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM users";

        List<Map<String, Object>> results = SQLUtils.executeQuery(sql);

        List<User> users = new ArrayList<>();
        for (Map<String, Object> result : results) {
            users.add(mapToUser(result));
        }
        return users;
    }

    public void update(User user) throws SQLException {

        String sql = "UPDATE users SET password = ?, first_name = ?, last_name = ?, is_vip = ? " +
                "WHERE username = ?";

        SQLUtils.executeUpdate(sql, user.getPassword(), user.getFirstName(), user.getLastName(),
                user.isVIP(), user.getUsername());

    }

    public void deleteByUsername(String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";
        SQLUtils.executeUpdate(sql, username);
    }

    private User mapToUser(Map<String, Object> map) {
        User user = new User();

        user.setUsername((String)map.get("username"));
        user.setPassword((String)map.get("password"));
        user.setFirstName((String)map.get("first_name"));
        user.setLastName((String)map.get("last_name"));
        user.setVIP((Boolean)map.get("is_vip"));

        return user;

    }
}