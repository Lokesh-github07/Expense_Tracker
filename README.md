💰 Expense Tracker with Monthly Analytics

A desktop-based personal finance management application built using Java and JavaFX.
This application helps users record, categorize, and analyze their expenses with powerful monthly filtering and data visualization features.

📌 Project Overview

The Expense Tracker with Monthly Analytics is designed to solve the common problem of tracking daily expenses and understanding spending patterns.

The application provides:

Easy expense recording

Category-based organization

Monthly/yearly filtering

Statistical analysis

Data visualization

CSV export functionality

The project follows the MVC (Model-View-Controller) architecture for clean structure and maintainability.

🚀 Features
📝 Expense Management

Add new expenses

Categorize expenses

View all recorded transactions

Organized data display

📊 Monthly Analytics

Filter expenses by month and year

Category-wise expense breakdown

Statistical summaries

📈 Data Visualization

Graphical charts using JFreeChart

Category distribution visualization

Monthly expense comparison

📤 Export Functionality

Export expense records to CSV format

Easy external analysis in Excel or other tools

💾 Database Integration

SQLite database for persistent data storage

JDBC for database connectivity

Structured schema with foreign key constraints

🛠 Tech Stack
Programming Language

Java 11+

Frontend

JavaFX 21

FXML (UI Design)

Database

SQLite 3

JDBC

Libraries

JFreeChart 1.0.19

JCommon 1.0.23

Tools

Eclipse IDE

VS Code

Git

🏗 Architecture

The project follows the MVC Architecture:

Model – Data classes and database entities

DAO – Database access logic

Controller – Application business logic

View – JavaFX UI (FXML files)

This ensures:

Clean separation of concerns

Maintainable code structure

Scalability

📂 Project Structure (Example)
Expense-Tracker/
│
├── src/
│   ├── model/
│   ├── dao/
│   ├── controller/
│   ├── view/
│
├── resources/
│   ├── fxml/
│   ├── css/
│
├── lib/ (if using external JARs)
├── database/
└── README.md

⚙️ How to Run
Requirements

Java 11 or higher

JavaFX installed

SQLite (included as file-based DB)

Steps

Clone the repository:

git clone https://github.com/Lokesh-github07/expense-tracker.git


Open the project in Eclipse or VS Code.

Add required JAR files (if not using Maven):

JFreeChart

JCommon

SQLite JDBC

Configure JavaFX VM options (if required).

Run the main application class.

🎯 Learning Outcomes

This project demonstrates:

Object-Oriented Programming (OOP)

MVC design pattern

JDBC database integration

Desktop UI development using JavaFX

Data visualization integration

File handling (CSV export)

📌 Future Improvements

User authentication system

Budget limit alerts

Dark mode UI

Cloud database integration

PDF report export

👨‍💻 Author

Lokesh Pande
Final Year IT Student
