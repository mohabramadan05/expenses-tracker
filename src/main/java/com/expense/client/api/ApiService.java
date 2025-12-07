package com.expense.client.api;

public interface ApiService {
    String login(String username, String password) throws Exception;
    String signup(String fullName, String username, String password) throws Exception;

}
