package com.expensetracker.model;

import java.time.LocalDate;

public class Expense {

    private int id;
    private double amount;
    private String description;
    private LocalDate date;
    private int categoryId;
    private String categoryName;

    // Default Constructor
    public Expense() {}

    // Existing Constructor
    public Expense(double amount, String description, LocalDate date, int categoryId) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.categoryId = categoryId;
    }

    // 🔥 Added constructor (fixes categoryName column)
    public Expense(int id, double amount, String description,
                   LocalDate date, int categoryId, String categoryName) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
