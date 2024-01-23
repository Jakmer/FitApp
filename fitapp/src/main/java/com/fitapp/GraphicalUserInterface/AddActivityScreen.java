package com.fitapp.GraphicalUserInterface;

import com.fitapp.DataBase.DbHandler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class AddActivityScreen extends Application {

    @FXML
    private TextField searchActivityField;
    @FXML
    private TextField activityNameField;
    @FXML
    private TextField kcalField;
    @FXML
    private Label AddActivityError;
    @FXML
    public ListView<String> activityList;
    private final DbHandler dbHandler;
    private MainScreen mainScreen;
    public AddActivityScreen() throws SQLException {
        this.dbHandler = new DbHandler();
    }

    public void setMainController(MainScreen mainScreen) {
        System.out.println(mainScreen);
        this.mainScreen = mainScreen;
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddActivityScreen.fxml"));
        Parent root = loader.load();

        AddActivityScreen controller = loader.getController();
        controller.setMainController(mainScreen);

        Scene scene = new Scene(root);
        Stage additionalStage = new Stage();
        additionalStage.setResizable(false);
        additionalStage.setTitle("FitApp");
        additionalStage.setScene(scene);
        additionalStage.show();
    }

    @FXML
    private void handleAddActButtonAction() throws IOException, SQLException {

        String activityName = activityNameField.getText();
        String kcal = kcalField.getText();

        String result = "";

        if(activityList.getSelectionModel().getSelectedItem() != null)
        {
            String activity = activityList.getSelectionModel().getSelectedItem().toString();
            String[] activityData = activity.split("  ");
            for (String activityDatum : activityData) {
                System.out.println(activityDatum);

            }
            result = dbHandler.handleAddWorkout(ScreenHandler.currentUser, activityData[0], activityData[1]);
        }
        else if(activityName.isEmpty() || kcal.isEmpty())
        {
            activityNameField.clear();
            kcalField.clear();
            AddActivityError.setVisible(true);
        }
        else {
            System.out.println(ScreenHandler.currentUser);
            result = dbHandler.handleAddWorkout(ScreenHandler.currentUser, activityName, kcal);
        }

        if(result.equals("Error"))
        {
            AddActivityError.setText("Failed to add product");
            AddActivityError.setVisible(true);
        }
        else
        {
            System.out.println(mainScreen);
            if(mainScreen != null)
                mainScreen.update();
            else
                System.out.println("MainScreen is null");

            Stage stage = (Stage) this.activityNameField.getScene().getWindow();
            stage.close();

        }

    }

    public void handleSearchActivityButtonAction() throws SQLException {
        String searchedActivity = searchActivityField.getText();

        if(!searchedActivity.isEmpty())
        {
            ArrayList<String> items = dbHandler.handleSearchActivity(searchedActivity);
            // Convert ArrayList to ObservableList
            ObservableList<String> observableList = FXCollections.observableArrayList(items);

            // Set items to ListView
            activityList.setItems(observableList);
        }
    }

}
