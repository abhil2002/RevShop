
# 🚀 RevShop – Console-Based E-Commerce Application

**RevShop** is a **Java-based Console E-Commerce Application** built using **Core Java, JDBC, MySQL, and a clean Layered (N-Tier) Architecture**.
It provides **role-based access for Buyers and Sellers** to simulate real-world e-commerce workflows such as product browsing, cart management, order processing, inventory control, reviews, favorites, and notifications.

The application is designed with **scalability, modularity, and maintainability** in mind and can be extended into a **Spring Boot or Microservices-based web application** in future phases.

---

## 📌 Key Features

### 🛍 Buyer

* Secure registration and login
* Browse all available products
* Search products by keyword
* Filter products by category
* Add products to cart with quantity
* Remove products from cart
* View cart with calculated total
* Checkout with shipping and billing details
* Simulated payment methods (UPI / Card / COD)
* View order history
* Review and rate purchased products
* Save products as favorites (wishlist)
* Receive in-app notifications for order placement

---

### 🏪 Seller

* Registration with business details
* Secure login
* Add new products with description, category, and pricing
* Update or delete existing products
* Set MRP and discounted price
* Manage inventory stock
* Configure inventory threshold values
* Receive low-stock alerts via console
* View orders placed for their products
* View customer reviews and ratings
* Receive notifications when orders are placed

---

## 🏗️ Architecture Overview

RevShop follows a **Layered (N-Tier) Architecture**, ensuring **clear separation of concerns and scalability**.

```
Controller Layer → Service Layer → DAO Layer → Database
```

### Layer Responsibilities

* **Controller Layer (`controller`)**
  Handles console-based menus, user input, and role-based navigation.

* **Service Layer (`service`)**
  Contains business logic, validations, and workflows such as cart management, checkout processing, inventory updates, and payment simulation.

* **DAO Layer (`dao`)**
  Responsible for all database operations using JDBC, including CRUD operations and SQL execution.

* **Model Layer (`model`)**
  POJO/entity classes representing database tables.

* **Utility Layer (`util`)**
  Common utilities such as database connection handling and password hashing.

---

## 📂 Project Structure

```
revshop
│
├── database
│   ├── revshop_schema.sql
│   ├── revshop_seed.sql
│   └── revshop_updates.sql
│
├── src/main/java
│   ├── app
│   │   └── RevShopApp.java
│   │
│   ├── controller
│   │   ├── AuthController
│   │   ├── BuyerController
│   │   └── SellerController
│   │
│   ├── service
│   │   ├── AuthService
│   │   ├── ProductService
│   │   ├── CartService
│   │   ├── OrderService
│   │   └── ReviewService
│   │
│   ├── dao
│   │   ├── UserDAO
│   │   ├── SellerDAO
│   │   ├── ProductDAO
│   │   ├── CartDAO
│   │   ├── OrderDAO
│   │   ├── ReviewDAO
│   │   ├── FavoriteDAO
│   │   └── NotificationDAO
│   │
│   ├── model
│   │   ├── User
│   │   ├── Product
│   │   ├── CartItem
│   │   └── Order
│   │
│   └── util
│       ├── DBConnection
│       └── PasswordUtil
│
├── pom.xml
└── README.md
```

---

## 📊 Entity Relationship Diagram (ERD)

The system is centered around **users, products, and orders**, forming the core of the e-commerce workflow.

### Core Relationships

* `users → sellers (1 : 1)`
  Each seller is a registered user with business details.

* `sellers → products (1 : N)`
  A seller can list multiple products.

* `users → carts (1 : 1)`
  Each buyer has one active cart.

* `carts → cart_items (1 : N)`
  A cart can contain multiple products.

* `orders → order_items (1 : N)`
  Each order consists of multiple purchased items.

* `products → reviews (1 : N)`
  Products can receive multiple reviews.

* `users → notifications (1 : N)`
  Users receive system-generated notifications.

📌 This ERD ensures **data normalization, referential integrity, and scalability**.

---

## 🔄 System Workflows

### 1️⃣ Buyer Order Placement Workflow

```
Buyer browses products
        ↓
Adds items to cart
        ↓
Checkout with address & payment method
        ↓
Order created
        ↓
Inventory updated
        ↓
Notifications sent to buyer & seller
```

---

### 2️⃣ Seller Inventory Monitoring Workflow

```
Seller adds products
        ↓
Stock decreases on orders
        ↓
Stock reaches threshold
        ↓
Low-stock alert displayed in console
```

---

## 🗄️ Database Details

### Database Name

```
revshop
```

### Major Tables

* users
* sellers
* products
* carts
* cart_items
* orders
* order_items
* reviews
* favorites
* notifications

---

## ⚙️ Configuration

### Database Configuration (`DBConnection.java`)

```java
jdbc:mysql://localhost:3306/revshop
username: root
password: your_password
```

---

## ▶️ How to Run the Application

1. Import the project into **IntelliJ IDEA / Eclipse**
2. Create the MySQL database and execute SQL scripts
3. Update database credentials in `DBConnection.java`
4. Build the project using Maven:

   ```bash
   mvn clean install
   ```
5. Run:

   ```
   app.RevShopApp
   ```

---

## 🔐 Authentication & Security

* Secure login with hashed passwords (SHA-256)
* Role-based access control (Buyer / Seller)
* Security question-based password recovery
* Change password functionality
* Centralized authentication via `AuthService`

---

## 🧪 Error Handling & Validation

* Input validations at service layer
* Graceful handling of invalid operations
* JDBC exception handling to prevent application crashes
* Clear console messages for user feedback

---

## 🛠️ Tech Stack

* **Language:** Java 21
* **Database:** MySQL
* **Architecture:** Layered (N-Tier)
* **Build Tool:** Maven
* **Connectivity:** JDBC
* **Security:** Password Hashing (SHA-256)
* **Version Control:** Git
* **Testing:** JUnit (optional / extensible)

---

## 🚀 Future Enhancements

* Web UI using Spring Boot & REST APIs
* JWT-based authentication
* Microservices architecture
* Real payment gateway integration
* Admin dashboard & analytics
* Email / SMS notifications

---

## 👨‍💻 Author

**Abhishek Satish Lawhale**
Java fullStack Developer


Just tell me 👍
