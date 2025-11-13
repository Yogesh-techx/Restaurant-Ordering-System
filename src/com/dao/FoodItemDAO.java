package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.FoodItem;
import com.util.DBConnection;

public class FoodItemDAO {

    // 1. Fetch all available food items
    public List<FoodItem> getAllFoodItems() {
        List<FoodItem> foodList = new ArrayList<>();
        String sql = "SELECT food_id, food_name, price, available FROM food_items WHERE available = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                FoodItem item = new FoodItem(
                    rs.getInt("food_id"),
                    rs.getString("food_name"),
                    rs.getDouble("price"),
                    rs.getBoolean("available")
                );
                foodList.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foodList;
    }

    // 2. Get price of a food item
    public double getFoodPrice(int foodId) {
        String sql = "SELECT price FROM food_items WHERE food_id = ? AND available = 1";
        double price = -1.0;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, foodId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) price = rs.getDouble("price");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return price;
    }

    // 3. Check food availability
    public boolean isFoodAvailable(int foodId) {
        String sql = "SELECT available FROM food_items WHERE food_id = ?";
        boolean available = false;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, foodId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) available = rs.getBoolean("available");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return available;
    }

    // 4. Add new food item
    public boolean addFoodItem(String name, double price, boolean available) {
        String query = "INSERT INTO food_items (food_name, price, available) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setBoolean(3, available);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. Update food item
    public boolean updateFoodItem(int foodId, double price, boolean available) {
        String query = "UPDATE food_items SET price = ?, available = ? WHERE food_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, price);
            pstmt.setBoolean(2, available);
            pstmt.setInt(3, foodId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 6. Get Food Name by ID
    public String getFoodItemName(int foodId) {
        String name = "Unknown";
        String sql = "SELECT food_name FROM food_items WHERE food_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, foodId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("food_name");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching food name: " + e.getMessage());
        }
        return name;
    }

}
