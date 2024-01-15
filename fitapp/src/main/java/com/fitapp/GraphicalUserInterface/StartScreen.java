package com.fitapp.GraphicalUserInterface;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class StartScreen extends Application {

    public Label incorrectLabel;
    @FXML
    private javafx.scene.control.TextField usernameField;
    @FXML
    private javafx.scene.control.TextField passwordField;

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
    private void handleLoginButtonAction() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        /**
         * database is responsible for checking if the user exists and if the password is correct
         * for let's suppose that the username is "admin" and the password is "admin"
         */
        System.out.println(username);
        System.out.println(password);

        if(username.isEmpty() || password.isEmpty()){
            incorrectLabel.setText("Please enter username and password");
            incorrectLabel.setVisible(true);
            usernameField.clear();
            passwordField.clear();
        }
        else if (username.equals("admin") && password.equals("admin")) {
            System.out.println("Login successful");
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
            Scene scene = new Scene(root, 817, 812);
            Stage primaryStage = new Stage();
            primaryStage.setResizable(false);
            primaryStage.setTitle("FitApp");
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            incorrectLabel.setVisible(true);
            usernameField.clear();
            passwordField.clear();
        }
    }
}





