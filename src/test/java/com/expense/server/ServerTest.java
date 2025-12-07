package com.expense.server;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.net.http.*;
import java.net.URI;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerTest {

    private static final String BASE = "http://localhost:8080/api";
    private final HttpClient client = HttpClient.newHttpClient();
    private HttpResponse<String> sendPost(String path, String json) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> sendPut(String path, String json) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE + path))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @BeforeAll
    static void startServer() throws InterruptedException {
        new Thread(Server::runServer).start();
        Thread.sleep(2000); // wait for Spark to fully start
    }

    @AfterAll
    static void stopServer() {
        Server.stopServer();
    }

    @Test @Order(1)
    void testGetAllCategories() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/categories"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("CATEGORY_ID"));
    }

    @Test @Order(2)
    void testUserSignup_NewUser() throws Exception {
        String json = """
            {
              "username": "testUserA",
              "password": "123",
              "fullName": "Test User A"
            }
            """;

        HttpResponse<String> res = sendPost("/users/signup", json);

        assertEquals(201, res.statusCode());
        assertTrue(res.body().contains("Signup successful"));
    }

    @Test @Order(3)
    void testUserSignup_ExistingUser() throws Exception {
        String json = """
            {
              "username": "Mohab",
              "password": "1",
              "fullName": "Mohab"
            }
            """;

        HttpResponse<String> res = sendPost("/users/signup", json);

        assertEquals(400, res.statusCode());
        assertTrue(res.body().contains("failed"));
    }

    @Test @Order(4)
    void testUserLogin_Valid() throws Exception {
        String json = """
            {
              "username": "Mohab",
              "password": "1"
            }
            """;

        HttpResponse<String> res = sendPost("/users/login", json);

        assertEquals(200, res.statusCode());
        assertTrue(res.body().contains("\"username\":\"Mohab\""));
    }

    @Test @Order(5)
    void testUserLogin_Invalid() throws Exception {
        String json = """
            {
              "username": "Mohab",
              "password": "wrong"
            }
            """;

        HttpResponse<String> res = sendPost("/users/login", json);

        assertEquals(401, res.statusCode());
        assertTrue(res.body().contains("Invalid username or password"));
    }

    @Test @Order(6)
    void testCreateExpense() throws Exception {
        String json = """
            {
              "USER_ID": 23,
              "CATEGORY_ID": 5,
              "AMOUNT": 25.50,
              "NOTE": "Test expense",
              "DATE_CREATED": "2024-12-05T10:00:00.000+00:00"
            }
            """;

        HttpResponse<String> res = sendPost("/expenses", json);

        assertEquals(201, res.statusCode());
        assertTrue(res.body().contains("success"));
    }

    @Test @Order(7)
    void testGetAllExpenses() throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/expenses/all/23"))
                .GET()
                .build();

        HttpResponse<String> res =
                client.send(req, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, res.statusCode());
        assertTrue(res.body().startsWith("["));
    }

    @Test @Order(8)
    void testGetExpense_NotFound() throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/expenses/999999"))
                .GET()
                .build();

        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        assertEquals(404, res.statusCode());
    }

    @Test @Order(9)
    void testUpdateExpense() throws Exception {
        String json = """
            {
              "USER_ID": 23,
              "CATEGORY_ID": 4,
              "AMOUNT": 50.00,
              "NOTE": "Internet Bill",
              "DATE_CREATED": "2024-12-05T10:00:00.000+00:00"
            }
            """;

        HttpResponse<String> res = sendPut("/expenses/24", json);

        assertEquals(200, res.statusCode());
    }

    @Test @Order(10)
    void testDeleteExpense() throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/expenses/24"))
                .DELETE()
                .build();

        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, res.statusCode());
    }

    @Test @Order(12)
    void testGetAllBudgets() throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/budgets/all/23"))
                .GET()
                .build();

        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, res.statusCode());
        assertTrue(res.body().contains("["));
    }

    @Test @Order(13)
    void testUpdateBudget() throws Exception {
        String json = """
            {
              "USER_ID": 23,
              "CATEGORY_ID": 5,
              "AMOUNT": 200.00
            }
            """;

        HttpResponse<String> res = sendPut("/budgets/25", json);

        assertEquals(200, res.statusCode());
    }

    @Test @Order(15)
    void testReport1() throws Exception {
        String json = """
            { "userId": 23 }
            """;

        HttpResponse<String> res = sendPost("/reports", json);

        assertEquals(200, res.statusCode());
        assertTrue(res.body().contains("spent"));
    }

    @Test @Order(16)
    void testReport2() throws Exception {
        String json = """
            { "userId": 23 }
            """;

        HttpResponse<String> res = sendPost("/reports2", json);

        assertEquals(200, res.statusCode());
        assertTrue(res.body().contains("spent_this_month"));
    }



}
