# 🚚 Smart Courier Management System

A fully functional **Spring Boot REST API** for managing courier deliveries — from order creation to final delivery, with JWT authentication, role-based access, and multithreaded bulk assignments.

---

## 🛠️ Tech Stack

| Technology | Details |
|------------|---------|
| Java | 25.0.1 |
| Spring Boot | 3.5.13 |
| Spring Security | JWT Authentication |
| Hibernate / JPA | ORM |
| MySQL | Database |
| Maven | Build Tool |

---

## 👥 User Roles

| Role | Access |
|------|--------|
| **Customer** | Create orders, view own orders |
| **Admin** | Manage all orders, assign deliveries |
| **Agent** | View & update assigned deliveries |

---

## 📦 Project Flow

```
1. Login (get JWT token)
        ↓
2. Create Location (sender & receiver)
        ↓
3. Create Order (Customer)
        ↓
4. Create Parcel (linked to order)
        ↓
5. Assign Delivery to Agent (Admin)
        ↓
6. Agent updates status → IN_TRANSIT
        ↓
7. Agent updates current location
        ↓
8. Agent marks → DELIVERED
        ↓
9. Admin updates Order → DELIVERED
```

---

## 🔐 Authentication

All endpoints (except login) require a Bearer token in the header:

```
Authorization: Bearer <your_jwt_token>
```

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@gmail.com",
  "password": "password123"
}
```

---

## 🌍 Location APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/locations/create` | Create a location |
| GET | `/api/locations/all` | Get all locations |
| GET | `/api/locations/{locationId}` | Get location by ID |
| GET | `/api/locations/city/{city}` | Get locations by city |

### Create Location
```http
POST /api/locations/create
Authorization: Bearer <token>

{
  "city": "Ahmedabad",
  "address": "Ring Road, Ahmedabad",
  "latitude": 23.0225,
  "longitude": 72.5714
}
```

---

## 📋 Order APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/orders/create` | Create an order (Customer) |
| GET | `/api/orders/myOrders` | Get my orders (Customer) |
| GET | `/api/orders/{orderId}` | Get order by ID |
| GET | `/api/orders/all` | Get all orders (Admin) |
| PUT | `/api/orders/{orderId}/status` | Update order status |

### Create Order
```http
POST /api/orders/create
Authorization: Bearer <Customer token>

{
  "senderLocationId": 1,
  "receiverLocationId": 2,
  "expectedDeliveryDate": "2026-05-20"
}
```

### Update Order Status
```http
PUT /api/orders/1/status?status=DELIVERED
Authorization: Bearer <Admin token>
```

**Valid Status Values:** `PENDING` | `IN_TRANSIT` | `DELIVERED` | `CANCELLED`

---

## 📦 Parcel APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/parcel/create` | Create a parcel |
| GET | `/api/parcel/all` | Get all parcels |
| GET | `/api/parcel/{parcelId}` | Get parcel by ID |
| PUT | `/api/parcel/{parcelId}/status` | Update parcel status |

### Create Parcel
```http
POST /api/parcel/create
Authorization: Bearer <token>

{
  "orderId": 1,
  "weight": 2.5,
  "dimension": "30x20x10",
  "description": "Electronics"
}
```

### Update Parcel Status
```http
PUT /api/parcel/1/status?status=DELIVERED
Authorization: Bearer <token>
```

**Valid Status Values:** `PENDING` | `IN_TRANSIT` | `DELIVERED` | `LOST`

---

## 🚴 Delivery Assignment APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/assignments/assign` | Assign delivery to agent |
| POST | `/api/assignments/bulk-assign` | Bulk assign deliveries (Multithreaded) |
| GET | `/api/assignments/my-assignments` | Get agent's assignments |
| GET | `/api/assignments/all` | Get all assignments (Admin) |
| GET | `/api/assignments/{assignmentId}` | Get assignment by ID |
| GET | `/api/assignments/in-transit` | Get in-transit assignments |
| PUT | `/api/assignments/{assignmentId}/status` | Update assignment status |
| PUT | `/api/assignments/{assignmentId}/location/{locationId}` | Update current location |

### Assign Delivery
```http
POST /api/assignments/assign
Authorization: Bearer <Admin token>

{
  "parcelId": 1,
  "agentId": 2,
  "currentLocationId": 1,
  "estimatedDeliveryDate": "2026-05-20"
}
```

### Bulk Assign (Multithreaded)
```http
POST /api/assignments/bulk-assign
Authorization: Bearer <Admin token>

{
  "assignments": [
    {
      "parcelId": 1,
      "agentId": 2,
      "currentLocationId": 1,
      "estimatedDeliveryDate": "2026-05-20"
    },
    {
      "parcelId": 2,
      "agentId": 2,
      "currentLocationId": 1,
      "estimatedDeliveryDate": "2026-05-21"
    }
  ]
}
```

### Update Assignment Status
```http
PUT /api/assignments/1/status?status=IN_TRANSIT
Authorization: Bearer <Agent token>
```

**Valid Status Values:** `ASSIGNED` | `IN_TRANSIT` | `DELIVERED` | `FAILED`

### Update Current Location
```http
PUT /api/assignments/1/location/2
Authorization: Bearer <Agent token>
```

---

## 📊 Standard API Response Format

All APIs return a consistent response structure:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": { },
  "status": 200,
  "timestamp": "2026-05-16T18:12:28.9739542"
}
```

---

## ⚙️ Application Properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_courier
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
jwt.secret=your_jwt_secret_key
jwt.expiration=86400000
```

---

## 🚀 How to Run

```bash
# Clone the repository
git clone https://github.com/yourusername/Smart-Courier-Management-System.git

# Navigate to project
cd Smart-Courier-Management-System

# Run the application
mvn spring-boot:run
```

Server starts on: `http://localhost:8080`

---

## 🗄️ Database Tables

| Table | Description |
|-------|-------------|
| `users` | Customers, Agents, Admins |
| `location` | Sender/Receiver locations |
| `orders` | Customer orders |
| `parcels` | Parcels linked to orders |
| `delivery_assignments` | Agent delivery assignments |

---

## ✅ Features

- JWT Authentication & Authorization
- Role-based access (Customer, Admin, Agent)
- Multithreaded Bulk Assignment using ExecutorService
- Scheduled tasks for delivery tracking
- Global Exception Handling
- Consistent API response format
- 
