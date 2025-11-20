package com.expense.server.dao;

import com.expense.server.db.Database;
import com.expense.server.models.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "select CATEGORY_ID, NAME, DESCRIPTION from CATEGORIES order by CATEGORY_ID";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("CATEGORY_ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION")
                );
                categories.add(category);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching categories: " + e.getMessage());
            e.printStackTrace();
        }

        return categories;
    }
}
