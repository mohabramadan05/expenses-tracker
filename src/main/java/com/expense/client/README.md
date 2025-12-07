# 🖥️ Client Module — Expense Manager

The **Client Module** is a modern **JavaFX desktop application** for managing personal expenses. It communicates with the server via REST APIs and provides a rich user interface for managing expenses, budgets, and categories.

---

## 📁 Directory Structure

```
src
└── main
    └── java
        └── com
            └── expense
                └── client
                    ├── api         # API communication classes (REST clients, MockApiClient)
                    ├── session     # User session management
                    └── ui
                        └── panels  # JavaFX UI panels (Dashboard, Add Expense, View Expenses, Manage Budgets)
```

---

## 🔧 Features

* **User Authentication**

    * Login & Signup with server validation
* **Dashboard**

    * Expense summary and charts
* **Add Expense**

    * Enter new expenses by category, amount, and date
* **View Expenses**

    * List all expenses with filtering and sorting
* **Manage Budgets**

    * View and update budget allocations per category
* **Global Session Handling**

    * Maintains user info across panels
* **UI Navigation**

    * Sidebar with active panel highlighting
* **Integration with Server**

    * Fetches categories, expenses, and budgets via REST API
* **Mock API Support**

    * For testing without connecting to the real server

---

## 🛠 Technologies Used

* **JavaFX 21** — GUI framework
* **CSS** — Styling for panels and controls
* **Gson / Jackson** — JSON parsing for REST responses
* **TestFX** — UI testing (see `testing` module)
* **JUnit 5** — Unit tests for client logic

---

## 📄 Main Components

| Component       | Description                                                             |
| --------------- | ----------------------------------------------------------------------- |
| `ApiClient`     | Handles HTTP requests to the server                                     |
| `MockApiClient` | Provides mock JSON responses for testing                                |
| `Session`       | Maintains global user session data                                      |
| `ui/panels`     | JavaFX panels for Dashboard, Add Expense, View Expenses, Manage Budgets |
| `MainClient`    | Entry point for the JavaFX application                                  |

---

## 🚀 Running the Client

### Using IntelliJ IDEA

1. Open `MainClient.java`
2. Right-click → **Run**

### Using Maven

```bash
mvn clean javafx:run
```

---

## ⚙️ Configuration

* REST endpoints are configured in `ApiClient` or replaced by `MockApiClient` for testing.
* Styles can be customized via CSS files in `resources`.
* Ensure Java 17 and JavaFX 21 are properly installed.

---
## 💻 Application Preview

Login Page
![Login](https://github.com/mohabramadan05/expenses-tracker/blob/main/src/main/resources/1.png?raw=true)

Signup Page
![Signup](https://github.com/mohabramadan05/expenses-tracker/blob/main/src/main/resources/2.png?raw=true)

Landing Page "Dashboard Panel"
![Dashboard](https://github.com/mohabramadan05/expenses-tracker/blob/main/src/main/resources/3.png?raw=true)

Landing Page "Add Expenses Panel"
![Add Expenses](https://github.com/mohabramadan05/expenses-tracker/blob/main/src/main/resources/4.png?raw=true)

Landing Page "View Expenses Panel"
![View Expenses](https://github.com/mohabramadan05/expenses-tracker/blob/main/src/main/resources/5.png?raw=true)

Landing Page "Manage Budgets Panel"
![Manage Budgets](https://github.com/mohabramadan05/expenses-tracker/blob/main/src/main/resources/6.png?raw=true)

---

## 🧪 Testing

All client-side UI tests are in the `testing` module:
`../testing/src/test/java/com/expense/client/ui/`

Tests cover:

* Panel navigation
* Expense submission
* Budget updates
* Category combobox loading

---

## 📄 Related Documentation

* **Server Documentation**
  `../server/README.md`

* **Testing Documentation**
  `../testing/README.md`

* **Root Project Documentation**
  `../README.md`
