package com.expense.server.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.expense.server.db.Database;
import com.expense.server.models.BudgetReport;

public class ReportDAO {
    public List<BudgetReport> getBudgetReport(int userId) {
        List<BudgetReport> list = new ArrayList<>();

        String sql = """
            SELECT
                  b.user_id,
                  b.category_id,
                  c.name AS category_name,
                  NVL(SUM(e.amount), 0) AS spent,
                  b.amount AS budget,
                  b.amount - NVL(SUM(e.amount), 0) AS remaining,
                  CASE
                      WHEN b.amount = 0 THEN 'Safe'
                      WHEN (NVL(SUM(e.amount), 0) / b.amount) * 100 < 70 THEN 'Safe'
                      WHEN (NVL(SUM(e.amount), 0) / b.amount) * 100 BETWEEN 70 AND 100 THEN 'Warning'
                      WHEN (NVL(SUM(e.amount), 0) / b.amount) * 100 > 100 THEN 'Danger'
                  END AS status
            FROM budgets b
            JOIN categories c
                ON b.category_id = c.category_id
            LEFT JOIN expenses e
                ON e.user_id = b.user_id
                AND e.category_id = b.category_id
                AND e.DATE_CREATED >= TRUNC(SYSDATE, 'MM')
                AND e.DATE_CREATED < ADD_MONTHS(TRUNC(SYSDATE, 'MM'), 1)
            WHERE b.user_id = ?
            GROUP BY
                  b.user_id, b.category_id, c.name, b.amount
            """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BudgetReport r = new BudgetReport();
                r.setUserId(rs.getInt("user_id"));
                r.setCategoryId(rs.getInt("category_id"));
                r.setCategoryName(rs.getString("category_name"));
                r.setSpent(rs.getDouble("spent"));
                r.setBudget(rs.getDouble("budget"));
                r.setRemaining(rs.getDouble("remaining"));
                r.setStatus(rs.getString("status"));
                list.add(r);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
}
