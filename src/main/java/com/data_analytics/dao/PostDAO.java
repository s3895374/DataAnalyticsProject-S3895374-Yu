package com.data_analytics.dao;

import com.data_analytics.models.Post;
import com.data_analytics.utils.SQLUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PostDAO {

    public void save(Post post) throws SQLException {
        String sql = "INSERT INTO posts (id, content, author, likes, shares, datetime) VALUES (?, ?, ?, ?, ?, ?)";

        SQLUtils.executeUpdate(sql, post.getId(), post.getContent(), post.getAuthor(),
                post.getLikes(), post.getShares(), post.getDatetime());
    }

    public Post findById(int id) throws SQLException {
        String sql = "SELECT * FROM posts WHERE id = ?";

        Map<String, Object> result = SQLUtils.executeQuerySingle(sql, id);

        // construct Post from result map
        return mapToPost(result);
    }

    public List<Post> findAll() throws SQLException {
        String sql = "SELECT * FROM posts";

        List<Map<String, Object>> results = SQLUtils.executeQuery(sql);

        // convert each map to Post and add to list
        List<Post> posts = new ArrayList<>();
        for (Map<String, Object> result : results) {
            posts.add(mapToPost(result));
        }
        return posts;
    }

    public void update(Post post) throws SQLException {
        String sql = "UPDATE posts SET content = ?, author = ?, likes = ?, shares = ?, datetime = ? " +
                "WHERE id = ?";

        SQLUtils.executeUpdate(sql, post.getContent(), post.getAuthor(), post.getLikes(),
                post.getShares(), post.getDatetime(), post.getId());
    }

    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM posts WHERE id = ?";

        SQLUtils.executeUpdate(sql, id);
    }


    private Post mapToPost(Map<String, Object> map) {
        Post post = new Post();

        post.setId((Integer) map.get("id"));
        post.setContent((String) map.get("content"));
        post.setAuthor((String) map.get("author"));
        post.setLikes((Integer) map.get("likes"));
        post.setShares((Integer) map.get("shares"));
        post.setDatetime((Date) map.get("datetime"));

        return post;
    }
}