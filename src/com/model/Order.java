package com.model;

import java.time.LocalDateTime;

public class Order {
	private int orderID;
	private String customerName;
	private LocalDateTime orderDate;
	private double totalPrice;
	
	//Empty Constructor for flexibility
	public Order() {}

	//Parameterized Constructors for ease to create objects quickly
	public Order(int orderID, String customerName, LocalDateTime orderDate, double totalPrice) {
		this.orderID = orderID;
		this.customerName = customerName;
		this.orderDate = orderDate;
		this.totalPrice = totalPrice;
	}
	
	public Order(String customerName, LocalDateTime orderDate, double totalPrice) {
		this.customerName = customerName;
		this.orderDate = orderDate;
		this.totalPrice = totalPrice;
	}

	//Getters and Setters
	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Order #" + orderID + " | Customer:" + customerName + " | Date:" + orderDate
				+ " | Total:" + totalPrice;
	}
	
}
