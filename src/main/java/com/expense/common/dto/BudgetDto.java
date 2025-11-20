package com.expense.common.dto;

public class BudgetDto {
    private int BUDGET_ID;
    private int USER_ID;
    private int CATEGORY_ID;
    private double AMOUNT;

    public BudgetDto(){}
    public BudgetDto(int BUDGET_ID, int USER_ID, int CATEGORY_ID, double AMOUNT) {
        this.BUDGET_ID = BUDGET_ID;
        this.USER_ID = USER_ID;
        this.CATEGORY_ID  = CATEGORY_ID;
        this.AMOUNT = AMOUNT;
    }
    public int getBUDGET_ID() {
        return BUDGET_ID;
    }
    public void setBUDGET_ID(int BUDGET_ID) {
        this.BUDGET_ID = BUDGET_ID;
    }
    public int getUSER_ID() {
        return USER_ID;
    }
    public void setUSER_ID(int USER_ID) {
        this.USER_ID = USER_ID;
    }
    public int getCATEGORY_ID() {
        return CATEGORY_ID;
    }
    public void setCATEGORY_ID(int CATEGORY_ID) {
        this.CATEGORY_ID = CATEGORY_ID;
    }
    public double getAMOUNT() {
        return AMOUNT;
    }
    public void setAMOUNT(double AMOUNT) {
        this.AMOUNT = AMOUNT;
    }
}
