package com.expensetracker;

import com.expensetracker.model.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize database
        DatabaseConnection.initializeDatabase();
        
        // Load main view
        Parent root = FXMLLoader.load(getClass().getResource("/com/expensetracker/view/MainView.fxml"));
        primaryStage.setTitle("Expense Tracker with Monthly Analytics");
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}