package com.fitapp.DataBase;

import com.fitapp.GraphicalUserInterface.StartScreen;

import java.sql.*;
import java.util.ArrayList;

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

    public String handleCreateUser(String username, String password, String goal, String weight) throws SQLException {

        try
        {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(String.format("SELECT add_to_logs2('%s', '%s', '%s', '%s');", username, password, goal, weight));
        result.next();
        return result.getString(1);
        }
        catch (SQLException e)
        {
            return "Error";
        }
    }

    public void updateValues() throws SQLException {
        try {
            String username = StartScreen.currentUser;
            Statement statement = connection.createStatement();

            ResultSet goalResult = statement.executeQuery(String.format("SELECT goal from users where name = '%s';", username));
            goalResult.next();
            StartScreen.goal = Integer.parseInt(goalResult.getString(1));

            System.out.println("Goal: " + StartScreen.goal);

            ResultSet kcalResult = statement.executeQuery(String.format("select sum(kcal) from todayproducts join users on users.id = todayproducts.user_id where users.name = '%s';", username));
            kcalResult.next();
            if(kcalResult.getString(1) == null)
            {
                StartScreen.kcal = 0;
            }
            else
                StartScreen.kcal = Integer.parseInt(kcalResult.getString(1));



            System.out.println("Kcal: " + StartScreen.kcal);
        }
        catch (SQLException e)
        {
            System.out.println(e.getSQLState());
        }
    }

    public String handleLogin(String username, String password) throws SQLException {

        try
        {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(String.format("SELECT check_password('%s', '%s');", username, password));
        result.next();
        return result.getString(1);
        }
        catch (SQLException e)
        {
            return "Error";
        }
    }

    public String handleAddProduct(String username ,String productName, String protein, String fat, String carbs, String kcal) throws SQLException {

        try
        {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(String.format("SELECT add_meal('%s', '%s', '%s', '%s', '%s', '%s');", username ,productName, protein, fat, carbs, kcal));
        result.next();
        return result.getString(1);

        }
        catch (SQLException e)
        {
            System.out.println(e.getSQLState());
            return "Error";
        }
    }

    public ArrayList<String> handleSearchProduct(String productName) throws SQLException {

        String name = "", protein = "", fat = "", carbs = "", kcal = "";
        ArrayList<String> items = new ArrayList<>();
        try
        {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(String.format("SELECT * from baseproducts where name ilike '%%%s%%' ;", productName));

        while(result.next()) {
             name = result.getString("name");
             protein = result.getString("protein");
             fat = result.getString("fat");
             carbs = result.getString("carbs");
             kcal = result.getString("kcal");
             String item = name + " "+ protein + " " + fat + " " + carbs + " " + kcal;
             items.add(item);

        }}
        catch (SQLException e)
        {
            System.out.println(e.getSQLState());
            items.clear();
            items.add("Problems encountered while searching for product");
            return items;
        }
        if(items.isEmpty())
        {
            items.add("No products found");
        }
        return items;
    }

    public void closeConnection() {
        databaseConnector.closeConnection(this.connection);
    }
}
