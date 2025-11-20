package com.expense.server.models;

public class Category {
    private int CATEGORY_ID;
    private String NAME;
    private String DESCRIPTION;

    public Category() {}

    public Category(int CATEGORY_ID, String NAME, String DESCRIPTION) {
        this.CATEGORY_ID = CATEGORY_ID;
        this.NAME = NAME;
        this.DESCRIPTION = DESCRIPTION;
    }

    public int getCategoryID() {
        return CATEGORY_ID;
    }
    public void setCategoryID(int categoryID) {
        this.CATEGORY_ID = categoryID;
    }
    public String getNAME() {
        return NAME;
    }
    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
    public String getDESCRIPTION() {
        return DESCRIPTION;
    }
    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }


}
