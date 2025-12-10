module Expense_Tracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.swing;   // IMPORTANT

    requires java.desktop;
    requires java.sql;
    
    opens com.expensetracker.controller to javafx.fxml;
  

    opens com.expensetracker.view to javafx.fxml;
    exports com.expensetracker;
}
