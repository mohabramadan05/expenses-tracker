package com.expense.server.controllers;

import com.expense.common.dto.BudgetReport2Dto;
import com.expense.server.dao.ReportDAO2;
import com.expense.server.models.BudgetReport2;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class ReportController2 {
    private final ReportDAO2 reportDao = new ReportDAO2();
    private final Gson gson = new Gson();

    private BudgetReport2Dto toDTO(BudgetReport2 r) {
        return new BudgetReport2Dto(
                r.getUserId(),
                r.getSpent_this_month(),
                r.getRemaining_budget()
        );
    }

    public String getMonthlyBudgetReport(Request req, Response res) {
        res.type("application/json");

        int userId = gson.fromJson(req.body(), BudgetReport2Dto.class).getUserId();

        List<BudgetReport2> reports = reportDao.getBudgetReport(userId);
        List<BudgetReport2Dto> dtos = new ArrayList<>();

        for (BudgetReport2 r : reports) {
            dtos.add(toDTO(r));
        }

        return gson.toJson(dtos);
    }
}
