# 🧪 Testing Module — Expense Manager

This module contains all **automated tests** for the Expense Manager application, including:

- ✅ **UI tests** using **TestFX**
- ✅ **Mocked API tests** using **Mockito**
- ✅ **Pane navigation tests** (Dashboard → Add Expense → View → Manage Budgets)
- ✅ **DAO / Database tests**
- ✅ **Headless mode CI-friendly configuration**

This document explains how to configure, run, and understand the tests.

---

## 🧰 Testing Technologies

| Component | Tech |
|----------|------|
| **Unit Testing** | JUnit 5 |
| **UI Testing** | TestFX |
| **Mocking** | Mockito |
| **Headless UI** | Monocle (TestFX) |
| **Assertions** | JUnit Jupiter |

---

# 🖥 UI Testing (TestFX)

All JavaFX UI tested using **TestFX**.  
We test:

### ✔ Login screen
### ✔ Landing page navigation
### ✔ Pane switching
### ✔ Button actions
### ✔ API calls via MockApiClient

---

# 📊 Server Testing

All Endpoints tested
We test:

### ✔ get endpoints
### ✔ post endpoints
### ✔ put endpoints
### ✔ delete endpoints
---