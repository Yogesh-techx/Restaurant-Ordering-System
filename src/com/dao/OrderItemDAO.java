package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.OrderItem;
import com.util.DBConnection;

public class OrderItemDAO {

    // 1. Add an item to an order
    public boolean addItem(int orderId, int foodId, int quantity, double subTotal) {
        String sql = "INSERT INTO order_items (order_id, food_id, quantity, sub_total) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setInt(2, foodId);
            ps.setInt(3, quantity);
            ps.setDouble(4, subTotal);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Calculate total of all items in an order
    public double calculateTotal(int orderId) {
        String sql = "SELECT SUM(sub_total) AS total FROM order_items WHERE order_id = ?";
        double total = 0.0;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) total = rs.getDouble("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    // 3. Get all items for a specific order (with food names)
    public List<OrderItem> getItemsByOrder(int orderId) {
        List<OrderItem> itemList = new ArrayList<>();

        String sql = """
            SELECT oi.item_id, oi.order_id, oi.food_id, f.food_name, oi.quantity, oi.sub_total
            FROM order_items oi
            JOIN food_items f ON oi.food_id = f.food_id
            WHERE oi.order_id = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem(
                        rs.getInt("item_id"),
                        rs.getInt("order_id"),
                        rs.getInt("food_id"),
                        rs.getString("food_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("sub_total")
                    );
                    itemList.add(item);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemList;
    }
    
    // 4. Update quantity of an order item
    public boolean updateOrderItemQuantity(int orderId, int foodId, int newQty, double subTotal) {
        String sql = "UPDATE order_items SET quantity = ?, sub_total = ? WHERE order_id = ? AND food_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newQty);
            ps.setDouble(2, subTotal);
            ps.setInt(3, orderId);
            ps.setInt(4, foodId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating quantity: " + e.getMessage());
            return false;
        }
    }

    // 5. Remove item from order
    public boolean removeOrderItem(int orderId, int foodId) {
        String sql = "DELETE FROM order_items WHERE order_id = ? AND food_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, foodId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error removing item: " + e.getMessage());
            return false;
        }
    }

    
}
