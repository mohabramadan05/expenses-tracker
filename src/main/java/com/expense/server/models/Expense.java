package com.expense.server.models;
import java.util.Date;

public class Expense {
    private int EXPENSE_ID;
    private int USER_ID;
    private int CATEGORY_ID;
    private double AMOUNT;
    private String NOTE;
    private Date  DATE_CREATED;

    public Expense() {}

    public Expense (int EXPENSE_ID, int USER_ID, int CATEGORY_ID, double AMOUNT, String NOTE, Date  DATE_CREATED) {
        this.EXPENSE_ID = EXPENSE_ID;
        this.USER_ID = USER_ID;
        this.CATEGORY_ID = CATEGORY_ID;
        this.AMOUNT = AMOUNT;
        this.NOTE = NOTE;
        this.DATE_CREATED = DATE_CREATED;
    }
    public int getId() { return EXPENSE_ID; }
    public void setId(int EXPENSE_ID) { this.EXPENSE_ID = EXPENSE_ID; }
    public int getUserId() { return USER_ID; }
    public void setUserId(int USER_ID) { this.USER_ID = USER_ID; }
    public int getCategoryId() { return CATEGORY_ID; }
    public void setCategoryId(int CATEGORY_ID) { this.CATEGORY_ID = CATEGORY_ID; }
    public double getAmount() { return AMOUNT; }
    public void setAmount(double AMOUNT) { this.AMOUNT = AMOUNT; }
    public String getNote() { return NOTE; }
    public void setNote(String NOTE) { this.NOTE = NOTE; }
    public Date  getDateCreated() { return DATE_CREATED; }
    public void setDateCreated(Date  DATE_CREATED) { this.DATE_CREATED = DATE_CREATED; }

}