package com.expense.client.api;

import com.expense.common.dto.*;

import java.util.*;

public class MockApiClient {

    // Mock GET
    public static String get(String path) {
        switch (path) {
            case "/categories":
                return "[{\"CATEGORY_ID\":1,\"NAME\":\"Food\"}," +
                        "{\"CATEGORY_ID\":2,\"NAME\":\"Transport\"}," +
                        "{\"CATEGORY_ID\":3,\"NAME\":\"Entertainment\"}]";

            case "/budgets/all/1": // userId = 1
                return "[{\"BUDGET_ID\":1,\"USER_ID\":1,\"CATEGORY_ID\":1,\"AMOUNT\":500}," +
                        "{\"BUDGET_ID\":2,\"USER_ID\":1,\"CATEGORY_ID\":2,\"AMOUNT\":200}]";



            case "/expenses/all/1": // userId = 1
                return "[{\"id\":1,\"USER_ID\":1,\"categoryId\":1,\"amount\":50.0,\"note\":\"Lunch\",\"dateCreated\":\"2025-12-07T00:00:00.000Z\"}," +
                        "{\"id\":2,\"USER_ID\":1,\"categoryId\":2,\"amount\":20.0,\"note\":\"Bus\",\"dateCreated\":\"2025-12-06T00:00:00.000Z\"}]";

            default:
                return "{\"error\":\"Unknown GET path\"}";
        }
    }

    // Mock POST
    public static String post(String path, String jsonBody) {
        switch (path) {
            case "/expenses":
                return "{\"success\":\"Expense added successfully\"}";
            case "/reports":
                return "[{\"categoryName\":\"Food\",\"spent\":50,\"budget\":300,\"status\":\"Safe\"}," +
                        "{\"categoryName\":\"Transport\",\"spent\":20,\"budget\":150,\"status\":\"Warning\"}]";
            case "/reports2":
                return "[{\"spent_this_month\":70,\"remaining_budget\":380}]";
            default:
                return "{\"error\":\"Unknown POST path\"}";
        }
    }

    // Mock PUT
    public static String put(String path, String jsonBody) {
        return "{\"success\":\"Budget updated successfully\"}";
    }

    // Mock DELETE
    public static String delete(String path) {
        return "{\"success\":\"Expense deleted successfully\"}";
    }

}
