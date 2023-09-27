package com.data_analytics.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides utility methods for executing SQL queries and updates.
 */
public class SQLUtils {

    /**
     * Creates a new database connection.
     *
     * @return the database connection
     * @throws SQLException if a database access error occurs
     */
    public static Connection createConnection() throws SQLException {
        // Establish connection
        return DriverManager.getConnection("jdbc:sqlite:db.sqlite");
    }

    /**
     * Executes an SQL update statement.
     *
     * @param sql the SQL statement to be executed
     * @param params the parameters to be used in the SQL statement
     * @throws SQLException if a database access error occurs
     */
    public static void executeUpdate(String sql, Object... params) throws SQLException {
        // Create a connection to the database
        Connection conn = createConnection();

        // Prepare a SQL statement
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Set parameters for the SQL statement
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        // Execute the SQL statement and update the database
        stmt.executeUpdate();

        // Close the prepared statement
        stmt.close();
    }

    /**
     * Executes an SQL insert statement and returns the generated key.
     *
     * @param sql the SQL statement to be executed
     * @param params the parameters to be used in the SQL statement
     * @return the generated key
     * @throws SQLException if a database access error occurs
     */
    public static long executeInsert(String sql, Object... params) throws SQLException {
        // Create a connection to the database
        Connection conn = createConnection();
        conn.setAutoCommit(false);
        // Prepare a statement with the given SQL query and return generated keys
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        // Set parameters for the SQL statement
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        stmt.executeUpdate();

        // Execute the update query
        long generatedKey = 0;
        Statement statement = conn.createStatement();
        ResultSet generatedKeys = statement.executeQuery("SELECT last_insert_rowid()");
        if (generatedKeys.next()) {
            generatedKey = generatedKeys.getLong(1);
        }
        conn.commit();

        // Return the generated key
        return generatedKey;
    }

    /**
     * Executes an SQL query and returns a single row as a map of column names to values.
     *
     * @param sql the SQL statement to be executed
     * @param params the parameters to be used in the SQL statement
     * @return a map representing a single row of the query result
     * @throws SQLException if a database access error occurs
     */
    public static Map<String, Object> executeQuerySingle(String sql, Object... params) throws SQLException {

        // Create a connection to the database
        Connection conn = createConnection();

        // Prepare a SQL statement
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Set parameters for the SQL statement
        int index = 1;
        for (Object param : params) {
            stmt.setObject(index, param);
            index++;
        }

        // Execute the SQL statement and get the result set
        ResultSet rs = stmt.executeQuery();

        // Create a map to store the result
        Map<String, Object> resultMap = new HashMap<>();

        // Check if there is a next row in the result set
        if (rs.next()) {
            // Get the metadata of the result set
            ResultSetMetaData md = rs.getMetaData();
            // Get the number of columns in the result set
            int cols = md.getColumnCount();

            // Iterate through each column
            for (int i = 1; i <= cols; i++) {
                // Get the column name
                String key = md.getColumnName(i);
                // Get the value of the column
                Object obj = rs.getObject(i);
                // Put the column name and value into the map
                resultMap.put(key, obj);
            }
        }

        // Close the result set, statement, and connection
        rs.close();
        stmt.close();
        conn.close();

        // Return the result map
        return resultMap;

    }

    /**
     * Executes an SQL query and returns the result as a list of maps, where each map represents a row
     * with column names as keys and corresponding values.
     *
     * @param sql the SQL statement to be executed
     * @param params the parameters to be used in the SQL statement
     * @return a list of maps representing the query result
     * @throws SQLException if a database access error occurs
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) throws SQLException {
        // Create a connection to the database
        Connection conn = createConnection();

        // Prepare a SQL statement
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Set parameters for the SQL statement
        int index = 1;
        for (Object param : params) {
            stmt.setObject(index, param);
            index++;
        }

        // Execute the SQL statement and get the result set
        ResultSet rs = stmt.executeQuery();

        // Create a list to store the result
        List<Map<String, Object>> resultList = new ArrayList<>();

        // Iterate through the result set
        while (rs.next()) {
            // Create a map to store each row of data
            Map<String, Object> rowMap = new HashMap<>();

            // Get the metadata of the result set
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();

            // Iterate through each column of the row
            for (int i = 1; i <= cols; i++) {
                // Get the column name and value
                String key = md.getColumnName(i);
                Object obj = rs.getObject(i);

                // Add the column name and value to the row map
                rowMap.put(key, obj);
            }

            // Add the row map to the result list
            resultList.add(rowMap);
        }

        // Close the result set, statement, and connection
        rs.close();
        stmt.close();
        conn.close();

        // Return the result list
        return resultList;
    }

}