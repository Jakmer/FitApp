package com.fitapp.GraphicalUserInterface;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StartScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Hello, JavaFX!");
        StackPane root = new StackPane();
        root.getChildren().add(label);

        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("JavaFX Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}