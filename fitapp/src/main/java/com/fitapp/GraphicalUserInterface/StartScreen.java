package com.fitapp.GraphicalUserInterface;

import com.fitapp.DataBase.DbHandler;
import com.sun.javafx.charts.Legend;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class StartScreen extends Application {

    public static String currentUser;
    private FXMLLoader mainScreenLoader;
    public static int goal;
    public static int kcal;
    @FXML
    public ListView productList;
    @FXML
    public Label KcalLabel;
    @FXML
    public Label ProteinLabel;
    @FXML
    public Label FatLabel;
    @FXML
    public Label CarbsLabel;
    @FXML
    public Label GoalLabel;
    @FXML
    public ProgressBar goalProgressBar;
    @FXML
    public Label totalGoalLabel;
    @FXML
    public Label goalProgress;

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
                this.currentUser = username;
                System.out.println(currentUser+" logged in");

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
    private void handleSignUpButtonAction() throws IOException, SQLException {
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

    @FXML
    private void handleAddMealButtonAction() throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("/AddMealScreen.fxml"));
        Scene scene = new Scene(root);
        Stage additionalStage = new Stage();
        additionalStage.setResizable(false);
        additionalStage.setTitle("FitApp");
        additionalStage.setScene(scene);
        additionalStage.show();
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
            result = dbHandler.handleAddProduct(currentUser, productData[0], productData[1], productData[2], productData[3], productData[4]);
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
            System.out.println(this.currentUser);
            result = dbHandler.handleAddProduct(currentUser, productName, protein, fat, carbs, kcal);
        }
        if(result.equals("Error"))
            {
                AddMealError.setText("Failed to add product");
                AddMealError.setVisible(true);
            }
            else
            {
                /**
                 * TODO: here we need to update the table with products
                 */
                Stage stage = (Stage) this.productNameField.getScene().getWindow();
                stage.close();

            }

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
    private void updateMainScreen(int kcal, int protein, int fat, int carbs)
    {
        KcalLabel.setText(String.valueOf(kcal));
        ProteinLabel.setText(String.valueOf(protein));
        FatLabel.setText(String.valueOf(fat));
        CarbsLabel.setText(String.valueOf(carbs));
        totalGoalLabel.setText(goal +"kcal");

    }

    @FXML
    public void updateMainScreen() throws SQLException {

        dbHandler.updateValues();

        goalProgressBar.setProgress((double)kcal/goal);

        goalProgress.setText(String.valueOf((kcal)));
        KcalLabel.setText(String.valueOf(kcal));
        totalGoalLabel.setText(goal +"kcal");

        System.out.println(KcalLabel.getText());
        System.out.println(totalGoalLabel.getText());
        System.out.println(goalProgress.getText());
    }

}





