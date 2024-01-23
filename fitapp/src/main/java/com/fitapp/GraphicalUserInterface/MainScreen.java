package com.fitapp.GraphicalUserInterface;

import com.fitapp.DataBase.DbHandler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainScreen extends Application {
    private final DbHandler dbHandler;

    @FXML
    public ListView<String> DailyList;
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
    private ProgressBar waterProgressBar;
    @FXML
    private Label totalGoalLabel;
    @FXML
    private Label goalProgress;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label dayLabel;
    @FXML
    private javafx.scene.control.Button nextDayButton;
    @FXML
    private javafx.scene.control.Button addMealButton;
    @FXML
    private javafx.scene.control.Button addWaterButton;
    @FXML
    private javafx.scene.control.Button addActivityButton;


    public MainScreen() throws SQLException {
        this.dbHandler = new DbHandler();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
        Parent root = loader.load();

        MainScreen controller = loader.getController();
        controller.update();

        Scene scene = new Scene(root, 817, 812);
        Stage primaryStage = new Stage();
        primaryStage.setResizable(false);
        primaryStage.setTitle("FitApp");
        primaryStage.setScene(scene);
        primaryStage.show();
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

            waterProgressBar.setProgress(ScreenHandler.waterProgress);

            dayLabel.setText("Day:  "+ ScreenHandler.currentDay);

            ArrayList<String> todayProducts = dbHandler.handleDailyProducts();
            ObservableList<String> observableList = FXCollections.observableArrayList(todayProducts);
            DailyList.setItems(observableList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSettingsButtonAction() throws Exception
    {
        System.out.println("Settings");
        update();
        nextDayButton.setVisible(true);
        addActivityButton.setVisible(true);
        addMealButton.setVisible(true);
        addWaterButton.setVisible(true);

    }

    @FXML
    public void handleAddWaterButtonAction() throws Exception
    {
        dbHandler.handleAddWater();

        update();
    }

    @FXML
    public void handleAddActivityButtonAction()
    {
        try {
            AddActivityScreen addActivityScreen = new AddActivityScreen();
            addActivityScreen.setMainController(this);
            addActivityScreen.start(new Stage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void handleNextDayButtonAction(){
        try {
            dbHandler.handleNextDay();
            update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDatePicker() {
        String date = datePicker.getValue().toString();

        date = date.substring(date.length()-2);
        int day = Integer.parseInt(date);

        updateHistory(day);
    }

    @FXML
    public void updateHistory(int day)
    {

        try {

            boolean isGreater = dbHandler.getHistory(day);
            if(isGreater) {
                return;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }



        nextDayButton.setVisible(false);
        addActivityButton.setVisible(false);
        addMealButton.setVisible(false);
        addWaterButton.setVisible(false);

        ScreenHandler.currentDay = day;

        KcalLabel.setText(String.valueOf(ScreenHandler.kcal));
        ProteinLabel.setText(String.valueOf(ScreenHandler.protein));
        FatLabel.setText(String.valueOf(ScreenHandler.fat));
        CarbsLabel.setText(String.valueOf(ScreenHandler.carbs));

        goalProgressBar.setProgress((double) ScreenHandler.kcal / ScreenHandler.goal);
        goalProgress.setText(String.valueOf(ScreenHandler.kcal));

        if(ScreenHandler.kcal<0)
        {
            goalProgressBar.setProgress(0);
        }

        waterProgressBar.setProgress(ScreenHandler.waterProgress);

        dayLabel.setText("Day:  "+ ScreenHandler.currentDay);
        DailyList.setItems(null);

    }

}
