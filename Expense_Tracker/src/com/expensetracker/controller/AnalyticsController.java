package com.expensetracker.controller;

import com.expensetracker.dao.ExpenseDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// For JFreeChart 1.0.19 - Simplified approach without SwingNode
public class AnalyticsController {
    
    @FXML private VBox chartContainer;
    
    private ExpenseDAO expenseDAO = new ExpenseDAO();
    
    public void loadCharts() {
        try {
            // Clear previous charts
            chartContainer.getChildren().clear();
            
            // Get current month expenses
            LocalDate now = LocalDate.now();
            List<com.expensetracker.model.Expense> expenses = 
                expenseDAO.getMonthlyExpenses(now.getYear(), now.getMonthValue());
            
            if (expenses.isEmpty()) {
                addNoDataLabel();
                return;
            }
            
            // Create charts using a simpler approach
            createChartsSimple(expenses);
            
        } catch (Exception e) {
            e.printStackTrace();
            showErrorLabel("Failed to load charts: " + e.getMessage());
        }
    }
    
    private void createChartsSimple(List<com.expensetracker.model.Expense> expenses) {
        try {
            // First, create the charts and save them as images
            String pieChartFile = createPieChartImage(expenses);
            String barChartFile = createBarChartImage(expenses);
            
            // Then display the saved images
            if (pieChartFile != null) {
                displayChartImage(pieChartFile, "Expense Categories");
            }
            
            if (barChartFile != null) {
                displayChartImage(barChartFile, "Daily Expenses");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            showErrorLabel("Failed to create charts: " + e.getMessage());
        }
    }
    
    private String createPieChartImage(List<com.expensetracker.model.Expense> expenses) {
        try {
            // Calculate category totals
            Map<String, Double> categoryTotals = new HashMap<>();
            for (com.expensetracker.model.Expense expense : expenses) {
                String category = expense.getCategoryName();
                categoryTotals.put(category, 
                    categoryTotals.getOrDefault(category, 0.0) + expense.getAmount());
            }
            
            // Create a simple text representation of pie chart
            StringBuilder pieChartText = new StringBuilder();
            pieChartText.append("Expense Categories - ").append(LocalDate.now().getMonth()).append("\n");
            pieChartText.append("============================\n");
            
            double total = 0;
            for (Double value : categoryTotals.values()) {
                total += value;
            }
            
            for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                double percentage = (entry.getValue() / total) * 100;
                pieChartText.append(String.format("%s: $%.2f (%.1f%%)\n", 
                    entry.getKey(), entry.getValue(), percentage));
            }
            
            pieChartText.append(String.format("\nTotal: $%.2f", total));
            
            // Save as text file (alternative to image)
            String fileName = "pie_chart.txt";
            saveTextToFile(pieChartText.toString(), fileName);
            
            // Also create a simple bar chart representation
            createSimpleBarChart(expenses);
            
            return fileName;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private String createBarChartImage(List<com.expensetracker.model.Expense> expenses) {
        try {
            // Calculate daily totals
            Map<Integer, Double> dailyTotals = new HashMap<>();
            for (com.expensetracker.model.Expense expense : expenses) {
                int day = expense.getDate().getDayOfMonth();
                dailyTotals.put(day, 
                    dailyTotals.getOrDefault(day, 0.0) + expense.getAmount());
            }
            
            // Create text representation of bar chart
            StringBuilder barChartText = new StringBuilder();
            barChartText.append("Daily Expenses - ").append(LocalDate.now().getMonth()).append("\n");
            barChartText.append("============================\n");
            
            // Find max for scaling
            double max = 0;
            for (Double value : dailyTotals.values()) {
                if (value > max) max = value;
            }
            
            for (int day = 1; day <= 31; day++) {
                if (dailyTotals.containsKey(day)) {
                    double amount = dailyTotals.get(day);
                    int barLength = (int) ((amount / max) * 50); // Scale to 50 characters
                    barChartText.append(String.format("Day %2d: %s $%.2f\n", 
                        day, "█".repeat(barLength), amount));
                }
            }
            
            // Save as text file
            String fileName = "bar_chart.txt";
            saveTextToFile(barChartText.toString(), fileName);
            
            return fileName;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void createSimpleBarChart(List<com.expensetracker.model.Expense> expenses) {
        // Create a simple ASCII bar chart for console output
        Map<String, Double> categoryTotals = new HashMap<>();
        for (com.expensetracker.model.Expense expense : expenses) {
            String category = expense.getCategoryName();
            categoryTotals.put(category, 
                categoryTotals.getOrDefault(category, 0.0) + expense.getAmount());
        }
        
        System.out.println("\n=== SIMPLE BAR CHART ===");
        System.out.println("Expense Categories - " + LocalDate.now().getMonth());
        System.out.println("============================\n");
        
        // Find max for scaling
        double max = 0;
        for (Double value : categoryTotals.values()) {
            if (value > max) max = value;
        }
        
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            int barLength = (int) ((entry.getValue() / max) * 30); // Scale to 30 characters
            System.out.printf("%-15s: %s $%.2f\n", 
                entry.getKey(), 
                "█".repeat(barLength),
                entry.getValue());
        }
        System.out.println();
    }
    
    private void saveTextToFile(String text, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(text.getBytes());
            System.out.println("Chart saved as text: " + fileName);
        } catch (IOException e) {
            System.err.println("Failed to save chart: " + e.getMessage());
        }
    }
    
    private void displayChartImage(String fileName, String title) {
        try {
            Label titleLabel = new Label(title);
            titleLabel.setStyle("-fx-font-size: 14pt; -fx-font-weight: bold;");
            chartContainer.getChildren().add(titleLabel);
            
            // For text files, display as text area
            if (fileName.endsWith(".txt")) {
                // Read and display the text content
                java.nio.file.Path path = java.nio.file.Paths.get(fileName);
                String content = new String(java.nio.file.Files.readAllBytes(path));
                
                Label contentLabel = new Label(content);
                contentLabel.setStyle("-fx-font-family: 'Monospace'; -fx-font-size: 10pt;");
                contentLabel.setWrapText(true);
                
                chartContainer.getChildren().add(contentLabel);
            } 
            // For image files, display as image
            else if (fileName.endsWith(".png") || fileName.endsWith(".jpg")) {
                File file = new File(fileName);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(600);
                    imageView.setFitHeight(400);
                    imageView.setPreserveRatio(true);
                    chartContainer.getChildren().add(imageView);
                }
            }
            
            // Add separator
            javafx.scene.control.Separator separator = new javafx.scene.control.Separator();
            separator.setPrefWidth(600);
            chartContainer.getChildren().add(separator);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void addNoDataLabel() {
        Label noDataLabel = new Label("No expense data available for this month.");
        noDataLabel.setStyle("-fx-font-size: 14pt; -fx-text-fill: gray;");
        chartContainer.getChildren().add(noDataLabel);
    }
    
    private void showErrorLabel(String message) {
        Label errorLabel = new Label("Error: " + message);
        errorLabel.setStyle("-fx-font-size: 12pt; -fx-text-fill: red;");
        chartContainer.getChildren().add(errorLabel);
    }
}