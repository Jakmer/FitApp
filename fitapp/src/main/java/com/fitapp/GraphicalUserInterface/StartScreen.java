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
import sun.text.resources.ext.JavaTimeSupplementary_sq;

import java.io.IOException;
import java.sql.SQLException;

public class StartScreen extends Application {

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
    @FXML
    private Label incorrectLabel;
    @FXML
    private javafx.scene.control.TextField usernameField;
    @FXML
    private javafx.scene.control.TextField passwordField;
    private DbHandler dbHandler;

    public StartScreen() throws SQLException {
        this.dbHandler = new DbHandler();
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            loginScreen(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates the login screen
     *
     * @param primaryStage
     */
    private void loginScreen(Stage primaryStage) throws IOException {
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/LoginScreen.fxml"));

        Scene scene = new Scene(root, 817, 812);

        primaryStage.setTitle("FitApp");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @FXML
    private void handleLoginButtonAction() throws IOException, SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        /**
         * database is responsible for checking if the user exists and if the password is correct
         * for let's suppose that the username is "admin" and the password is "admin"
         */
        System.out.println(username);
        System.out.println(password);
        /**
         * TODO: send data to database and database will validate it
         * if its good then open the main screen
         */

        if(username.isEmpty() || password.isEmpty()){
            incorrectLabel.setText("Please enter username and password");
            incorrectLabel.setVisible(true);
            usernameField.clear();
            passwordField.clear();
        }
        else
        {
            String result = dbHandler.handleLogin(username, password);
            System.out.println(result);

            if(result.equals("f"))
            {
                incorrectLabel.setText("Incorrect password");
                incorrectLabel.setVisible(true);
            }
            else if(result.equals("Error"))
            {
                incorrectLabel.setText("Failed to login");
                incorrectLabel.setVisible(true);
            }
            else
            {
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.close();
                Parent root = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
                Scene scene = new Scene(root, 817, 812);
                Stage primaryStage = new Stage();
                primaryStage.setResizable(false);
                primaryStage.setTitle("FitApp");
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        }
    }

    @FXML
    private void handleSignUpButtonAction() throws IOException {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/SignUpScreen.fxml"));
        Scene scene = new Scene(root, 817, 812);
        Stage primaryStage = new Stage();
        primaryStage.setResizable(false);
        primaryStage.setTitle("FitApp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void handleCreateAccountButtonAction() throws IOException, SQLException {
        /**
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
                /**
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
                    isCreated = true;
                }

                if(isCreated){

                    Stage stage = (Stage) SUusernameField.getScene().getWindow();
                    stage.close();
                    Parent root = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
                    Scene scene = new Scene(root, 817, 812);
                    Stage primaryStage = new Stage();
                    primaryStage.setResizable(false);
                    primaryStage.setTitle("FitApp");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                }
            }

        }

    }

}





