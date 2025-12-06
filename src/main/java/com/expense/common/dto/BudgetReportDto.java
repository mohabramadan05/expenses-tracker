package com.expense.common.dto;

public class BudgetReportDto {
    private int userId;
    private int categoryId;
    private String categoryName;
    private double spent;
    private double budget;
    private double remaining;
    private String status;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BudgetReportDto() {}
    public BudgetReportDto(int userId, int categoryId,String categoryName, double spent, double budget, double remaining, String status) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.spent = spent;
        this.budget = budget;
        this.remaining = remaining;
        this.status = status;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public double getSpent() {
        return spent;
    }
    public void setSpent(double spent) {
        this.spent = spent;
    }
    public double getBudget() {
        return budget;
    }
    public void setBudget(double budget) {
        this.budget = budget;
    }
    public double getRemaining() {
        return remaining;
    }
    public void setRemaining(double remaining) {
        this.remaining = remaining;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
