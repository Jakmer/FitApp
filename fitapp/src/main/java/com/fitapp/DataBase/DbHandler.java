package com.fitapp.DataBase;

import com.fitapp.GraphicalUserInterface.ScreenHandler;
import java.sql.*;
import java.util.ArrayList;

public class DbHandler {
    private final DatabaseConnector databaseConnector;
    private final Connection connection;
    public DbHandler() throws SQLException {
        String configFile = "login/logs.txt";  // Path to config file
        this.databaseConnector = new DatabaseConnector(configFile);
        this.connection = databaseConnector.connect();
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
            String username = ScreenHandler .currentUser;
            Statement statement = connection.createStatement();

            ResultSet currentDay = statement.executeQuery(String.format("SELECT day_number from day;"));
            currentDay.next();
            ScreenHandler.currentDay = Integer.parseInt(currentDay.getString(1));
            System.out.println("Current day: " + ScreenHandler.currentDay);

            ResultSet goalResult = statement.executeQuery(String.format("SELECT goal from users where name = '%s';", username));
            goalResult.next();
            ScreenHandler.goal = Integer.parseInt(goalResult.getString(1));

            System.out.println("Goal: " + ScreenHandler.goal);

            ResultSet Result;
            Result = statement.executeQuery(String.format("select  sum(kcal), sum(protein), sum(fat), sum(carbs) from todayproducts join users on users.id = todayproducts.user_id where users.name = '%s';", username));
            if(Result.next())
            {
                if (Result.getString(1) != null)
                    ScreenHandler.kcal = Integer.parseInt(Result.getString(1));
                else ScreenHandler.kcal = 0;
                if (Result.getString(2) != null)
                    ScreenHandler.protein = Integer.parseInt(Result.getString(2));
                else ScreenHandler.protein = 0;
                if (Result.getString(3) != null)
                    ScreenHandler.fat = Integer.parseInt(Result.getString(3));
                else ScreenHandler.fat = 0;
                if (Result.getString(4) != null)
                    ScreenHandler.carbs = Integer.parseInt(Result.getString(4));
                else ScreenHandler.carbs = 0;
            }
            else
            {
                ScreenHandler.kcal = 0;
                ScreenHandler.protein = 0;
                ScreenHandler.fat = 0;
                ScreenHandler.carbs = 0;
            }

            System.out.println("-----------------");
            ResultSet water_amount = statement.executeQuery(String.format("SELECT ml from water join users on users.id = water.user_id where name = '%s';", ScreenHandler.currentUser));
            if(water_amount.next())
                ScreenHandler.waterProgress = (double) Integer.parseInt(water_amount.getString(1)) / 2000.0;
            else
                ScreenHandler.waterProgress = 0;

            if (ScreenHandler.waterProgress < 0)
                ScreenHandler.waterProgress = 0;
            System.out.println("Water: " + ScreenHandler.waterProgress);

            System.out.println("Kcal: " + ScreenHandler.kcal);
            System.out.println("Protein: " + ScreenHandler.protein);
            System.out.println("Fat: " + ScreenHandler.fat);
            System.out.println("Carbs: " + ScreenHandler.carbs);

        }
        catch (SQLException e)
        {
            //System.out.println(e.getSQLState());
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

    public String handleAddWorkout(String username ,String activityName, String kcal) throws SQLException {

        try
        {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(String.format("SELECT add_workout('%s', '%s', '%s');", username ,activityName, kcal));
        result.next();
        return result.getString(1);

        }
        catch (SQLException e)
        {
            System.out.println(e.getSQLState());
            return "Error";
        }
    }

    public ArrayList<String> handleSearchActivity(String searchedActivity)
    {
        String name = "", kcal = "";
        ArrayList<String> items = new ArrayList<>();
        try
        {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(String.format("SELECT * from baseworkout where name ilike '%%%s%%' ;", searchedActivity));

            while(result.next()) {
                name = result.getString("name");
                kcal = result.getString("kcal");
                String item = name + "  " + kcal;
                items.add(item);

            }}
        catch (SQLException e)
        {
            System.out.println(e.getSQLState());
            items.clear();
            items.add("Problems encountered while searching for activity");
            return items;
        }
        if(items.isEmpty())
        {
            items.add("No activities found");
        }
        return items;
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
             String item = name + "  "+ protein + "  " + fat + "  " + carbs + "  " + kcal;
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

    public ArrayList<String> handleDailyProducts() throws SQLException {

        String name = "", protein = "", fat = "", carbs = "", kcal = "";
        ArrayList<String> items = new ArrayList<>();
        try
        {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(String.format("select todayproducts.name, protein, fat, carbs, kcal from todayproducts join users on users.id = todayproducts.user_id where users.name = '%s';", ScreenHandler.currentUser));

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
            items.add("No products today");
        }
        return items;
    }

    public void handleAddWater() throws SQLException {
        try
        {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(String.format("SELECT add_water('%s');", ScreenHandler.currentUser));
        result.next();

        ResultSet water_amount = statement.executeQuery(String.format("SELECT ml from water join users on users.id = water.user_id where name = '%s';", ScreenHandler.currentUser));
        water_amount.next();

        int user_water = Integer.parseInt(water_amount.getString(1));
        int total_water = 2000;

        ScreenHandler.waterProgress = (double) user_water / total_water;
        if (ScreenHandler.waterProgress < 0)
            ScreenHandler.waterProgress = 0;

        System.out.println("Add Water: " + ScreenHandler.waterProgress);

        }
        catch (SQLException e)
        {
            System.out.println(e.getSQLState());
        }
    }

    public void handleNextDay() throws SQLException {
        try
        {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(String.format("call clearandmovetohistory();"));
        result.next();

        /*ScreenHandler.kcal = 0;
        ScreenHandler.protein = 0;
        ScreenHandler.fat = 0;
        ScreenHandler.carbs = 0;
        ScreenHandler.waterProgress = 0;*/

        updateValues();

        }
        catch (SQLException e)
        {
            System.out.println(e.getSQLState());
        }
    }

    public boolean getHistory(int day) throws SQLException {
        try
        {
        Statement statement = connection.createStatement();

        ResultSet thisDay = statement.executeQuery(String.format("SELECT day_number from day;"));
        thisDay.next();
        int currentDay = Integer.parseInt(thisDay.getString(1));
        if(day>currentDay)
            return true;

        ResultSet result = statement.executeQuery(String.format("SELECT * from history join users on users.id = history.user_id where day = '%s' and users.name = '%s';", day, ScreenHandler.currentUser));
        result.next();

        ScreenHandler.currentDay = Integer.parseInt(result.getString("day"));
        ScreenHandler.kcal = Integer.parseInt(result.getString("kcal"));
        ScreenHandler.protein = Integer.parseInt(result.getString("protein"));
        ScreenHandler.fat = Integer.parseInt(result.getString("fat"));
        ScreenHandler.carbs = Integer.parseInt(result.getString("carbs"));
        ScreenHandler.waterProgress = (double) Integer.parseInt(result.getString("water")) / 2000;
        if (ScreenHandler.waterProgress < 0)
            ScreenHandler.waterProgress = 0;
        }
        catch (SQLException e)
        {
            System.out.println(e.getSQLState());
            return true;
        }
        return false;
    }

    public void clearHistory() throws SQLException {
        try
        {
        Statement statement = connection.createStatement();
        System.out.println("Clearing history");

        //ResultSet result = statement.executeQuery(String.format("call clear_history('%s');", ScreenHandler.currentUser));
        //result.next();
        }
        catch (SQLException e)
        {
            System.out.println(e.getSQLState());
        }
    }

    public void closeConnection() {
        databaseConnector.closeConnection(this.connection);
    }
}
