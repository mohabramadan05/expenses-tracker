package com.expense.common.dto;

public class BudgetReport2Dto {
    private double spent_this_month;
    private double remaining_budget;
    private int userId;
    public BudgetReport2Dto(){}
    public BudgetReport2Dto(int userId, double spent_this_month, double remaining_budget) {
        this.spent_this_month = spent_this_month;
        this.remaining_budget = remaining_budget;
        this.userId = userId;
    }
    public double getSpent_this_month() {
        return spent_this_month;
    }
    public void setSpent_this_month(double spent_this_month) {
        this.spent_this_month = spent_this_month;
    }
    public double getRemaining_budget() {
        return remaining_budget;
    }
    public void setRemaining_budget(double remaining_budget) {
        this.remaining_budget = remaining_budget;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
