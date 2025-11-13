package com.model;

public class FoodItem {
	private int foodId;
	private String foodName;
	private double price;
	private boolean available;
	
	
	//Empty Constructor for flexibility
	public FoodItem() {}
	
	//Parameterized Constructors for ease to create objects quickly
	public FoodItem(int foodId, String foodName, double price, boolean available) {
		this.foodId = foodId;
		this.foodName = foodName;
		this.price = price;
		this.available = available;
	}

	public FoodItem(String foodName,  double price, boolean available) {
		this.foodName = foodName;
		this.price = price;
		this.available = available;
	}
	
	

	//getters and setters
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return foodId + ". " + foodName + " - ₹" + price + (available? " Available": " Not Available");
	}
	
}
