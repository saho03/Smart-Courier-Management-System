# 📦 Smart Courier Management System

<div align="center">

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.13-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Auth-black?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

A production-ready **RESTful backend** for managing courier operations — from order placement and parcel assignment to real-time delivery tracking with scheduled background tasks.

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [API Endpoints](#-api-endpoints)
- [Database Schema](#-database-schema)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [Scheduled Tasks](#-scheduled-tasks)
- [Roles & Permissions](#-roles--permissions)

---

## 🧠 Overview

The **Smart Courier Management System** is a full-featured backend REST API built with **Spring Boot 3** and **Spring Security**. It handles the complete lifecycle of a courier order — user registration, order placement, parcel tracking, agent assignment, and automated delivery monitoring — all secured with **JWT-based authentication**.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔐 **JWT Authentication** | Secure stateless login with token-based access control |
| 👥 **Role-Based Access** | Four roles: `Admin`, `Manager`, `Agent`, `Customer` |
| 📬 **Order Management** | Create, view, and update courier orders with status tracking |
| 🚚 **Delivery Assignment** | Assign agents to parcels individually or in bulk |
| 📍 **Live Location Updates** | Update and track current delivery location per assignment |
| ⏱️ **Scheduled Tasks** | Background jobs for tracking, delay detection, and daily summaries |
| 🌐 **Global Exception Handling** | Centralized error responses with clean `ApiResponse` DTO wrapper |
| ✅ **Input Validation** | Bean validation on all incoming request bodies |
| 📊 **Delivery Statistics** | Automated metrics: assigned, in-transit, delivered, failed rates |

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5.13 |
| Security | Spring Security + JWT (jjwt 0.12.3) |
| Persistence | Spring Data JPA + Hibernate |
| Database | MySQL 8.0 |
| Build Tool | Maven |
| Scheduling | Spring `@Scheduled` tasks |
| Validation | Jakarta Bean Validation |

---

## 📁 Project Structure

```
Smart-Courier-Management-System/
│
├── src/main/java/com/Courier/Smart_Courier_Management_System/
│   │
│   ├── Controller/
│   │   ├── AuthController.java                # POST /api/auth/login
│   │   ├── UserController.java                # POST /user/register
│   │   ├── OrderController.java               # Order CRUD & status management
│   │   └── DeliveryAssignmentController.java  # Assignment & tracking
│   │
│   ├── DTO/
│   │   ├── ApiResponse.java                   # Unified response wrapper
│   │   ├── LoginRequest/Response.java
│   │   ├── RegisterRequest/Response.java
│   │   ├── OrderRequest/Response.java
│   │   ├── DeliveryAssignmentRequest/Response.java
│   │   ├── BulkAssignmentRequest/Response.java
│   │   └── ErrorResponse.java
│   │
│   ├── Model/
│   │   ├── User.java                          # User entity
│   │   ├── Order.java                         # Courier order entity
│   │   ├── Parcel.java                        # Parcel entity
│   │   ├── DeliveryAssignment.java            # Assignment entity
│   │   ├── Location.java                      # Location entity
│   │   ├── UserRole.java                      # Enum: Admin, Manager, Agent, Customer
│   │   ├── OrderStatus.java                   # Enum: PENDING → ASSIGNED → IN_TRANSIT → DELIVERED / CANCELLED
│   │   └── DeliveryStatus.java                # Enum: ASSIGNED → IN_TRANSIT → DELIVERED / FAILED
│   │
│   ├── Repository/
│   │   ├── UserRepository.java
│   │   ├── OrderRepository.java
│   │   ├── ParcelRepository.java
│   │   ├── DeliveryAssignmentRepository.java
│   │   └── LocationRepository.java
│   │
│   ├── Service/
│   │   ├── AuthService.java
│   │   ├── RegisterUser.java
│   │   ├── UserService.java
│   │   ├── OrderService.java
│   │   ├── DeliveryAssignmentService.java
│   │   └── ScheduledDeliveryService.java      # Background scheduled tasks
│   │
│   ├── Security/
│   │   ├── JwtTokenProvider.java              # JWT generation & validation
│   │   ├── JwtAuthenticationFilter.java       # Per-request JWT filter
│   │   └── SecurityConfig.java               # Security rules & CORS
│   │
│   └── Exception/
│       ├── GlobalExceptionHandler.java
│       ├── InvalidCredentialsException.java
│       ├── InvalidRoleException.java
│       ├── OrderNotFoundException.java
│       ├── UserAlreadyExistsException.java
│       ├── UnauthorizedException.java
│       ├── PasswordMismatchException.java
│       └── LocationNotFound.java
│
└── src/main/resources/
    └── application.properties
```

---

## 🌐 API Endpoints

### 🔓 Auth & User (Public)

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/user/register` | Register a new user |
| `POST` | `/api/auth/login` | Login and receive JWT token |

### 📦 Orders (Authenticated)

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/orders/create` | Place a new courier order |
| `GET` | `/api/orders/{orderId}` | Get order by ID |
| `GET` | `/api/orders/myOrders` | Get all orders for logged-in customer |
| `GET` | `/api/orders/all` | Get all orders (Admin/Manager) |
| `PUT` | `/api/orders/{orderId}/status?status=` | Update order status |

### 🚚 Delivery Assignments (Authenticated)

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/assignments/assign` | Assign a parcel to a delivery agent |
| `POST` | `/api/assignments/bulk-assign` | Bulk assign multiple parcels at once |
| `GET` | `/api/assignments/{assignmentId}` | Get assignment by ID |
| `GET` | `/api/assignments/my-assignments` | Get assignments for the logged-in agent |
| `GET` | `/api/assignments/all` | Get all assignments |
| `GET` | `/api/assignments/in-transit` | Get all currently in-transit deliveries |
| `PUT` | `/api/assignments/{assignmentId}/status?status=` | Update delivery status |
| `PUT` | `/api/assignments/{assignmentId}/location/{locationId}` | Update current parcel location |

> **All protected endpoints require:** `Authorization: Bearer <token>` header.

---

## 🗄️ Database Schema

The system maps **5 core entities** via JPA/Hibernate:

```
User ──────────────────────────────── UserRole (Admin / Manager / Agent / Customer)
 │
 ├──< Order >──── Location (senderLocation & receiverLocation)
 │       └── OrderStatus: PENDING → ASSIGNED → IN_TRANSIT → DELIVERED / CANCELLED
 │
 └──< DeliveryAssignment >──── Parcel
             ├── agent (User)
             ├── currentLocation (Location)
             └── DeliveryStatus: ASSIGNED → IN_TRANSIT → DELIVERED / FAILED
```

> Tables are auto-created and updated by Hibernate (`spring.jpa.hibernate.ddl-auto=update`).

---

## 🚀 Getting Started

### Prerequisites

- **Java 17+** — [Download](https://adoptium.net/)
- **MySQL 8.0+** — [Download](https://dev.mysql.com/downloads/)
- **Maven 3.8+** — [Download](https://maven.apache.org/) *(or use the included `mvnw` wrapper)*

### 1. Clone the Repository

```bash
git clone https://github.com/saho03/Smart-Courier-Management-System.git
cd Smart-Courier-Management-System
```

### 2. Create the Database

```sql
CREATE DATABASE courier_db;
```

### 3. Configure the Application

Open `src/main/resources/application.properties` and update with your credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/courier_db?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

jwt.secret=your-super-secret-key-make-this-long-and-random
jwt.expiration=86400000
```

### 4. Build & Run

```bash
# Using the Maven wrapper (Linux/Mac)
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

The server will start at **`http://localhost:8080`** 🎉

### 5. Test the API

Register a user:
```bash
curl -X POST http://localhost:8080/user/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John","email":"john@example.com","password":"secret","role":"Customer"}'
```

Login to get a JWT token:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"secret"}'
```

---

## ⚙️ Configuration Reference

| Property | Default | Description |
|---|---|---|
| `spring.datasource.url` | `localhost:3306/courier_db` | MySQL connection URL |
| `spring.jpa.hibernate.ddl-auto` | `update` | Auto-creates/updates DB tables on startup |
| `jwt.secret` | *(change this!)* | Secret key for signing JWT tokens |
| `jwt.expiration` | `86400000` | Token validity in milliseconds (24 hours) |
| `spring.jpa.open-in-view` | `false` | Disabled for performance best practices |

> ⚠️ **Security Notice:** Always change `jwt.secret` and database credentials before deploying to production.

---

## ⏰ Scheduled Tasks

The `ScheduledDeliveryService` automatically runs background monitoring jobs:

| Task | Frequency | Description |
|---|---|---|
| `updateInTransitDeliveries` | Every **30 seconds** | Polls all in-transit deliveries for tracking updates |
| `checkForDelayedDeliveries` | Every **1 minute** | Flags assignments overdue past estimated delivery time |
| `generateDeliveryStatistics` | Every **2 minutes** | Logs counts by status and calculates success rate |
| `dailySummary` | **Daily at 6:00 PM** | Reports total deliveries completed for the day |
| `cleanupFailedDeliveries` | Every **5 minutes** *(10s startup delay)* | Reviews failed assignments for potential retry |

---

## 👤 Roles & Permissions

| Role | Capabilities |
|---|---|
| `Customer` | Register, login, place orders, view own orders |
| `Agent` | View assigned deliveries, update delivery status and current location |
| `Manager` | View all orders and assignments, assign parcels to agents |
| `Admin` | Full access to all system resources |

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

<div align="center">

Made with ❤️ by [@saho03](https://github.com/saho03)

⭐ If this project helped you, consider giving it a star!

</div>
