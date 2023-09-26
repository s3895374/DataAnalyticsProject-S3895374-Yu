package com.data_analytics.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLUtils {

    public static Connection createConnection() throws SQLException {
        // Establish connection
        return DriverManager.getConnection("jdbc:sqlite:db.sqlite");
    }

    public static void executeUpdate(String sql, Object... params) throws SQLException {
        Connection conn = createConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        // set parameters
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        stmt.executeUpdate();
        stmt.close();
    }

    public static long executeInsert(String sql, Object... params) throws SQLException {
        Connection conn = createConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        // set parameters
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        long generatedKey = 0;
        if (rs.next()) {
            generatedKey = rs.getLong(1);
        }
        rs.close();
        stmt.close();
        return generatedKey;
    }

    public static Map<String, Object> executeQuerySingle(String sql, Object... params) throws SQLException {

        Connection conn = createConnection();

        PreparedStatement stmt = conn.prepareStatement(sql);
        // set parameters
        int index = 1;
        for (Object param : params) {
            stmt.setObject(index, param);
            index++;
        }

        ResultSet rs = stmt.executeQuery();

        Map<String, Object> resultMap = new HashMap<>();

        if (rs.next()) {
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();

            for (int i = 1; i <= cols; i++) {
                String key = md.getColumnName(i);
                Object obj = rs.getObject(i);
                resultMap.put(key, obj);
            }
        }

        rs.close();
        stmt.close();
        conn.close();

        return resultMap;

    }

    public static List<Map<String, Object>> executeQuery(String sql, Object... params) throws SQLException {
        Connection conn = createConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        // set parameters
        int index = 1;
        for (Object param : params) {
            stmt.setObject(index, param);
            index++;
        }

        ResultSet rs = stmt.executeQuery();
        List<Map<String, Object>> resultList = new ArrayList<>();
        while (rs.next()) {
            Map<String, Object> rowMap = new HashMap<>();
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();
            for (int i = 1; i <= cols; i++) {
                String key = md.getColumnName(i);
                Object obj = rs.getObject(i);
                rowMap.put(key, obj);
            }
            resultList.add(rowMap);
        }
        rs.close();
        stmt.close();
        conn.close();
        return resultList;
    }

}