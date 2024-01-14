package com.fitapp.DataBase;

import java.sql.*;

public class DbHandler {
    private String configFile;
    private DatabaseConnector databaseConnector;
    private Connection connection;
    public DbHandler() throws SQLException {
        this.configFile = "login/logs.txt";  // Path to config file
        this.databaseConnector = new DatabaseConnector(configFile);
        this.connection = databaseConnector.connect();
    }

    public void handleQuery(String query) throws SQLException {

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);


        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String user_id = resultSet.getString("user_id");
            Date date = resultSet.getDate("date");
            int protein = resultSet.getInt("protein");
            int fat = resultSet.getInt("fat");
            int carbs = resultSet.getInt("carbs");
            int kcal = resultSet.getInt("kcal");

            System.out.println(id + " " + user_id + " " + date + " " + protein + " " + fat + " " + carbs + " " + kcal);
        }
    }
    public void closeConnection() {
        databaseConnector.closeConnection(this.connection);
    }
}
