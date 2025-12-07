package com.expense.client.api;

public class ApiServiceImpl implements ApiService {
    @Override
    public String login(String username, String password) throws Exception {
        String jsonBody = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);
        return ApiClient.post("/users/login", jsonBody);
    }

    @Override
    public String signup(String fullName, String username, String password) throws Exception {
        String jsonBody = String.format(
                "{\"fullName\":\"%s\", \"username\":\"%s\", \"password\":\"%s\"}",
                fullName, username, password
        );
        return ApiClient.post("/users/signup", jsonBody);
    }

}
