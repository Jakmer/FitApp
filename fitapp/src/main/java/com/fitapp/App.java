package com.fitapp;

import com.fitapp.DataBase.DatabaseConnector;
import com.fitapp.DataBase.DbHandler;
import com.fitapp.GraphicalUserInterface.StartScreen;
import javafx.application.Application;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException {
        try {
            DbHandler dbHandler = new DbHandler();
            dbHandler.handleQuery("SELECT * FROM history");

            Application.launch(StartScreen.class, args);
            dbHandler.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
