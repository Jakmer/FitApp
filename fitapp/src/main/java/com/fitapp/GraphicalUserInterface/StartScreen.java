package com.fitapp.GraphicalUserInterface;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class StartScreen extends Application {

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
     * @param primaryStage
     */
    private void loginScreen(Stage primaryStage) throws IOException {
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

        System.out.println(username);
        System.out.println(password);
    }

}