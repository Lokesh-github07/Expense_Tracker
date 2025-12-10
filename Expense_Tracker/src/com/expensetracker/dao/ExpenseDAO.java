package com.expensetracker.dao;

import com.expensetracker.model.Expense;
import com.expensetracker.model.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
    
    public void addExpense(Expense expense) {
        String sql = "INSERT INTO expenses(amount, description, date, category_id) VALUES(?,?,?,?)";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, expense.getAmount());
            pstmt.setString(2, expense.getDescription());
            pstmt.setString(3, expense.getDate().toString());
            pstmt.setInt(4, expense.getCategoryId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = """
            SELECT e.*, c.name as category_name 
            FROM expenses e 
            JOIN categories c ON e.category_id = c.id 
            ORDER BY e.date DESC
            """;
        
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Expense expense = new Expense();
                expense.setId(rs.getInt("id"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setDescription(rs.getString("description"));
                expense.setDate(LocalDate.parse(rs.getString("date")));
                expense.setCategoryId(rs.getInt("category_id"));
                expense.setCategoryName(rs.getString("category_name"));
                expenses.add(expense);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expenses;
    }
    
    public List<Expense> getMonthlyExpenses(int year, int month) {
        List<Expense> expenses = new ArrayList<>();
        String sql = """
            SELECT e.*, c.name as category_name 
            FROM expenses e 
            JOIN categories c ON e.category_id = c.id 
            WHERE strftime('%Y', date) = ? AND strftime('%m', date) = ?
            ORDER BY e.date
            """;
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, String.format("%04d", year));
            pstmt.setString(2, String.format("%02d", month));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Expense expense = new Expense();
                expense.setId(rs.getInt("id"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setDescription(rs.getString("description"));
                expense.setDate(LocalDate.parse(rs.getString("date")));
                expense.setCategoryId(rs.getInt("category_id"));
                expense.setCategoryName(rs.getString("category_name"));
                expenses.add(expense);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expenses;
    }
    
    public double getMonthlyTotal(int year, int month) {
        String sql = """
            SELECT SUM(amount) as total 
            FROM expenses 
            WHERE strftime('%Y', date) = ? AND strftime('%m', date) = ?
            """;
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, String.format("%04d", year));
            pstmt.setString(2, String.format("%02d", month));
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0.0;
    }
}