package com.expense.server.dao;

import com.expense.server.db.Database;
import com.expense.server.models.User;
import java.sql.*;

public class UserDao {

    public boolean signup(User user) {
        String sql = "INSERT INTO users (USERNAME, PASSWORD_HASH, FULL_NAME) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash());
            pstmt.setString(3, user.getFullName());

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.err.println("Error signing up user: " + e.getMessage());
            return false;
        }
    }

    public User login(String username, String passwordHash) {
        String sql = "SELECT USER_ID, USERNAME, FULL_NAME FROM users WHERE USERNAME = ? AND PASSWORD_HASH = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, passwordHash);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("USER_ID"),
                        rs.getString("USERNAME"),
                        passwordHash,
                        rs.getString("FULL_NAME")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error logging in: " + e.getMessage());
        }

        return null;
    }
}
