<h1>🍽️ Restaurant Ordering System — Java Console Application</h1>

<p>
A fully interactive <strong>restaurant ordering system</strong> built using 
<strong>Java</strong>, <strong>MySQL</strong>, and a <strong>layered architecture</strong> 
(DAO → Service → Controller).  
This project simulates real-world backend development with multi-role access, cart system,
receipt generation, order management, and admin controls.
</p>

<hr/>

<h2>🚀 Features</h2>

<h3>👤 Customer Features</h3>
<ul>
  <li>View food menu</li>
  <li>Place new orders</li>
  <li>Add items to cart</li>
  <li>Edit quantity in cart</li>
  <li>Remove items from cart</li>
  <li>View order summaries</li>
  <li>Generate & save receipts</li>
</ul>

<h3>🛠️ Admin Features</h3>
<ul>
  <li>Admin login (database-based)</li>
  <li>Add / update food items</li>
  <li>Enable / disable menu items</li>
  <li>View all orders</li>
  <li>View any customer's order history</li>
  <li>Cancel orders</li>
  <li>Print order receipts</li>
</ul>

<hr/>

<h2>🧱 Project Architecture</h2>

<pre>
Main.java
 ↓
AppEngine (Controller logic)
 ↓
Service Layer (Business logic)
 ↓
DAO Layer (Database operations)
 ↓
MySQL Database
</pre>

<hr/>

<h2>📂 Directory Structure</h2>

<pre>
Restaurant/
│
├── src/
│   ├── com.App/
│   │     └── Main.java
│   │
│   ├── com.util/
│   │     ├── AppEngine.java
│   │     ├── DBConnection.java
│   │     └── EnvLoader.java
│   │
│   ├── com.dao/
│   │     ├── AdminDAO.java
│   │     ├── FoodItemDAO.java
│   │     ├── OrderDAO.java
│   │     └── OrderItemDAO.java
│   │
│   ├── com.service/
│   │     ├── AdminService.java
│   │     ├── FoodService.java
│   │     └── OrderService.java
│   │
│   ├── com.model/
│         ├── Admin.java
│         ├── FoodItem.java
│         ├── Order.java
│         └── OrderItem.java
│
├── .env.example
├── .gitignore
├── receipts/             (auto-generated)
└── README.md
</pre>

<hr/>

<h2>⚙️ Dependencies</h2>

<table>
<tr><th>Dependency</th><th>Purpose</th></tr>
<tr><td>MySQL JDBC Driver</td><td>Database connectivity</td></tr>
<tr><td>Java 17+</td><td>Runtime</td></tr>
<tr><td>MySQL 8+</td><td>Database server</td></tr>
</table>

<hr/>

<h2>🛢️ Database Schema</h2>

<p>Run this SQL in MySQL:</p>

<pre>
CREATE DATABASE restaurant;
USE restaurant;

CREATE TABLE admin_users (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE food_items (
    food_id INT AUTO_INCREMENT PRIMARY KEY,
    food_name VARCHAR(100),
    price DOUBLE,
    available BOOLEAN DEFAULT 1
);

CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_price DOUBLE
);

CREATE TABLE order_items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    food_id INT,
    quantity INT,
    sub_total DOUBLE,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (food_id) REFERENCES food_items(food_id)
);
</pre>

<p>Add an admin user:</p>

<pre>
INSERT INTO admin_users (username, password)
VALUES ('Write your admin username here', 'write your admin password here');
</pre>

<hr/>

<h2>▶️ Running the Application</h2>

<h3>✔️ From Eclipse</h3>
<pre>Right-click Main.java → Run As → Java Application</pre>

<hr/>

<h2>📄 Receipt Example</h2>

<pre>
======================================
          FOOD ORDER RECEIPT
======================================
Order ID: 
Customer: 
Date: 
--------------------------------------
Item                 Qty     Subtotal
--------------------------------------
Veg Burger           x       ₹xx
French Fries         y       ₹yy
Cold Drink           z       ₹zz
--------------------------------------
Total Items: x+y+z       Total: ₹(xx+yy+zz)
======================================
  Thank you for ordering with us!
======================================
</pre>

<hr/>

<h2>🧪 Future Enhancements</h2>

<ul>
  <li>Move UI to JavaFX</li>
  <li>Convert to Spring Boot REST API</li>
  <li>JWT authentication</li>
  <li>PDF receipts</li>
  <li>Docker support</li>
</ul>

<hr/>
