package com.service;

import java.util.List;

import com.dao.OrderDAO;
import com.dao.OrderItemDAO;
import com.model.Order;
import com.model.OrderItem;

public class OrderService {
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.orderItemDAO = new OrderItemDAO();
    }

    // 1. Create order
    public int createOrder(String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            return -1;
        }
        return orderDAO.createOrder(customerName.trim());
    }

    // 2. Add item to order
    public boolean addOrderItem(int orderId, int foodId, int qty, double pricePerUnit) {
        if (orderId <= 0 || foodId <= 0 || qty <= 0 || pricePerUnit <= 0) return false;
        double subTotal = qty * pricePerUnit;
        return orderItemDAO.addItem(orderId, foodId, qty, subTotal);
    }

    // 3. Update total
    public boolean updateOrderTotal(int orderId) {
        double total = orderItemDAO.calculateTotal(orderId);
        return orderDAO.updateTotal(orderId, total);
    }
    
    // Overloaded: Update total with provided total value
    public boolean updateOrderTotal(int orderId, double total) {
        return orderDAO.updateTotal(orderId, total);
    }

    // 4. Cancel order
    public boolean cancelOrder(int orderId) {
        if (orderId <= 0) return false;
        return orderDAO.cancelOrder(orderId);
    }

    // 5. Get all orders
    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    // 6. Get orders by customer
    public List<Order> getOrdersByCustomer(String name) {
        return orderDAO.getOrdersByCustomer(name);
    }
    
    public List<OrderItem> getOrderItems(int orderId) {
        return orderItemDAO.getItemsByOrder(orderId);
    }
    
    public Order getOrderById(int orderId) {
        return orderDAO.getOrderById(orderId);
    }
    
    public boolean updateOrderItemQuantity(int orderId, int foodId, int newQty, double subTotal) {
        return orderItemDAO.updateOrderItemQuantity(orderId, foodId, newQty, subTotal);
    }

    public boolean removeOrderItem(int orderId, int foodId) {
        return orderItemDAO.removeOrderItem(orderId, foodId);
    }

    public double calculateOrderTotal(int orderId) {
        return orderItemDAO.calculateTotal(orderId);
    }

}
