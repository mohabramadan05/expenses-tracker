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
                e.user_id,
                e.category_id,
                SUM(e.amount) AS spent,
                b.amount AS budget,
                b.amount - SUM(e.amount) AS remaining,
                CASE 
                    WHEN (SUM(e.amount) / b.amount) * 100 < 70 THEN 'Safe'
                    WHEN (SUM(e.amount) / b.amount) * 100 BETWEEN 70 AND 100 THEN 'Warning'
                    WHEN (SUM(e.amount) / b.amount) * 100 > 100 THEN 'Danger'
                END AS status
            FROM expenses e
            JOIN budgets b 
                ON b.user_id = e.user_id
                AND b.category_id = e.category_id
            WHERE e.user_id = ?
            AND e.DATE_CREATED >= TRUNC(SYSDATE, 'MM')
            AND e.DATE_CREATED < ADD_MONTHS(TRUNC(SYSDATE, 'MM'), 1)
            GROUP BY 
                e.user_id, e.category_id, b.amount
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BudgetReport r = new BudgetReport();
                r.setUserId(rs.getInt("user_id"));
                r.setCategoryId(rs.getInt("category_id"));
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
