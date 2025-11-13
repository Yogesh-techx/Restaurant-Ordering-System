package com.service;

import java.util.List;
import com.dao.FoodItemDAO;
import com.model.FoodItem;

public class FoodService {
    private FoodItemDAO foodDAO;

    public FoodService() {
        this.foodDAO = new FoodItemDAO();
    }

    // 1. Get all food items
    public List<FoodItem> getAvailableItems() {
        return foodDAO.getAllFoodItems();
    }

    // 2. Add new food item
    public boolean addFoodItem(String name, double price, boolean available) {
        if (name == null || name.trim().isEmpty() || price <= 0) {
            return false;
        }
        return foodDAO.addFoodItem(name.trim(), price, available);
    }

    // 3. Update existing food item
    public boolean updateFoodItem(int foodId, double price, boolean available) {
        if (foodId <= 0 || price <= 0) return false;
        return foodDAO.updateFoodItem(foodId, price, available);
    }

    // 4. Get food price
    public double getFoodPrice(int foodId) {
        return foodDAO.getFoodPrice(foodId);
    }

    // 5. Check availability
    public boolean isAvailable(int foodId) {
        return foodDAO.isFoodAvailable(foodId);
    }
    
    // 6. Get food name by ID
    public String getFoodItemName(int foodId) {
        return foodDAO.getFoodItemName(foodId);
    }

}
