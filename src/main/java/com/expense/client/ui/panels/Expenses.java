package com.expense.client.ui.panels;

import com.expense.client.api.ApiClient;
import com.expense.client.session.Session;
import com.expense.common.dto.CategoryDto;
import com.expense.common.dto.ExpenseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Expenses {
    private static List<CategoryDto> cachedCategories = null;


    public static Pane loadViewExpensesPage() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #fff;");

        Label title = new Label("Expense List");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Table
        TableView<ExpenseDto> table = new TableView<>();
        table.setPrefHeight(600);

        // Date column (formatted)
        TableColumn<ExpenseDto, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(data -> {
            Date date = data.getValue().getDateCreated();
            String formatted = new SimpleDateFormat("yyyy-MM-dd").format(date);
            return new SimpleStringProperty(formatted);
        });

        // Category column (you will need a method to map CATEGORY_ID to name)
        TableColumn<ExpenseDto, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data -> {
            int catId = data.getValue().getCategoryId();
            String catName = getCategoryNameById(catId); // implement this method
            return new SimpleStringProperty(catName);
        });

        // Amount column
        TableColumn<ExpenseDto, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(data -> new SimpleStringProperty("$" + data.getValue().getAmount()));

        // Note column
        TableColumn<ExpenseDto, String> noteCol = new TableColumn<>("Note");
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));

        // Action buttons
        TableColumn<ExpenseDto, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteBtn = new Button("🗑");

            {
                deleteBtn.setStyle("-fx-background-color: #ff3b30; -fx-text-fill: white; -fx-cursor: hand;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    deleteBtn.setOnAction(e -> {
                        ExpenseDto expense = getTableView().getItems().get(getIndex());
                        try {
                            String path = "/expenses/" + expense.getId(); // your API path
                            String response = ApiClient.delete(path);
                            System.out.println("Deleted: " + response);

                            // Remove from table
                            getTableView().getItems().remove(expense);

                        } catch (IOException ex) {
                            ex.printStackTrace();
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to delete expense: " + ex.getMessage(), ButtonType.OK);
                            alert.showAndWait();
                        }
                    });

                    HBox box = new HBox(10, deleteBtn);
                    setGraphic(box);
                }
            }
        });

        table.getColumns().addAll(dateCol, categoryCol, amountCol, noteCol, actionsCol);

        // Fetch data from API
        List<ExpenseDto> expenses = fetchExpenses(); // implement this API call
        table.getItems().setAll(expenses);

        root.getChildren().addAll(title, table);
        return root;
    }


    private static List<ExpenseDto> fetchExpenses() {
        try {
            int userId = Session.getUserId();
            String endpoint = "/expenses/all/" + userId;

            String response = ApiClient.get(endpoint);

            if (response.contains("error")) {
                System.err.println("API Error: " + response);
                return new ArrayList<>();
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules(); // support LocalDateTime

            return mapper.readValue(response, new TypeReference<List<ExpenseDto>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    private static String getCategoryNameById(int id) {
        try {
            // Load categories only once
            if (cachedCategories == null) {
                String response = ApiClient.get("/categories");

                if (response.contains("error")) {
                    System.err.println("Failed to load categories: " + response);
                    return "Unknown";
                }

                ObjectMapper mapper = new ObjectMapper();
                cachedCategories = mapper.readValue(response, new TypeReference<List<CategoryDto>>() {});
            }

            // Find the category
            for (CategoryDto cat : cachedCategories) {
                if (cat.getCATEGORY_ID() == id) {
                    return cat.getNAME();

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Unknown";
    }


}
