package com.expense.server.models;

public class BudgetReport2 {
    private int userId;
    private double spent_this_month;
    private double remaining_budget;

    public BudgetReport2(){}
    public BudgetReport2(int userId,double spent_this_month, double remaining_budget) {
        this.userId = userId;
        this.spent_this_month = spent_this_month;
        this.remaining_budget = remaining_budget;
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
