package com.expense.server.dao;

import com.expense.server.db.Database;
import com.expense.server.models.BudgetReport2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO2 {

    public List<BudgetReport2> getBudgetReport(int userId) {
        List<BudgetReport2> list = new ArrayList<>();

        String sql = """
            SELECT
                b.user_id,
                NVL(SUM(e.amount), 0) AS spent_this_month,
                NVL(SUM(b.amount), 0) - NVL(SUM(e.amount), 0) AS remaining_budget
                            FROM budgets b
            LEFT JOIN
                expenses e
                ON e.
                user_id = b.user_id
                            AND e.category_id = b.category_id
                            AND e.date_created >= TRUNC(SYSDATE, 'MM')
                AND e.
                date_created < ADD_MONTHS(TRUNC(SYSDATE, 'MM'), 1)
            WHERE b.user_id = ?
            GROUP BY b.user_id
            """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BudgetReport2 r = new BudgetReport2();
                r.setUserId(rs.getInt("user_id"));
                r.setRemaining_budget(rs.getDouble("remaining_budget"));
                r.setSpent_this_month(rs.getDouble("spent_this_month"));
                list.add(r);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
}
