package com.fitapp.GraphicalUserInterface;

import com.fitapp.DataBase.DbHandler;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginScreen extends Application {

    @FXML
    private javafx.scene.control.Label incorrectLabel;
    @FXML
    private javafx.scene.control.TextField usernameField;
    @FXML
    private javafx.scene.control.TextField passwordField;

    private final DbHandler dbHandler;

    public LoginScreen() throws SQLException {
        this.dbHandler = new DbHandler();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginScreen.fxml")));

        Scene scene = new Scene(root, 817, 812);

        stage.setTitle("FitApp");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleLoginButtonAction() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println(username);
        System.out.println(password);

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
                System.out.println(username +" logged in");
                ScreenHandler.currentUser = username;

                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.close();

                MainScreen mainScreen = new MainScreen();
                mainScreen.start(new Stage());
            }
        }
    }

    @FXML
    private void handleSignUpButtonAction() throws Exception {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();

        SignUpScreen signUpScreen = new SignUpScreen();
        signUpScreen.start(new Stage());

    }
}
