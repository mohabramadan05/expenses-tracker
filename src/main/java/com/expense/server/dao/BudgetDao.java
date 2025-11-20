package com.expense.server.dao;

import com.expense.server.db.Database;
import com.expense.server.models.Budget;
import com.expense.server.models.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetDao {
    public List<Budget> getAllBudgets(int userId) {
        List<Budget> budgets = new ArrayList<>();
        String sql = "select BUDGET_ID, USER_ID, CATEGORY_ID, AMOUNT from BUDGETS WHERE USER_ID = ? order by BUDGET_ID DESC";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Budget budget = new Budget(
                            rs.getInt("BUDGET_ID"),
                            rs.getInt("USER_ID"),
                            rs.getInt("CATEGORY_ID"),
                            rs.getDouble("AMOUNT")
                    );
                    budgets.add(budget);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching budgets: " + e.getMessage());
            e.printStackTrace();
        }
        return budgets;
    }

    public Budget getBudgetById(int id) {
        String sql = "SELECT BUDGET_ID, USER_ID, CATEGORY_ID, AMOUNT from BUDGETS WHERE BUDGET_ID = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Budget(
                        rs.getInt("BUDGET_ID"),
                        rs.getInt("USER_ID"),
                        rs.getInt("CATEGORY_ID"),
                        rs.getDouble("AMOUNT")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error fetching budget: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean addBudget(Budget budget) {

        String sql = "INSERT INTO BUDGETS (USER_ID, CATEGORY_ID, AMOUNT) VALUES (?, ?, ?)";


        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, budget.getUSER_ID());
            pstmt.setInt(2, budget.getCATEGORY_ID());
            pstmt.setDouble(3, budget.getAMOUNT());

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.err.println("Error adding budget: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBudget(int id, Budget budget) {
        String sql = "UPDATE BUDGETS SET CATEGORY_ID = ?, AMOUNT = ? " +
                "WHERE BUDGET_ID = ?";



        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, budget.getCATEGORY_ID());
            pstmt.setDouble(2, budget.getAMOUNT());
            pstmt.setInt(3, id);

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.err.println("Error updating budget: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBudget(int id) {
        String sql = "DELETE FROM BUDGETS WHERE BUDGET_ID = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting budget: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
