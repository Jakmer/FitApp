package com.fitapp.GraphicalUserInterface;

import com.fitapp.DataBase.DbHandler;
import com.sun.javafx.charts.Legend;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class StartScreen extends Application {

    public static String currentUser;
    private FXMLLoader mainScreenLoader;
    public static int goal;
    public static int kcal;

    @FXML
    public Label KcalLabel;
    @FXML
    public Label ProteinLabel;
    @FXML
    public Label FatLabel;
    @FXML
    public Label CarbsLabel;
    @FXML
    public Label GoalLabel;
    @FXML
    public ProgressBar goalProgressBar;
    @FXML
    public Label totalGoalLabel;
    @FXML
    public Label goalProgress;

    @Override
    public void start(Stage stage) throws Exception {

    }



















/*
   @FXML
    private void updateMainScreen(int kcal, int protein, int fat, int carbs)
    {
        KcalLabel.setText(String.valueOf(kcal));
        ProteinLabel.setText(String.valueOf(protein));
        FatLabel.setText(String.valueOf(fat));
        CarbsLabel.setText(String.valueOf(carbs));
        totalGoalLabel.setText(goal +"kcal");

    }

    @FXML
    public void updateMainScreen() throws SQLException {

        dbHandler.updateValues();

        goalProgressBar.setProgress((double)kcal/goal);

        goalProgress.setText(String.valueOf((kcal)));
        KcalLabel.setText(String.valueOf(kcal));
        totalGoalLabel.setText(goal +"kcal");

        System.out.println(KcalLabel.getText());
        System.out.println(totalGoalLabel.getText());
        System.out.println(goalProgress.getText());
    }*/

}





