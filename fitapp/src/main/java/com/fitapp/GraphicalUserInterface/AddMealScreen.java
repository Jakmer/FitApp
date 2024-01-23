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
import java.util.Objects;

public class AddMealScreen extends Application {

    @FXML
    private TextField searchProductField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField proteinField;
    @FXML
    private TextField fatField;
    @FXML
    private TextField carbsField;
    @FXML
    private TextField kcalField;
    @FXML
    private Label AddMealError;
    @FXML
    public ListView<String> productList;
    private final DbHandler dbHandler;
    private MainScreen mainScreen;
    public AddMealScreen() throws SQLException {
        this.dbHandler = new DbHandler();
    }

    public void setMainController(MainScreen mainScreen) {
        System.out.println(mainScreen);
        this.mainScreen = mainScreen;
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddMealScreen.fxml"));
        Parent root = loader.load();

        AddMealScreen controller = loader.getController();
        controller.setMainController(mainScreen);

        Scene scene = new Scene(root);
        Stage additionalStage = new Stage();
        additionalStage.setResizable(false);
        additionalStage.setTitle("FitApp");
        additionalStage.setScene(scene);
        additionalStage.show();
    }

    @FXML
    private void handleSearchButtonAction() throws IOException, SQLException {
        String searchedProduct = searchProductField.getText();

        if(!searchedProduct.isEmpty())
        {
            ArrayList<String> items = dbHandler.handleSearchProduct(searchedProduct);
            // Convert ArrayList to ObservableList
            ObservableList<String> observableList = FXCollections.observableArrayList(items);

            // Set items to ListView
            productList.setItems(observableList);
        }
    }

    @FXML
    private void handleAddProductButtonAction() throws IOException, SQLException {

        String productName = productNameField.getText();
        String protein = proteinField.getText();
        String fat = fatField.getText();
        String carbs = carbsField.getText();
        String kcal = kcalField.getText();

        String result = "";

        if(productList.getSelectionModel().getSelectedItem() != null)
        {
            String product = productList.getSelectionModel().getSelectedItem().toString();
            String[] productData = product.split(" ");
            for (String productDatum : productData) {
                System.out.println(productDatum);
            }
            result = dbHandler.handleAddProduct(ScreenHandler.currentUser, productData[0], productData[1], productData[2], productData[3], productData[4]);
        }
        else if(productName.isEmpty() || protein.isEmpty() || fat.isEmpty() || carbs.isEmpty() || kcal.isEmpty())
        {
            productNameField.clear();
            proteinField.clear();
            fatField.clear();
            carbsField.clear();
            kcalField.clear();
            AddMealError.setVisible(true);
        }
        else {
            System.out.println(ScreenHandler.currentUser);
            result = dbHandler.handleAddProduct(ScreenHandler.currentUser, productName, protein, fat, carbs, kcal);
        }
        if(result.equals("Error"))
        {
            AddMealError.setText("Failed to add product");
            AddMealError.setVisible(true);
        }
        else
        {
            /*
             * TODO: here we need to update the table with products
             */

            System.out.println(mainScreen);
            if(mainScreen != null)
                mainScreen.update();
            else
                System.out.println("MainScreen is null");

            Stage stage = (Stage) this.productNameField.getScene().getWindow();
            stage.close();

        }

    }
}
