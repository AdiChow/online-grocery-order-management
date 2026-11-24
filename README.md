# Online Grocery Order Management System

A RESTful backend application built using **Java 21, Spring Boot, Spring Data JPA, and MySQL** to manage customers, grocery items, and their orders.

## Features

- **Customer Management:** Create, retrieve, update, and delete customers.
- **Grocery Item Management:** Manage grocery items, including their category, price, and available quantity.
- **Order Management:** Create, retrieve, update, and delete customer orders.
- **Order Items:** Each order can contain multiple grocery items with individual quantities.
- **Price Calculation:** Order totals are calculated on the server using `BigDecimal`.
- **Historical Pricing:** Each order item stores the item's unit price at the time the order is created.
- **Exception Handling:** Handles missing resources and invalid order inputs with appropriate HTTP status codes.

## Tech Stack

- Java 21
- Spring Boot 4.2.0
- Spring Web MVC
- Spring Data JPA
- MySQL 8.4
- Gradle
- Lombok
- Docker Compose

## Project Structure

```text
src/main/java/com/example/grocery/
├── controller/
├── service/
├── repository/
├── entity/
├── dto/
├── exception/
└── GroceryApplication.java
```

## Database Model

- A customer can have multiple orders.
- An order contains one or more order items.
- Each order item references one grocery item.
- A grocery item can appear in multiple orders.

`OrderItem` acts as the association between an order and a grocery item. It stores the ordered quantity and the unit price at the time of purchase.

## Prerequisites

- Java 21
- Docker Desktop
- Git

## Setup and Run

### 1. Clone the repository

```bash
git clone https://github.com/AdiChow/online-grocery-order-management.git
cd online-grocery-order-management
```

### 2. Configure environment variables

Create a `.env` file in the project root:

```properties
MYSQL_ROOT_PASSWORD=your_root_password
MYSQL_PASSWORD=your_database_password
```

Do not commit this file to GitHub.

### 3. Start MySQL

```bash
docker compose up -d
```

### 4. Run the application

On macOS/Linux:

```bash
./gradlew bootRun
```

On Windows:

```bash
gradlew.bat bootRun
```

The application runs at:

`http://localhost:8080`

Hibernate is configured to update the database schema automatically during development.

## API Endpoints

### Customers

| Method | Endpoint | Description |
|---|---|---|
| POST | `/customers` | Create a customer |
| GET | `/customers` | Retrieve all customers |
| GET | `/customers/{id}` | Retrieve a customer |
| PUT | `/customers/{id}` | Update a customer |
| DELETE | `/customers/{id}` | Delete a customer |

### Grocery Items

| Method | Endpoint | Description |
|---|---|---|
| POST | `/grocery-items` | Create a grocery item |
| GET | `/grocery-items` | Retrieve all grocery items |
| GET | `/grocery-items/{id}` | Retrieve a grocery item |
| PUT | `/grocery-items/{id}` | Update a grocery item |
| DELETE | `/grocery-items/{id}` | Delete a grocery item |

### Orders

| Method | Endpoint | Description |
|---|---|---|
| POST | `/orders?customerId={id}` | Create an order |
| GET | `/orders` | Retrieve all orders |
| GET | `/orders/{id}` | Retrieve an order |
| PUT | `/orders/{id}?customerId={id}` | Update an order |
| DELETE | `/orders/{id}` | Delete an order |

## Sample API Requests

### Create a customer

`POST /customers`

```json
{
  "name": "Rahul Sharma",
  "email": "rahul@example.com",
  "address": "Gurugram, Haryana",
  "phone": "9876543210"
}
```

### Create a grocery item

`POST /grocery-items`

```json
{
  "name": "Milk",
  "category": "Dairy",
  "price": 60.00,
  "quantity": 50
}
```

### Create an order

`POST /orders?customerId=1`

```json
[
  {
    "groceryItem": {
      "id": 1
    },
    "quantity": 2
  },
  {
    "groceryItem": {
      "id": 2
    },
    "quantity": 3
  }
]
```

The application fetches the grocery item details and calculates the order total on the server.

## HTTP Status Codes

- `200 OK` — Successful retrieval or update.
- `201 Created` — Resource successfully created.
- `204 No Content` — Resource successfully deleted.
- `400 Bad Request` — Invalid order input.
- `404 Not Found` — Requested customer, grocery item, or order does not exist.

## Running Tests

```bash
./gradlew test
```

## Future Improvements

- Request and response DTOs.
- Bean Validation for request fields.
- Inventory stock validation and updates.
- Prevention of duplicate grocery items within an order.
- Pagination and sorting.
- Automated integration tests.