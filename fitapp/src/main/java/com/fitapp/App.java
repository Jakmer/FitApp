package com.fitapp;

import com.fitapp.DataBase.DatabaseConnector;
import com.fitapp.DataBase.DbHandler;
import com.fitapp.GraphicalUserInterface.StartScreen;
import javafx.application.Application;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException {
        try {
            Application.launch(StartScreen.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
