package com.expense.server.dao;


import com.expense.server.db.Database;
import com.expense.server.models.Expense;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ExpenseDao {
    public List<Expense> getAllExpenses(int userId) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT EXPENSE_ID, USER_ID, CATEGORY_ID, AMOUNT, NOTE, DATE_CREATED FROM expenses WHERE USER_ID = ? ORDER BY DATE_CREATED DESC";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Expense expense = new Expense(
                            rs.getInt("EXPENSE_ID"),
                            rs.getInt("USER_ID"),
                            rs.getInt("CATEGORY_ID"),
                            rs.getDouble("AMOUNT"),
                            rs.getString("NOTE"),
                            rs.getDate("DATE_CREATED")
                    );
                    expenses.add(expense);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching expenses: " + e.getMessage());
            e.printStackTrace();
        }
        return expenses;
    }

    public Expense getExpenseById(int id) {
        String sql = "SELECT EXPENSE_ID, USER_ID, CATEGORY_ID, AMOUNT, NOTE, DATE_CREATED " +
                "FROM expenses WHERE EXPENSE_ID = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Expense(
                        rs.getInt("EXPENSE_ID"),
                        rs.getInt("USER_ID"),
                        rs.getInt("CATEGORY_ID"),
                        rs.getDouble("AMOUNT"),
                        rs.getString("NOTE"),
                        rs.getDate("DATE_CREATED")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error fetching expense: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean addExpense(Expense expense) {

        String sql = "INSERT INTO expenses (USER_ID, CATEGORY_ID, AMOUNT, NOTE, DATE_CREATED) VALUES (?, ?, ?, ?, ?)";


        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, expense.getUserId());
            pstmt.setInt(2, expense.getCategoryId());
            pstmt.setDouble(3, expense.getAmount());
            pstmt.setString(4, expense.getNote());
            pstmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.err.println("Error adding expense: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateExpense(int id, Expense expense) {
        String sql = "UPDATE expenses SET NOTE = ?, AMOUNT = ?, CATEGORY_ID = ?, " +
                "DATE_CREATED = ? WHERE EXPENSE_ID = ?";



        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, expense.getNote());
            pstmt.setDouble(2, expense.getAmount());
            pstmt.setInt(3, expense.getCategoryId());
            pstmt.setDate(4, new java.sql.Date(expense.getDateCreated().getTime()));
            pstmt.setInt(5, id);

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.err.println("Error updating expense: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteExpense(int id) {
        String sql = "DELETE FROM expenses WHERE EXPENSE_ID = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting expense: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
