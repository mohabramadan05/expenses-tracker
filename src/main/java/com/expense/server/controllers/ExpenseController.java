package com.expense.server.controllers;

import com.expense.common.dto.ExpenseDto;
import com.expense.server.dao.ExpenseDao;
import com.expense.server.models.Expense;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExpenseController {
    private final ExpenseDao expenseDAO  = new ExpenseDao();
    private final Gson gson  = new Gson();
    private final SimpleDateFormat dateFormat =  new SimpleDateFormat("dd/MM/yyyy");


    private ExpenseDto toDTO(Expense expense) {
        return new ExpenseDto(
                expense.getId(),
                expense.getUserId(),
                expense.getCategoryId(),
                expense.getAmount(),
                expense.getNote(),
                expense.getDateCreated()
        );
    }

    private Expense fromDTO(ExpenseDto dto) throws ParseException {
        Expense expense = new Expense();
        expense.setId(dto.getId());
        expense.setUserId(dto.getUserId());
        expense.setCategoryId(dto.getCategoryId());
        expense.setAmount(dto.getAmount());
        expense.setNote(dto.getNote());
        expense.setDateCreated(dto.getDateCreated());
        return expense;
    }


    public String getAllExpenses(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params(":id"));
            List<Expense> expenses = expenseDAO.getAllExpenses(id);
            List<ExpenseDto> dtos = new ArrayList<>();

            for (Expense expense : expenses) {
                dtos.add(toDTO(expense));
            }

            return gson.toJson(dtos);
        }
        catch (Exception e) {
            res.status(400);
            return "{\"error\": \"Invalid ID format\"}";
        }
    }

    // GET expense by ID
    public String getExpenseById(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params(":id"));
            Expense expense = expenseDAO.getExpenseById(id);

            if (expense != null) {
                return gson.toJson(toDTO(expense));
            } else {
                res.status(404);
                return "{\"error\": \"Expense not found\"}";
            }
        } catch (NumberFormatException e) {
            res.status(400);
            return "{\"error\": \"Invalid ID format\"}";
        }
    }

    // POST new expense
    public String addExpense(Request req, Response res) {
        try {
            ExpenseDto dto = gson.fromJson(req.body(), ExpenseDto.class);
            Expense expense = fromDTO(dto);
            boolean success = expenseDAO.addExpense(expense);

            if (success) {
                res.status(201);
                return "{\"message\": \"Expense added successfully\"}";
            } else {
                res.status(500);
                return "{\"error\": \"Failed to add expense\"}";
            }
        } catch (Exception e) {
            res.status(400);
            return "{\"error\": \"Invalid request: " + e.getMessage() + "\"}";
        }
    }

    // PUT update expense
    public String updateExpense(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params(":id"));
            ExpenseDto dto = gson.fromJson(req.body(), ExpenseDto.class);
            Expense expense = fromDTO(dto);
            boolean success = expenseDAO.updateExpense(id, expense);

            if (success) {
                return "{\"message\": \"Expense updated successfully\"}";
            } else {
                res.status(404);
                return "{\"error\": \"Expense not found or update failed\"}";
            }
        } catch (Exception e) {
            res.status(400);
            return "{\"error\": \"Invalid request: " + e.getMessage() + "\"}";
        }
    }

    // DELETE expense
    public String deleteExpense(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params(":id"));
            boolean success = expenseDAO.deleteExpense(id);

            if (success) {
                return "{\"message\": \"Expense deleted successfully\"}";
            } else {
                res.status(404);
                return "{\"error\": \"Expense not found or delete failed\"}";
            }
        } catch (NumberFormatException e) {
            res.status(400);
            return "{\"error\": \"Invalid ID format\"}";
        }
    }






}
