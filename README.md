## 📦 Expense Manager — Full Project Documentation

Welcome to the **Expense Manager** system — a complete solution consisting of a  
**JavaFX Client**, a **SparkJava + Gson Server**, and a **JUnit/TestFX Testing Suite**.  
This main README provides an overview of the architecture, technologies, features,  
and direct links to all module-level documentation.

---

## 🧱 Project Structure

```
src
│
├───main
│   ├───java
│   │     └───com
│   │           └───expense
│   │                 ├───client # JavaFX Desktop Client
│   │                 │     ├──api # API calls (HttpURLConnection / MockApiClient)
│   │                 │     ├───session # Session management
│   │                 │     └───ui # JavaFX UI
│   │                 │         └───panels # Dashboard, Add Expense, View, Manage Budgets
│   │                 │
│   │                 ├───common # Shared DTOs
│   │                 │     └───dto
│   │                 │
│   │                 └───server # Backend Server (Spark + Gson)
│   │                       ├───controllers # REST endpoints
│   │                       ├───dao # Database logic
│   │                       ├───db # Database connection
│   │                       └───models # Server-side models
│   │
│   └───resources # Images
│
└───test
      └───java
            └───com
                  └───expense
                        ├───client
                        │     └───ui # TestFX tests
                        └───server
                              └───db # DB tests
```

---

## 🖥 Client — JavaFX Application

The client is built using **JavaFX 21**, offering:

✔ Login & Signup  
✔ Sidebar navigation  
✔ Dashboard, Add Expenses, View Expenses, Manage Budgets  
✔ REST communication using `HttpURLConnection`  
✔ Uses `Session` class to store user data  
✔ Uses DTO objects shared with backend

📄 **Client Documentation:**  
👉 `src/main/java/com/expense/client/README.md`

---

## 🔧 Server — SparkJava Backend

The backend uses:

- **SparkJava (2.9.4)** for REST routes
- **Gson** for JSON parsing
- **DAO architecture** for database operations
- **Oracle JDBC** for database connectivity
- Simple and lightweight — NO Spring Boot

📄 **Server Documentation:**  
👉 `src/main/java/com/expense/server/README.md`

---

## 🧪 Testing — JUnit + TestFX

This includes:

✔ UI Integration tests (TestFX)  
✔ Mock API tests  
✔ DAO tests  
✔ Headless mode compatible with CI  
✔ Mockito for mocking

📄 **Testing Documentation:**  
👉 `src/test/java/com/expense/README.md`

---

## 🚀 Technologies Used

### **Client**
- JavaFX 21
- CSS
- JUnit 5
- TestFX

### **Server**
- SparkJava
- Gson
- Oracle JDBC
- Custom controllers, DAO, DB layer

### **Testing**
- JUnit 5
- TestFX
- Mockito

---

## 📄 Maven Configuration (POM)

Below is the **POM.xml** used for the entire project:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>expense-tracker</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.eclipse.jetty</groupId>
                <artifactId>jetty-bom</artifactId>
                <version>10.0.24</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>

        <!-- SparkJava for REST server -->
        <dependency>
            <groupId>com.sparkjava</groupId>
            <artifactId>spark-core</artifactId>
            <version>2.9.4</version>
        </dependency>

        <!-- GSON for JSON parsing -->
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.11.0</version>
        </dependency>

        <!-- Oracle JDBC driver -->
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc11</artifactId>
            <version>21.9.0.0</version>
        </dependency>

        <!-- JUnit 5 -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>

        <!-- TestFX -->
        <dependency>
            <groupId>org.testfx</groupId>
            <artifactId>testfx-core</artifactId>
            <version>4.0.17</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.testfx</groupId>
            <artifactId>testfx-junit5</artifactId>
            <version>4.0.17</version>
            <scope>test</scope>
        </dependency>

        <!-- Mockito -->
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.6.0</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.testfx</groupId>
            <artifactId>openjfx-monocle</artifactId>
            <version>jdk-11+26</version>
            <scope>test</scope>
        </dependency>

        <!-- JavaFX -->
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>21.0.9</version>
        </dependency>

        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>21.0.9</version>
        </dependency>

        <!-- Jackson (used by client for some parsing) -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.17.0</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>com.example.MainApp</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
