package com.desirestodesigns.complexion;

public class Category {
    String category,categoryName;

    public Category() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(String category, String categoryName) {
        this.category = category;
        this.categoryName = categoryName;
    }
}
