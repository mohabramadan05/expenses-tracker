package com.expense.server.controllers;

import com.expense.common.dto.BudgetDto;
import com.expense.server.dao.BudgetDao;
import com.expense.server.models.Budget;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BudgetController {
    private final BudgetDao budgetDAO  = new BudgetDao();
    private final Gson gson  = new Gson();

    private BudgetDto toDTO(Budget budget) {
        return new BudgetDto(
                budget.getBUDGET_ID(),
                budget.getUSER_ID(),
                budget.getCATEGORY_ID(),
                budget.getAMOUNT()
        );
    }

    private Budget fromDTO(BudgetDto dto) throws ParseException {
        Budget budget = new Budget();
        budget.setBUDGET_ID(dto.getBUDGET_ID());
        budget.setUSER_ID(dto.getUSER_ID());
        budget.setCATEGORY_ID(dto.getCATEGORY_ID());
        budget.setAMOUNT(dto.getAMOUNT());
        return budget;
    }

    public String getAllBudgets(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params(":id"));
            List<Budget> budgets = budgetDAO.getAllBudgets(id);
            List<BudgetDto> dtos = new ArrayList<>();

            for (Budget budget : budgets) {
                dtos.add(toDTO(budget));
            }

            return gson.toJson(dtos);
        }
        catch (Exception e) {
            res.status(400);
            return "{\"error\": \"Invalid ID format\"}";
        }
    }

    public String getBudgetById(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params(":id"));
            Budget budget = budgetDAO.getBudgetById(id);

            if (budget != null) {
                return gson.toJson(toDTO(budget));
            } else {
                res.status(404);
                return "{\"error\": \"budget not found\"}";
            }
        } catch (NumberFormatException e) {
            res.status(400);
            return "{\"error\": \"Invalid ID format\"}";
        }
    }

    public String addBudget(Request req, Response res) {
        try {
            BudgetDto dto = gson.fromJson(req.body(), BudgetDto.class);
            Budget budget = fromDTO(dto);
            boolean success = budgetDAO.addBudget(budget);

            if (success) {
                res.status(201);
                return "{\"message\": \"Budget added successfully\"}";
            } else {
                res.status(500);
                return "{\"error\": \"Failed to add budget\"}";
            }
        } catch (Exception e) {
            res.status(400);
            return "{\"error\": \"Invalid request: " + e.getMessage() + "\"}";
        }
    }

    public String updateBudget(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params(":id"));
            BudgetDto dto = gson.fromJson(req.body(), BudgetDto.class);
            Budget budget = fromDTO(dto);
            boolean success = budgetDAO.updateBudget(id, budget);

            if (success) {
                return "{\"message\": \"Budget updated successfully\"}";
            } else {
                res.status(404);
                return "{\"error\": \"Budget not found or update failed\"}";
            }
        } catch (Exception e) {
            res.status(400);
            return "{\"error\": \"Invalid request: " + e.getMessage() + "\"}";
        }
    }

    public String deleteBudget(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params(":id"));
            boolean success = budgetDAO.deleteBudget(id);

            if (success) {
                return "{\"message\": \"Budget deleted successfully\"}";
            } else {
                res.status(404);
                return "{\"error\": \"Budget not found or delete failed\"}";
            }
        } catch (NumberFormatException e) {
            res.status(400);
            return "{\"error\": \"Invalid ID format\"}";
        }
    }

}
