package com.expensetracker.controller;

import com.expensetracker.dao.CategoryDAO;
import com.expensetracker.dao.ExpenseDAO;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class ExpenseFormController {
    
    @FXML private TextField amountField;
    @FXML private TextField descriptionField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<Category> categoryCombo;
    
    private MainController mainController;
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        System.out.println("MainController set successfully");
    }
    
    @FXML
    public void initialize() {
        System.out.println("ExpenseFormController initialized");
        
        // Set default date to today
        datePicker.setValue(LocalDate.now());
        
        // Load categories
        loadCategories();
    }
    
    private void loadCategories() {
        try {
            CategoryDAO categoryDAO = new CategoryDAO();
            List<Category> categories = categoryDAO.getAllCategories();
            
            if (categories.isEmpty()) {
                System.out.println("No categories found in database");
                // Add default categories
                ObservableList<Category> defaultCategories = FXCollections.observableArrayList(
                    new Category("Food"),
                    new Category("Transport"),
                    new Category("Entertainment"),
                    new Category("Utilities"),
                    new Category("Shopping")
                );
                categoryCombo.setItems(defaultCategories);
            } else {
                ObservableList<Category> categoryList = FXCollections.observableArrayList(categories);
                categoryCombo.setItems(categoryList);
                System.out.println("Loaded " + categories.size() + " categories");
            }
            
            // Set default selection
            if (categoryCombo.getItems().size() > 0) {
                categoryCombo.getSelectionModel().select(0);
            }
            
        } catch (Exception e) {
            System.err.println("Error loading categories: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void saveExpense() {
        System.out.println("Save button clicked");
        
        try {
            // Validate inputs
            if (amountField.getText().isEmpty()) {
                showAlert("Error", "Please enter an amount");
                return;
            }
            
            double amount;
            try {
                amount = Double.parseDouble(amountField.getText());
                if (amount <= 0) {
                    showAlert("Error", "Amount must be greater than 0");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Please enter a valid number for amount");
                return;
            }
            
            if (categoryCombo.getValue() == null) {
                showAlert("Error", "Please select a category");
                return;
            }
            
            if (datePicker.getValue() == null) {
                showAlert("Error", "Please select a date");
                return;
            }
            
            // Create expense object
            Expense expense = new Expense();
            expense.setAmount(amount);
            expense.setDescription(descriptionField.getText());
            expense.setDate(datePicker.getValue());
            expense.setCategoryId(categoryCombo.getValue().getId());
            
            // Save to database
            ExpenseDAO expenseDAO = new ExpenseDAO();
            expenseDAO.addExpense(expense);
            
            System.out.println("Expense saved successfully: $" + amount + " for " + datePicker.getValue());
            
            showAlert("Success", "Expense added successfully!");
            
            // Close window
            closeWindow();
            
            // Refresh main window
            if (mainController != null) {
                mainController.refreshAfterSave();
            } else {
                System.err.println("MainController is null!");
            }
            
        } catch (Exception e) {
            System.err.println("Error saving expense: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error", "Failed to save expense: " + e.getMessage());
        }
    }
    
    @FXML
    private void cancel() {
        System.out.println("Cancel button clicked");
        closeWindow();
    }
    
    private void closeWindow() {
        Stage stage = (Stage) amountField.getScene().getWindow();
        stage.close();
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}