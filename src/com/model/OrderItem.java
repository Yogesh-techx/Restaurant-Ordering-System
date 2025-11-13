package com.model;

public class OrderItem {
    private int itemId;
    private int orderId;
    private int foodId;
    private String foodName;
    private int quantity;
    private double subTotal;

    //Empty Constructor for flexibility
    public OrderItem() {}

    //Parameterized Constructors for ease to create objects quickly
    public OrderItem(int itemId, int orderId, int foodId, int quantity, double subTotal) {
        this.itemId = itemId;
        this.orderId = orderId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    
    public OrderItem(int itemId, int orderId, int foodId, String foodName, int quantity, double subTotal) {
        this.itemId = itemId;
        this.orderId = orderId;
        this.foodId = foodId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    // Getters and setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public String toString() {
        return "--> " + foodName + " (x" + quantity + ") - ₹" + subTotal;
    }
}
