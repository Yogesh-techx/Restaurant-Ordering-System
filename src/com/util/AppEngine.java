package com.util;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.model.FoodItem;
import com.model.Order;
import com.model.OrderItem;
import com.service.AdminService;
import com.service.FoodService;
import com.service.OrderService;

public class AppEngine {

    public static void startApplication() {
        Scanner input = new Scanner(System.in);

        FoodService foodService = new FoodService();
        OrderService orderService = new OrderService();
        AdminService adminService = new AdminService();

        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== WELCOME TO RESTAURANT SYSTEM ===");
            System.out.println("Login as:");
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int roleChoice = input.nextInt();
            input.nextLine();

            switch (roleChoice) {
                case 1 -> customerMenu(input, foodService, orderService);
                case 2 -> {
                    if (isAdminAuthenticated(input, adminService)) {
                        adminMenu(input, foodService, orderService);
                    }
                }
                case 3 -> {
                    System.out.println("Thank you! Visit again.");
                    exit = true;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        input.close();
    }

    // -------------------- AUTHENTICATION --------------------
    public static boolean isAdminAuthenticated(Scanner input, AdminService adminService) {
        System.out.println("\n=== ADMIN LOGIN ===");
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();

        boolean valid = adminService.login(username, password);
        if (valid)
            System.out.println("Login successful. Welcome, Admin!");
        else
            System.out.println("Invalid credentials. Access denied.");

        return valid;
    }

    // -------------------- CUSTOMER MENU --------------------
    public static void customerMenu(Scanner input, FoodService foodService, OrderService orderService) {
        boolean logout = false;

        while (!logout) {
            System.out.println("\n=== CUSTOMER PANEL ===");
            System.out.println("1. View Menu");
            System.out.println("2. Place New Order");
            System.out.println("3. View My Orders");
            System.out.println("4. Print My Order Receipt");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> viewMenu(foodService);
                case 2 -> placeOrder(input, foodService, orderService);
                case 3 -> viewPastOrders(input, orderService);
                case 4 -> printReceipt(input, orderService);
                case 5 -> {
                    System.out.println("Logging out...");
                    logout = true;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // -------------------- ADMIN MENU --------------------
    public static void adminMenu(Scanner input, FoodService foodService, OrderService orderService) {
        boolean logout = false;

        while (!logout) {
            System.out.println("\n=== ADMIN PANEL ===");
            System.out.println("1. View Menu");
            System.out.println("2. Add Food Item");
            System.out.println("3. Update Food Item");
            System.out.println("4. View All Orders");
            System.out.println("5. View Customer Order History");
            System.out.println("6. Cancel an Order");
            System.out.println("7. Print Order Receipt");
            System.out.println("8. Logout");
            System.out.print("Choose an option: ");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> viewMenu(foodService);
                case 2 -> addFoodItem(input, foodService);
                case 3 -> updateFoodItem(input, foodService);
                case 4 -> viewAllOrders(orderService);
                case 5 -> viewPastOrders(input, orderService);
                case 6 -> cancelOrder(input, orderService);
                case 7 -> printReceipt(input, orderService);
                case 8 -> {
                    System.out.println("Logging out...");
                    logout = true;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // -------------------- SHARED OPERATIONS --------------------
    public static void viewMenu(FoodService foodService) {
        List<FoodItem> menu = foodService.getAvailableItems();
        if (menu.isEmpty()) {
            System.out.println("\nNo food items available right now.");
            return;
        }

        System.out.println("\n------ MENU ------");
        for (FoodItem item : menu) {
            System.out.println(item);
        }
        System.out.println("------------------");
    }

    // -------------------- PLACE ORDER --------------------
    public static void placeOrder(Scanner input, FoodService foodService, OrderService orderService) {
        System.out.print("\nEnter your name: ");
        String customerName = input.nextLine();

        int orderId = orderService.createOrder(customerName);
        if (orderId == -1) {
            System.out.println("Failed to create order. Try again later.");
            return;
        }

        double grandTotal = 0.0;
        boolean done = false;

        System.out.println("\nStart building your order...");

        while (!done) {
            System.out.println("\nCART OPTIONS:");
            System.out.println("1. Add More Items");
            System.out.println("2. Edit Quantity");
            System.out.println("3. Remove Item");
            System.out.println("4. View Cart");
            System.out.println("5. Confirm & Place Order");
            System.out.print("Choose an option: ");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> addItemToCart(input, foodService, orderService, orderId);
                case 2 -> editCartItem(input, orderService, foodService, orderId);
                case 3 -> removeCartItem(input, orderService, orderId);
                case 4 -> viewCart(orderService, orderId);
                case 5 -> {
                    grandTotal = orderService.calculateOrderTotal(orderId);
                    boolean updated = orderService.updateOrderTotal(orderId, grandTotal);
                    if (updated) {
                        System.out.println("\nOrder placed successfully!");
                        viewCart(orderService, orderId);
                        System.out.printf("Final Total: ₹%.2f%n", grandTotal);
                    } else {
                        System.out.println("Order saved, but failed to update total.");
                    }
                    done = true;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // -------------------- CART FUNCTIONS --------------------
    private static void addItemToCart(Scanner input, FoodService foodService, OrderService orderService, int orderId) {
        viewMenu(foodService);
        System.out.print("Enter Food ID (0 to cancel): ");
        int foodId = input.nextInt();
        if (foodId == 0) return;

        if (!foodService.isAvailable(foodId)) {
            System.out.println("Invalid Food ID or unavailable. Try again.");
            return;
        }

        System.out.print("Enter quantity: ");
        int qty = input.nextInt();
        if (qty <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        double price = foodService.getFoodPrice(foodId);
        boolean added = orderService.addOrderItem(orderId, foodId, qty, price);

        if (added) {
            String foodName = foodService.getFoodItemName(foodId);
            System.out.printf("Added: %s × %d = ₹%.2f%n", foodName, qty, qty * price);
            viewCart(orderService, orderId);
        } else {
            System.out.println("Failed to add item.");
        }
    }

    private static void editCartItem(Scanner input, OrderService orderService, FoodService foodService, int orderId) {
        List<OrderItem> cart = orderService.getOrderItems(orderId);
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("\nYour Cart:");
        int i = 1;
        for (OrderItem item : cart) {
            System.out.printf("%d. %s × %d = ₹%.2f%n", i++, item.getFoodName(), item.getQuantity(), item.getSubTotal());
        }

        System.out.print("Enter item number to edit (0 to cancel): ");
        int choice = input.nextInt();
        if (choice == 0) return;
        if (choice < 1 || choice > cart.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        OrderItem selectedItem = cart.get(choice - 1);
        System.out.print("Enter new quantity: ");
        int newQty = input.nextInt();

        if (newQty <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        double newSubTotal = foodService.getFoodPrice(selectedItem.getFoodId()) * newQty;
        boolean updated = orderService.updateOrderItemQuantity(orderId, selectedItem.getFoodId(), newQty, newSubTotal);

        if (updated)
            System.out.printf("Updated: %s × %d = ₹%.2f%n", selectedItem.getFoodName(), newQty, newSubTotal);
        else
            System.out.println("Failed to update item.");

        viewCart(orderService, orderId);
    }

    private static void removeCartItem(Scanner input, OrderService orderService, int orderId) {
        List<OrderItem> cart = orderService.getOrderItems(orderId);
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("\nYour Cart:");
        int i = 1;
        for (OrderItem item : cart) {
            System.out.printf("%d. %s × %d = ₹%.2f%n", i++, item.getFoodName(), item.getQuantity(), item.getSubTotal());
        }

        System.out.print("Enter item number to remove (0 to cancel): ");
        int choice = input.nextInt();
        if (choice == 0) return;
        if (choice < 1 || choice > cart.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        OrderItem selectedItem = cart.get(choice - 1);
        boolean removed = orderService.removeOrderItem(orderId, selectedItem.getFoodId());

        if (removed)
            System.out.printf("Removed: %s × %d (₹%.2f)%n", selectedItem.getFoodName(), selectedItem.getQuantity(), selectedItem.getSubTotal());
        else
            System.out.println("Failed to remove item.");

        viewCart(orderService, orderId);
    }

    public static void viewCart(OrderService orderService, int orderId) {
        List<OrderItem> items = orderService.getOrderItems(orderId);
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("\nCurrent Cart:");
        double total = 0.0;
        for (OrderItem item : items) {
            System.out.printf("- %s × %d = ₹%.2f%n", item.getFoodName(), item.getQuantity(), item.getSubTotal());
            total += item.getSubTotal();
        }
        System.out.println("-------------------------------");
        System.out.printf("Total = ₹%.2f%n", total);
    }

    // -------------------- VIEW PAST ORDERS --------------------
    public static void viewPastOrders(Scanner input, OrderService orderService) {
        System.out.print("\nEnter customer name: ");
        String name = input.nextLine();

        List<Order> orders = orderService.getOrdersByCustomer(name);
        if (orders.isEmpty()) {
            System.out.println("No orders found for " + name);
        } else {
            System.out.println("\n=== Order History for " + name + " ===");
            for (Order o : orders) {
                System.out.println("\nOrder #" + o.getOrderID() + " | Date: " + o.getOrderDate() + " | Total: ₹" + o.getTotalPrice());
                List<OrderItem> items = orderService.getOrderItems(o.getOrderID());
                if (items.isEmpty()) {
                    System.out.println("  (No items found for this order)");
                } else {
                    System.out.println("  Items:");
                    int totalQty = 0;
                    for (OrderItem item : items) {
                        System.out.println("    " + item);
                        totalQty += item.getQuantity();
                    }
                    System.out.println("  -----------------------------");
                    System.out.println("  Total Items: " + totalQty + " | Total Price: ₹" + o.getTotalPrice());
                }
            }
        }
    }

    // -------------------- ADMIN FUNCTIONS --------------------
    public static void cancelOrder(Scanner input, OrderService orderService) {
        System.out.print("\nEnter Order ID to cancel: ");
        int id = input.nextInt();

        boolean success = orderService.cancelOrder(id);
        if (success)
            System.out.println("Order ID " + id + " cancelled successfully.");
        else
            System.out.println("Order ID not found or could not be cancelled.");
    }

    public static void addFoodItem(Scanner input, FoodService foodService) {
        System.out.print("Enter food name: ");
        String name = input.nextLine();
        System.out.print("Enter price: ");
        double price = input.nextDouble();
        System.out.print("Is it available (1 for yes, 0 for no): ");
        boolean available = input.nextInt() == 1;

        boolean success = foodService.addFoodItem(name, price, available);
        if (success)
            System.out.println("Food item added successfully!");
        else
            System.out.println("Failed to add food item.");
    }

    public static void updateFoodItem(Scanner input, FoodService foodService) {
        System.out.print("Enter Food ID to update: ");
        int id = input.nextInt();
        System.out.print("Enter new price: ");
        double price = input.nextDouble();
        System.out.print("Is it available (1 for yes, 0 for no): ");
        boolean available = input.nextInt() == 1;

        boolean success = foodService.updateFoodItem(id, price, available);
        if (success)
            System.out.println("Food item updated successfully!");
        else
            System.out.println("Food item not found or update failed.");
    }

    public static void viewAllOrders(OrderService orderService) {
        List<Order> allOrders = orderService.getAllOrders();
        if (allOrders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        System.out.println("\n=== ALL ORDERS ===");
        for (Order o : allOrders) {
            System.out.println("\nOrder #" + o.getOrderID() + " | Customer: " + o.getCustomerName() +
                    " | Date: " + o.getOrderDate() +
                    " | Total: ₹" + o.getTotalPrice());

            List<OrderItem> items = orderService.getOrderItems(o.getOrderID());
            if (items.isEmpty()) {
                System.out.println("  (No items found for this order)");
            } else {
                System.out.println("  Items:");
                int totalQty = 0;
                for (OrderItem item : items) {
                    System.out.println("    " + item);
                    totalQty += item.getQuantity();
                }
                System.out.println("  -----------------------------");
                System.out.println("  Total Items: " + totalQty + " | Total Price: ₹" + o.getTotalPrice());
            }
        }
    }

    // -------------------- RECEIPT PRINTING --------------------
    public static void printReceipt(Scanner input, OrderService orderService) {
        System.out.print("\nEnter Order ID to print receipt: ");
        int orderId = input.nextInt();
        input.nextLine();

        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            System.out.println("No order found with ID: " + orderId);
            return;
        }

        List<OrderItem> items = orderService.getOrderItems(orderId);
        if (items.isEmpty()) {
            System.out.println("No items found for this order.");
            return;
        }

        StringBuilder receipt = new StringBuilder();
        receipt.append("\n======================================\n");
        receipt.append("          FOOD ORDER RECEIPT \n");
        receipt.append("======================================\n");
        receipt.append("Order ID: ").append(order.getOrderID()).append("\n");
        receipt.append("Customer: ").append(order.getCustomerName()).append("\n");
        receipt.append("Date: ").append(order.getOrderDate()).append("\n");
        receipt.append("--------------------------------------\n");
        receipt.append(String.format("%-20s %-8s %-10s%n", "Item", "Qty", "Subtotal"));
        receipt.append("--------------------------------------\n");

        int totalQty = 0;
        for (OrderItem item : items) {
            receipt.append(String.format("%-20s %-8d ₹%-10.2f%n",
                    item.getFoodName(), item.getQuantity(), item.getSubTotal()));
            totalQty += item.getQuantity();
        }

        receipt.append("--------------------------------------\n");
        receipt.append(String.format("Total Items: %-5d Total: ₹%.2f%n", totalQty, order.getTotalPrice()));
        receipt.append("======================================\n");
        receipt.append("     Thank you for ordering with us!\n");
        receipt.append("======================================\n");

        System.out.println(receipt.toString());

        System.out.print("\nWould you like to save this receipt as a file? (y/n): ");
        String choice = input.nextLine();

        if (choice.equalsIgnoreCase("y")) {
            saveReceiptToFile(orderId, receipt.toString());
        }
    }

    // -------------------- SAVE RECEIPT TO FILE --------------------
    private static void saveReceiptToFile(int orderId, String content) {
        try {
            String workspacePath = System.getProperty("user.dir");
            File receiptsDir = new File(workspacePath + File.separator + "receipts");
            if (!receiptsDir.exists()) receiptsDir.mkdir();

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
            String timestamp = LocalDateTime.now().format(fmt);
            String fileName = "receipt_" + orderId + "_" + timestamp + ".txt";

            File file = new File(receiptsDir, fileName);

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }

            System.out.println("Receipt saved successfully!");
            System.out.println("Location: " + file.getAbsolutePath());

        } catch (Exception e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }
    }
}
