package com.fitapp.GraphicalUserInterface;

import com.fitapp.DataBase.DbHandler;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.Objects;

public class MainScreen extends Application {
    private DbHandler dbHandler;

    @FXML
    private Label KcalLabel;
    @FXML
    private Label ProteinLabel;
    @FXML
    private Label FatLabel;
    @FXML
    private Label CarbsLabel;
    @FXML
    private Label GoalLabel;
    @FXML
    private ProgressBar goalProgressBar;
    @FXML
    private Label totalGoalLabel;
    @FXML
    private Label goalProgress;


    public MainScreen() throws SQLException {
        this.dbHandler = new DbHandler();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainScreen.fxml")));

        Scene scene = new Scene(root, 817, 812);
        Stage primaryStage = new Stage();
        primaryStage.setResizable(false);
        primaryStage.setTitle("FitApp");
        primaryStage.setScene(scene);
        primaryStage.show();
        update();
    }

    @FXML
    private void handleAddMealButtonAction() throws Exception {
        try
        {
            AddMealScreen addMealScreen = new AddMealScreen();
            addMealScreen.setMainController(this);
            addMealScreen.start(new Stage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    public void update()
    {
        try {
            dbHandler.updateValues();
            KcalLabel.setText(String.valueOf(ScreenHandler.kcal));
            ProteinLabel.setText(String.valueOf(ScreenHandler.protein));
            FatLabel.setText(String.valueOf(ScreenHandler.fat));
            CarbsLabel.setText(String.valueOf(ScreenHandler.carbs));
            goalProgressBar.setProgress((double) ScreenHandler.kcal / ScreenHandler.goal);
            totalGoalLabel.setText(String.valueOf(ScreenHandler.goal));
            goalProgress.setText(String.valueOf(ScreenHandler.kcal));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSettingsButtonAction() throws Exception
    {
        System.out.println("Settings");
        update();
    }

}
