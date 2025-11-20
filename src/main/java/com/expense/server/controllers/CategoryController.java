package com.expense.server.controllers;

 import com.expense.common.dto.CategoryDto;
 import com.expense.server.dao.CategoryDao;
 import com.expense.server.models.Category;
 import com.google.gson.Gson;

import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class CategoryController {
    private final CategoryDao categoryDao = new CategoryDao();
    private final Gson gson = new Gson();

    private CategoryDto toDTO(Category category) {
        return new CategoryDto(
                category.getCategoryID(),
                category.getNAME(),
                category.getDESCRIPTION()
        );
    }

    public String getAllCategories(Request req, Response res) {
        List<Category> categories = categoryDao.getAllCategories();
        List<CategoryDto> dtos = new ArrayList<>();

        for (Category category : categories) {
            dtos.add(toDTO(category));
        }

        return gson.toJson(dtos);
    }

}
