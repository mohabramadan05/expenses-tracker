package com.expense.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryDto {

    @JsonProperty("CATEGORY_ID")
    private int CATEGORY_ID;

    @JsonProperty("NAME")
    private String NAME;

    @JsonProperty("DESCRIPTION")
    private String DESCRIPTION;

    public CategoryDto() {}

    public CategoryDto(int CATEGORY_ID, String NAME, String DESCRIPTION) {
        this.CATEGORY_ID = CATEGORY_ID;
        this.NAME = NAME;
        this.DESCRIPTION = DESCRIPTION;
    }

    public int getCATEGORY_ID() { return CATEGORY_ID; }
    public void setCATEGORY_ID(int CATEGORY_ID) { this.CATEGORY_ID = CATEGORY_ID; }

    public String getNAME() { return NAME; }
    public void setNAME(String NAME) { this.NAME = NAME; }

    public String getDESCRIPTION() { return DESCRIPTION; }
    public void setDESCRIPTION(String DESCRIPTION) { this.DESCRIPTION = DESCRIPTION; }

    @Override
    public String toString() {
        return this.NAME;
    }
}
