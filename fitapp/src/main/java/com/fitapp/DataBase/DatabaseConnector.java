package com.fitapp.DataBase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DatabaseConnector {

    private String jdbcUrl;
    private String username;
    private String password;

    public DatabaseConnector(String configFile) {
        readConfig(configFile);
    }

    private void readConfig(String configFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            jdbcUrl = reader.readLine();
            username = reader.readLine();
            password = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection connect() throws SQLException {
        try {
            String sslMode = "require";
            String sslFactory = "org.postgresql.ssl.NonValidatingFactory";
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(jdbcUrl, username, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
