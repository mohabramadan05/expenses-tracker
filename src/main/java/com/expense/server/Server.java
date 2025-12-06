package com.expense.server;

import com.expense.server.controllers.*;
import com.expense.server.db.Database;
import static spark.Spark.*;

public class Server {
    public static void runServer() {
        if (!Database.testConnection()) {
            System.err.println("Failed to connect to database. Exiting...");
            return;
        }
        port(8080);

        ExpenseController expensecontroller = new ExpenseController();
        UserController userController = new UserController();
        CategoryController categoryController = new CategoryController();
        BudgetController budgetcontroller = new BudgetController();
        ReportController reportController = new ReportController();
        ReportController2 reportController2 = new ReportController2();

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
            response.type("application/json");
        });

        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        path("/api", () -> {
            //expenses
            get("/expenses/all/:id", expensecontroller::getAllExpenses);
            get("/expenses/:id", expensecontroller::getExpenseById);
            post("/expenses", expensecontroller::addExpense);
            put("/expenses/:id", expensecontroller::updateExpense);
            delete("/expenses/:id", expensecontroller::deleteExpense);
            //users
            post("/users/signup", userController::signup);
            post("/users/login", userController::login);
            //categories
            get("/categories", categoryController::getAllCategories);
            //budget
            get("/budgets/all/:id", budgetcontroller::getAllBudgets);
            get("/budgets/:id", budgetcontroller::getBudgetById);
            post("/budgets", budgetcontroller::addBudget);
            put("/budgets/:id", budgetcontroller::updateBudget);
            delete("/budgets/:id", budgetcontroller::deleteBudget);
            //report
            post("/reports", reportController::getMonthlyBudgetReport);
            post("/reports2", reportController2::getMonthlyBudgetReport);
        });

        exception(Exception.class, (exception, request, response) -> {
            response.status(500);
            response.body("{\"error\": \"Internal server error: " + exception.getMessage() + "\"}");
        });

        System.out.println("\n===================================");
        System.out.println("  Expense Tracker Server Started!");
        System.out.println("===================================");
        System.out.println("Server URL: http://localhost:8080");
        System.out.println("\nAPI Endpoints:");

        System.out.println("===================================");
        System.out.println("Expenses:");
        System.out.println("  GET    /api/expenses/all/:id  - Get all expenses");
        System.out.println("  GET    /api/expenses/:id      - Get expense by ID");
        System.out.println("  POST   /api/expenses          - Add new expense");
        System.out.println("  PUT    /api/expenses/:id      - Update expense");
        System.out.println("  DELETE /api/expenses/:id      - Delete expense");
        System.out.println("===================================\n");

        System.out.println("===================================");
        System.out.println("Users:");
        System.out.println("  POST    /api/users/signup     - Add User");
        System.out.println("  POST    /api/users/login      - Check User");
        System.out.println("===================================\n");

        System.out.println("===================================");
        System.out.println("Categories:");
        System.out.println("  GET    /api/categories     - Get all categories");
        System.out.println("===================================\n");

        System.out.println("===================================");
        System.out.println("Budgets:");
        System.out.println("  GET    /api/budgets/all/:id    - Get all budgets");
        System.out.println("  GET    /api/budgets/:id - Get budget by ID");
        System.out.println("  POST   /api/budgets     - Add new budget");
        System.out.println("  PUT    /api/budgets/:id - Update budget");
        System.out.println("  DELETE /api/budgets/:id - Delete budget");
        System.out.println("===================================\n");

        System.out.println("===================================");
        System.out.println("Reports:");
        System.out.println("  post    /api/reports     - Get user reports");
        System.out.println("  post    /api/reports2    - Get user reports 2");
        System.out.println("===================================\n");
    }
}
