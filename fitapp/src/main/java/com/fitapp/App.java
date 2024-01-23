package com.fitapp;

import com.fitapp.GraphicalUserInterface.ScreenHandler;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) {
        try {
            ScreenHandler screenHandler = new ScreenHandler();
            screenHandler.startScreen();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
