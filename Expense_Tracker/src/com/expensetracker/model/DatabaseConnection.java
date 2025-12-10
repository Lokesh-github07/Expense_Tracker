package com.expensetracker.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:database/expensetracker.db";
    
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public static void initializeDatabase() {
        String createCategoriesTable = """
            CREATE TABLE IF NOT EXISTS categories (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL UNIQUE,
                budget_limit REAL DEFAULT 0.0
            );
            """;
            
        String createExpensesTable = """
            CREATE TABLE IF NOT EXISTS expenses (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                amount REAL NOT NULL,
                description TEXT,
                date TEXT NOT NULL,
                category_id INTEGER NOT NULL,
                FOREIGN KEY (category_id) REFERENCES categories (id)
            );
            """;
            
        String insertDefaultCategories = """
            INSERT OR IGNORE INTO categories (name) VALUES 
            ('Food'), ('Transport'), ('Entertainment'), 
            ('Utilities'), ('Shopping'), ('Healthcare');
            """;
            
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createCategoriesTable);
            stmt.execute(createExpensesTable);
            stmt.execute(insertDefaultCategories);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}