package com.data_analytics.controllers;

import com.data_analytics.dao.PostDAO;
import com.data_analytics.models.Post;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The ShareStatsController class is responsible for controlling the ShareStats.fxml view.
 * It provides methods to initialize the controller and handle the logic for displaying share statistics.
 */
public class ShareStatsController {

    @FXML
    private PieChart chart;
    private PostDAO postDAO = new PostDAO();

    /**
     * Initializes the ShareStatsController.
     */
    public void initialize() {
        // Query DB
        List<Post> posts = new ArrayList<>();
        try {
            posts = postDAO.findAll();
        } catch (SQLException e) {
        }

        // Categorize posts into buckets
        int lowShares = 0; // 0 - 99 shares
        int midShares = 0; // 100 - 999 shares
        int highShares = 0; // 1000+ shares

        for (Post post : posts) {
            if (post.getShares() >= 1000) {
                highShares++;
            } else if (post.getShares() >= 100) {
                midShares++;
            } else {
                lowShares++;
            }
        }

        // Create chart
        PieChart.Data lowSlice = new PieChart.Data("Low Shares(0-99)", lowShares);
        PieChart.Data midSlice = new PieChart.Data("Mid Shares(100-999)", midShares);
        PieChart.Data highSlice = new PieChart.Data("High Shares(1000+)", highShares);
        
        chart.getData().addAll(lowSlice, midSlice, highSlice);
    }
}