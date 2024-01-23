package com.fitapp.GraphicalUserInterface;

import com.fitapp.DataBase.DbHandler;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.Objects;

public class SignUpScreen extends Application {

    @FXML
    private Label SUincorrectLabel;
    @FXML
    private TextField SUusernameField;
    @FXML
    private TextField SUpasswordField;
    @FXML
    private TextField SUconfirmPasswordField;
    @FXML
    private TextField SUgoalField;
    @FXML
    private TextField SUweightField;

    private final DbHandler dbHandler;

    public SignUpScreen() throws SQLException {
        this.dbHandler = new DbHandler();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/SignUpScreen.fxml")));
        Scene scene = new Scene(root, 817, 812);
        Stage primaryStage = new Stage();
        primaryStage.setResizable(false);
        primaryStage.setTitle("FitApp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void handleCreateAccountButtonAction() throws Exception {
        /*
         * database is responsible for provided data validation
         */
        if(SUusernameField.getText().isEmpty() || SUpasswordField.getText().isEmpty() || SUconfirmPasswordField.getText().isEmpty() || SUgoalField.getText().isEmpty() || SUweightField.getText().isEmpty())
        {
            SUusernameField.clear();
            SUpasswordField.clear();
            SUconfirmPasswordField.clear();
            SUgoalField.clear();
            SUweightField.clear();
            SUincorrectLabel.setText("Please enter valid data");
            SUincorrectLabel.setVisible(true);

        }else {
            String username = SUusernameField.getText();
            String password = SUpasswordField.getText();
            String confirmPassword = SUconfirmPasswordField.getText();
            String goal = SUgoalField.getText();
            String weight = SUweightField.getText();

            if(!password.equals(confirmPassword)){
                SUincorrectLabel.setText("Passwords do not match");
                SUincorrectLabel.setVisible(true);
                SUpasswordField.clear();
                SUconfirmPasswordField.clear();
            }
            else {
                /*
                 * TODO: send data to database and database will validate it
                 * if its good then create the account and open the main screen
                 */
                boolean isCreated = false;
                String result = dbHandler.handleCreateUser(username, password, goal, weight);
                System.out.println(result);

                if(result.equals("Error"))
                {
                    SUincorrectLabel.setText("Failed to create account");
                }
                else
                {
                    ScreenHandler.currentUser = username;
                    isCreated = true;
                }

                if(isCreated){

                    Stage stage = (Stage) SUusernameField.getScene().getWindow();
                    stage.close();

                    MainScreen mainScreen = new MainScreen();
                    mainScreen.start(new Stage());
                    mainScreen.update();
                }
            }

        }

    }

}
