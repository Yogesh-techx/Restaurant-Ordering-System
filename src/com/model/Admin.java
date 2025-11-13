package com.model;

public class Admin {
	private int adminId;
    private String username;
    private String password;

    //Empty Constructor for flexibility
    public Admin() {}

    //Parameterized Constructors for ease to create objects quickly
    public Admin(int adminId, String username, String password) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
    }
    
    public Admin(String username, String password) {
    	this.username = username;
    	this.password = password;
    }
    
    //getters and setters
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
