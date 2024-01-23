package com.fitapp.GraphicalUserInterface;

import javafx.application.Application;

public class ScreenHandler {
    public static String currentUser;
    public static int goal;
    public static int kcal;
    public static int protein;
    public static int fat;
    public static int carbs;

    public void startScreen(){
        Application.launch(LoginScreen.class);

    }
}
