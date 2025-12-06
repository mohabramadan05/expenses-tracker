package com.expense.server.controllers;

import com.expense.common.dto.BudgetReportDto;
import com.expense.server.dao.ReportDAO;
import com.expense.server.models.BudgetReport;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class ReportController {
    private final ReportDAO reportDao = new ReportDAO();
    private final Gson gson = new Gson();

    private BudgetReportDto toDTO(BudgetReport r) {
        return new BudgetReportDto(
                r.getUserId(),
                r.getCategoryId(),
                r.getCategoryName(),
                r.getSpent(),
                r.getBudget(),
                r.getRemaining(),
                r.getStatus()
        );
    }

    public String getMonthlyBudgetReport(Request req, Response res) {
        res.type("application/json");

        int userId = gson.fromJson(req.body(), BudgetReportDto.class).getUserId();

        List<BudgetReport> reports = reportDao.getBudgetReport(userId);
        List<BudgetReportDto> dtos = new ArrayList<>();

        for (BudgetReport r : reports) {
            dtos.add(toDTO(r));
        }

        return gson.toJson(dtos);
    }
}
