package com.desirestodesigns.complexion;

public class IncomeExpense {
    String transcationType,categoryType,description;
    String amount;

    public String getTranscationType() {
        return transcationType;
    }

    public void setTranscationType(String transcationType) {
        this.transcationType = transcationType;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public IncomeExpense(String transcationType, String categoryType, String description, String amount) {
        this.transcationType = transcationType;
        this.categoryType = categoryType;
        this.description = description;
        this.amount = amount;
    }

    public IncomeExpense() {
    }

    public void setDate(String currentDate) {
    }
}
