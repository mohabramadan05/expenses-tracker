package com.expense.server.controllers;

import com.expense.common.dto.UserDto;
import com.expense.server.dao.ExpenseDao;
import com.expense.server.dao.UserDao;
import com.expense.server.models.User;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.text.SimpleDateFormat;

public class UserController {

    private final UserDao userDao = new UserDao();
    private final Gson gson = new Gson();

    public String signup(Request req, Response res) {
        UserDto dto = gson.fromJson(req.body(), UserDto.class);

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPasswordHash(dto.getPassword()); // plain → hashed
        user.setFullName(dto.getFullName());

        boolean success = userDao.signup(user);

        if (success) {
            res.status(201);
            return "{\"message\":\"Signup successful\"}";
        } else {
            res.status(400);
            return "{\"error\":\"Signup failed\"}";
        }
    }

    public String login(Request req, Response res) {
        UserDto dto = gson.fromJson(req.body(), UserDto.class);

        User user = userDao.login(dto.getUsername(), dto.getPassword());

        if (user != null) {
            res.status(200);
            return gson.toJson(new UserDto(
                    user.getUserId(),
                    user.getUsername(),
                    user.getFullName()
            ));
        } else {
            res.status(401);
            return "{\"error\":\"Invalid username or password\"}";
        }
    }
}
