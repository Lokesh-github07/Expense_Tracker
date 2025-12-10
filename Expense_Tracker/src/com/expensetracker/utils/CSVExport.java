package com.expensetracker.utils;

import com.expensetracker.model.Expense;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExport {
    
    public static void exportToCSV(List<Expense> expenses, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Write header
            writer.write("Date,Amount,Category,Description\n");
            
            // Write data
            for (Expense expense : expenses) {
                writer.write(String.format("%s,%.2f,%s,%s\n",
                    expense.getDate(),
                    expense.getAmount(),
                    expense.getCategoryName(),
                    expense.getDescription()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}