# Expense Tracker API Documentation

## Base URL
```
http://localhost:8080/api
```

## Overview
This API provides endpoints for managing personal expenses, budgets, categories, and generating budget reports. Built with Spark Framework using JSON request/response format.

---

## Table of Contents
1. [Authentication](#authentication)
2. [Expense Endpoints](#expense-endpoints)
3. [Budget Endpoints](#budget-endpoints)
4. [Category Endpoints](#category-endpoints)
5. [Report Endpoints](#report-endpoints)
6. [Data Models](#data-models)
7. [Status Codes](#status-codes)

---

## Authentication

### 1. User Signup

Register a new user account.

**Endpoint:** `POST /api/users/signup`

**Request Body:**
```json
{
  "username": "johndoe",
  "password": "securePassword123",
  "fullName": "John Doe"
}
```

**Success Response:**

**Code:** `201 CREATED`
```json
{
  "message": "Signup successful"
}
```

**Error Response:**

**Code:** `400 BAD REQUEST`
```json
{
  "error": "Signup failed"
}
```

---

### 2. User Login

Authenticate a user and retrieve user details.

**Endpoint:** `POST /api/users/login`

**Request Body:**
```json
{
  "username": "johndoe",
  "password": "securePassword123"
}
```

**Success Response:**

**Code:** `200 OK`
```json
{
  "userId": 1,
  "username": "johndoe",
  "fullName": "John Doe"
}
```

**Error Response:**

**Code:** `401 UNAUTHORIZED`
```json
{
  "error": "Invalid username or password"
}
```

---

## Expense Endpoints

### 1. Get All Expenses for a User

Retrieves all expenses for a specific user, ordered by date created (newest first).

**Endpoint:** `GET /api/expenses/all/:id`

**URL Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| id | integer | User ID |

**Request Example:**
```http
GET /api/expenses/all/1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json
```

**Success Response:**

**Code:** `200 OK`
```json
[
  {
    "EXPENSE_ID": 1,
    "USER_ID": 1,
    "CATEGORY_ID": 5,
    "AMOUNT": 45.50,
    "NOTE": "Grocery shopping",
    "DATE_CREATED": "2024-11-20T10:30:00.000+00:00"
  },
  {
    "EXPENSE_ID": 2,
    "USER_ID": 1,
    "CATEGORY_ID": 3,
    "AMOUNT": 120.00,
    "NOTE": "Electric bill",
    "DATE_CREATED": "2024-11-19T15:45:00.000+00:00"
  }
]
```

**Error Response:**

**Code:** `400 BAD REQUEST`
```json
{
  "error": "Invalid ID format"
}
```

---

### 2. Get Expense by ID

Retrieve a specific expense by its ID.

**Endpoint:** `GET /api/expenses/:id`

**URL Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| id | integer | Expense ID |

**Success Response:**

**Code:** `200 OK`
```json
{
  "EXPENSE_ID": 1,
  "USER_ID": 1,
  "CATEGORY_ID": 5,
  "AMOUNT": 45.50,
  "NOTE": "Grocery shopping",
  "DATE_CREATED": "2024-11-20T10:30:00.000+00:00"
}
```

**Error Responses:**

**Code:** `404 NOT FOUND`
```json
{
  "error": "Expense not found"
}
```

**Code:** `400 BAD REQUEST`
```json
{
  "error": "Invalid ID format"
}
```

---

### 3. Create New Expense

Add a new expense to the system.

**Endpoint:** `POST /api/expenses`

**Request Body:**
```json
{
  "USER_ID": 1,
  "CATEGORY_ID": 5,
  "AMOUNT": 45.50,
  "NOTE": "Grocery shopping",
  "DATE_CREATED": "2024-11-20T10:30:00.000+00:00"
}
```

**Field Descriptions:**
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| USER_ID | integer | Yes | ID of the user creating the expense |
| CATEGORY_ID | integer | Yes | Category ID for the expense |
| AMOUNT | decimal | Yes | Expense amount (must be positive) |
| NOTE | string | No | Optional description of the expense |
| DATE_CREATED | datetime | Yes | Date when expense was created |

**Success Response:**

**Code:** `201 CREATED`
```json
{
  "message": "Expense added successfully"
}
```

**Error Responses:**

**Code:** `400 BAD REQUEST`
```json
{
  "error": "Invalid request: [error details]"
}
```

**Code:** `500 INTERNAL SERVER ERROR`
```json
{
  "error": "Failed to add expense"
}
```

---

### 4. Update Expense

Update an existing expense.

**Endpoint:** `PUT /api/expenses/:id`

**URL Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| id | integer | Expense ID to update |

**Request Body:**
```json
{
  "USER_ID": 1,
  "CATEGORY_ID": 5,
  "AMOUNT": 50.00,
  "NOTE": "Updated grocery shopping",
  "DATE_CREATED": "2024-11-20T10:30:00.000+00:00"
}
```

**Success Response:**

**Code:** `200 OK`
```json
{
  "message": "Expense updated successfully"
}
```

**Error Responses:**

**Code:** `404 NOT FOUND`
```json
{
  "error": "Expense not found or update failed"
}
```

**Code:** `400 BAD REQUEST`
```json
{
  "error": "Invalid request: [error details]"
}
```

---

### 5. Delete Expense

Delete an expense by ID.

**Endpoint:** `DELETE /api/expenses/:id`

**URL Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| id | integer | Expense ID to delete |

**Success Response:**

**Code:** `200 OK`
```json
{
  "message": "Expense deleted successfully"
}
```

**Error Responses:**

**Code:** `404 NOT FOUND`
```json
{
  "error": "Expense not found or delete failed"
}
```

**Code:** `400 BAD REQUEST`
```json
{
  "error": "Invalid ID format"
}
```

---

## Budget Endpoints

### 1. Get All Budgets for a User

Retrieve all budgets set by a specific user.

**Endpoint:** `GET /api/budgets/all/:id`

**URL Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| id | integer | User ID |

**Success Response:**

**Code:** `200 OK`
```json
[
  {
    "BUDGET_ID": 1,
    "USER_ID": 1,
    "CATEGORY_ID": 5,
    "AMOUNT": 500.00
  },
  {
    "BUDGET_ID": 2,
    "USER_ID": 1,
    "CATEGORY_ID": 3,
    "AMOUNT": 200.00
  }
]
```

**Error Response:**

**Code:** `400 BAD REQUEST`
```json
{
  "error": "Invalid ID format"
}
```

---

### 2. Get Budget by ID

Retrieve a specific budget by its ID.

**Endpoint:** `GET /api/budgets/:id`

**URL Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| id | integer | Budget ID |

**Success Response:**

**Code:** `200 OK`
```json
{
  "BUDGET_ID": 1,
  "USER_ID": 1,
  "CATEGORY_ID": 5,
  "AMOUNT": 500.00
}
```

**Error Responses:**

**Code:** `404 NOT FOUND`
```json
{
  "error": "budget not found"
}
```

**Code:** `400 BAD REQUEST`
```json
{
  "error": "Invalid ID format"
}
```

---

### 3. Create New Budget

Add a new budget for a category.

**Endpoint:** `POST /api/budgets`

**Request Body:**
```json
{
  "USER_ID": 1,
  "CATEGORY_ID": 5,
  "AMOUNT": 500.00
}
```

**Field Descriptions:**
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| USER_ID | integer | Yes | ID of the user setting the budget |
| CATEGORY_ID | integer | Yes | Category ID for the budget |
| AMOUNT | decimal | Yes | Budget amount (must be positive) |

**Success Response:**

**Code:** `201 CREATED`
```json
{
  "message": "Budget added successfully"
}
```

**Error Responses:**

**Code:** `400 BAD REQUEST`
```json
{
  "error": "Invalid request: [error details]"
}
```

**Code:** `500 INTERNAL SERVER ERROR`
```json
{
  "error": "Failed to add budget"
}
```

---

### 4. Update Budget

Update an existing budget.

**Endpoint:** `PUT /api/budgets/:id`

**URL Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| id | integer | Budget ID to update |

**Request Body:**
```json
{
  "USER_ID": 1,
  "CATEGORY_ID": 5,
  "AMOUNT": 600.00
}
```

**Success Response:**

**Code:** `200 OK`
```json
{
  "message": "Budget updated successfully"
}
```

**Error Responses:**

**Code:** `404 NOT FOUND`
```json
{
  "error": "Budget not found or update failed"
}
```

**Code:** `400 BAD REQUEST`
```json
{
  "error": "Invalid request: [error details]"
}
```

---

### 5. Delete Budget

Delete a budget by ID.

**Endpoint:** `DELETE /api/budgets/:id`

**URL Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| id | integer | Budget ID to delete |

**Success Response:**

**Code:** `200 OK`
```json
{
  "message": "Budget deleted successfully"
}
```

**Error Responses:**

**Code:** `404 NOT FOUND`
```json
{
  "error": "Budget not found or delete failed"
}
```

**Code:** `400 BAD REQUEST`
```json
{
  "error": "Invalid ID format"
}
```

---

## Category Endpoints

### 1. Get All Categories

Retrieve all expense categories available in the system.

**Endpoint:** `GET /api/categories`

**Request Example:**
```http
GET /api/categories HTTP/1.1
Host: localhost:8080
Content-Type: application/json
```

**Success Response:**

**Code:** `200 OK`
```json
[
  {
    "CATEGORY_ID": 1,
    "NAME": "Food & Dining",
    "DESCRIPTION": "Groceries, restaurants, cafes"
  },
  {
    "CATEGORY_ID": 2,
    "NAME": "Transportation",
    "DESCRIPTION": "Gas, public transit, parking"
  },
  {
    "CATEGORY_ID": 3,
    "NAME": "Utilities",
    "DESCRIPTION": "Electric, water, internet bills"
  }
]
```

---

## Report Endpoints

### 1. Get Monthly Budget Report

Generate a comprehensive budget report showing spending vs. budget for each category.

**Endpoint:** `POST /api/reports`

**Request Body:**
```json
{
  "userId": 1
}
```

**Success Response:**

**Code:** `200 OK`
```json
[
  {
    "userId": 1,
    "categoryId": 5,
    "spent": 450.00,
    "budget": 500.00,
    "remaining": 50.00,
    "status": "On Track"
  },
  {
    "userId": 1,
    "categoryId": 3,
    "spent": 220.00,
    "budget": 200.00,
    "remaining": -20.00,
    "status": "Over Budget"
  }
]
```

**Field Descriptions:**
| Field | Type | Description |
|-------|------|-------------|
| userId | integer | ID of the user |
| categoryId | integer | Category ID |
| spent | decimal | Total amount spent in this category |
| budget | decimal | Budget amount set for this category |
| remaining | decimal | Remaining budget (negative if over budget) |
| status | string | Budget status (e.g., "On Track", "Over Budget") |

---

### 2. Get Monthly Overall Budget Report

Generate a comprehensive budget report showing spending vs. budget for all budgets categories.

**Endpoint:** `POST /api/reports2`

**Request Body:**
```json
{
  "userId": 1
}
```

**Success Response:**

**Code:** `200 OK`
```json
[
  {
    "userId": 1,
    "spent_this_month": 600.00,
    "remaining_budget": 1200.00
  }
]
```

**Field Descriptions:**
| Field | Type | Description |
|-------|------|-------------|
| userId | integer | ID of the user |
| spent_this_month | decimal | Total spent amount |
| remaining_budget | decimal | Total remaining amount |

---

## Data Models

### User
```json
{
  "userId": "integer - Unique user identifier",
  "username": "string - Username for login",
  "password": "string - User password (plain text in request, hashed in storage)",
  "fullName": "string - User's full name"
}
```

### Expense
```json
{
  "EXPENSE_ID": "integer - Unique expense identifier",
  "USER_ID": "integer - ID of the user who created the expense",
  "CATEGORY_ID": "integer - ID of the expense category",
  "AMOUNT": "decimal - Expense amount",
  "NOTE": "string - Optional description of the expense",
  "DATE_CREATED": "datetime - Timestamp when expense was created"
}
```

### Budget
```json
{
  "BUDGET_ID": "integer - Unique budget identifier",
  "USER_ID": "integer - ID of the user who set the budget",
  "CATEGORY_ID": "integer - ID of the category for this budget",
  "AMOUNT": "decimal - Budget amount"
}
```

### Category
```json
{
  "CATEGORY_ID": "integer - Unique category identifier",
  "NAME": "string - Category name",
  "DESCRIPTION": "string - Category description"
}
```

### Budget Report
```json
{
  "userId": "integer - ID of the user",
  "categoryId": "integer - Category ID",
  "spent": "decimal - Total amount spent",
  "budget": "decimal - Budget amount",
  "remaining": "decimal - Remaining budget",
  "status": "string - Budget status"
}
```

---

## Status Codes

| Code | Description |
|------|-------------|
| 200 | Success - Request completed successfully |
| 201 | Created - Resource created successfully |
| 400 | Bad Request - Invalid input or malformed request |
| 401 | Unauthorized - Invalid credentials |
| 404 | Not Found - Resource doesn't exist |
| 500 | Internal Server Error - Server-side error |

---

## CORS Configuration

The API supports Cross-Origin Resource Sharing (CORS) with the following headers:

```
Access-Control-Allow-Origin: *
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
Access-Control-Allow-Headers: Content-Type, Authorization
```

---

## Error Handling

All error responses follow a consistent JSON format:

```json
{
  "error": "Error description here"
}
```

For successful operations that don't return data:

```json
{
  "message": "Success message here"
}
```

---

## Notes

- All requests and responses use JSON format
- Content-Type header should be set to `application/json`
- Dates are in ISO 8601 format
- All amounts are in decimal format with two decimal places
- The server runs on port 8080 by default

---

## Quick Reference

### Authentication
```
POST   /api/users/signup    - Register new user
POST   /api/users/login     - Login user
```

### Expenses
```
GET    /api/expenses/all/:id   - Get all expenses for user
GET    /api/expenses/:id       - Get specific expense
POST   /api/expenses           - Create expense
PUT    /api/expenses/:id       - Update expense
DELETE /api/expenses/:id       - Delete expense
```

### Budgets
```
GET    /api/budgets/all/:id    - Get all budgets for user
GET    /api/budgets/:id        - Get specific budget
POST   /api/budgets            - Create budget
PUT    /api/budgets/:id        - Update budget
DELETE /api/budgets/:id        - Delete budget
```

### Categories
```
GET    /api/categories         - Get all categories
```

### Reports
```
POST   /api/reports            - Get budget report for user
POST   /api/reports2            - Get budget report for user
```
