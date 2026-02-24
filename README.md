

# 🛒 RevShop – Full-Stack E-Commerce Platform

**RevShop** is a sophisticated, role-based E-Commerce ecosystem designed for seamless shopping and business management. Built with a scalable **N-Tier Architecture**, it bridges the gap between a robust Java backend and a responsive user interface, simulating real-world retail workflows from inventory tracking to secure checkout.

---

## 🏗️ Architecture Overview

The system follows a strict **Layered (N-Tier) Architecture** to ensure a separation of concerns, making the transition from a Console-based app to a Web-based Spring Boot application seamless.

* **Presentation Layer:** Handles user interaction (Console UI / Web Controllers).
* **Service Layer:** The "Brain" containing business logic, validations, and workflows.
* **DAO (Data Access) Layer:** Manages persistence and SQL execution via JDBC.
* **Model Layer:** POJO entities representing the core business domain.
* **Utility Layer:** Shared resources like DB connections and security hashing.

---

## 🚀 Key Features

### 🛍️ For Buyers

* **Smart Discovery:** Browse, keyword search, and category-based filtering.
* **Cart Management:** Real-time total calculation and quantity adjustments.
* **Checkout Workflow:** Multi-mode simulated payments (UPI, Card, COD).
* **Personalization:** Wishlist (Favorites), order history, and product reviews.
* **Notifications:** Instant in-app alerts for order confirmations.

### 🏪 For Sellers

* **Inventory Control:** Manage products with MRP, discounts, and descriptions.
* **Stock Intelligence:** Configure low-stock thresholds with automatic console alerts.
* **Business Analytics:** View incoming orders and track customer feedback.
* **Security:** Business profile isolation from standard user data.

---

## 🛠️ Tech Stack

| Category | Technology |
| --- | --- |
| **Language** | Java 21 (LTS) |
| **Framework** | Spring Boot (Planned/Current) |
| **Database** | MySQL 8.0 |
| **Persistence** | JDBC / Spring Data JPA |
| **Security** | SHA-256 Password Hashing |
| **Build Tool** | Maven |
| **Version Control** | Git |

---

## 📂 Project Structure

```text
revshop
├── 📂 database             # SQL Schemas, Seeds, and Updates
├── 📂 src/main/java
│   ├── 📦 app              # Main Application Entry Point
│   ├── 📦 controller       # Role-based Request Handling
│   ├── 📦 service          # Business Logic & Validations
│   ├── 📦 dao              # JDBC/Persistence Operations
│   ├── 📦 model            # Entity Classes (User, Product, Order)
│   └── 📦 util             # DB Connection & Security Utils
├── 📂 src/main/resources   # Frontend Assets (HTML/CSS/JS) & Config
├── 📄 pom.xml              # Maven Dependencies
└── 📄 README.md            # Documentation

```

---

## 📊 Database Design (ERD)

The relational schema is optimized for data integrity and high performance.

* **Users & Sellers:** 1:1 Relationship for business-specific data.
* **Products & Inventory:** 1:N Relationship with Sellers.
* **Order Tracking:** Deep mapping between `orders` and `order_items` to preserve historical pricing.

---

## 🔄 Core Workflows

### 1. The Purchase Journey

1. **Selection:** Buyer adds items to Cart.
2. **Validation:** Service layer checks stock availability.
3. **Transaction:** Order is logged; Inventory is decremented.
4. **Alerting:** Buyer gets a receipt; Seller gets a "New Order" notification.

### 2. Low-Stock Alert System

```java
if (currentStock <= thresholdValue) {
    NotificationService.sendAlert(sellerId, "Low Stock Warning!");
}

```

---

## ⚙️ Getting Started

1. **Clone the Repo:**
```bash
git clone https://github.com/your-username/revshop.git

```


2. **Configure Database:**
Update `DBConnection.java` or `application.properties` with your MySQL credentials.
3. **Build & Install:**
```bash
mvn clean install

```


4. **Run:**
```bash
java -cp target/revshop-1.0.jar app.RevShopApp

```



---

## 🔮 Future Roadmap

* [ ] **Web UI:** Transitioning to Spring Boot Thymeleaf/React.
* [ ] **Security:** Implementing JWT (JSON Web Tokens) for stateless auth.
* [ ] **Cloud:** Deploying to AWS/Azure using Docker containers.
* [ ] **External APIs:** Integration with Stripe/Razorpay for actual payments.

---

**Developed by Abhishek Lawhale *Building scalable solutions for the modern web.*
