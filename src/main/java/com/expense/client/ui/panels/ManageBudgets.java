package com.expense.client.ui.panels;

import com.expense.client.api.ApiClient;
import com.expense.client.session.Session;
import com.expense.common.dto.BudgetDto;
import com.expense.common.dto.CategoryDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ManageBudgets {
    private static List<CategoryDto> cachedCategories = null;

    public static Pane loadManageBudgetsPage() {

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));

        Label title = new Label("Manage Budgets");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));


        TableView<BudgetDto> table = new TableView<>();
        table.setPrefHeight(600);

        // ---- Columns ----
        TableColumn<BudgetDto, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        getCategoryNameById(data.getValue().getCATEGORY_ID())
                )
        );

        TableColumn<BudgetDto, Double> amountCol = new TableColumn<>("Budget Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("AMOUNT"));

        // ---- Action Column ----
        TableColumn<BudgetDto, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(col -> new TableCell<>() {

            private final Button editBtn = new Button("✎");

            {
                editBtn.setStyle(
                        "-fx-background-color: #4CAF50; -fx-text-fill:white; -fx-cursor: hand; -fx-padding:4;"
                );
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    BudgetDto budget = getTableView().getItems().get(getIndex());

                    editBtn.setOnAction(e -> openBudgetEditDialog(budget, table));
                    setGraphic(editBtn);
                }
            }
        });

        table.getColumns().addAll(categoryCol, amountCol, actionCol);

        // ---- Load Data ----
        table.getItems().setAll(fetchBudgets(Session.getUserId()));

        root.getChildren().addAll(title, table);
        return root;
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

    private static void openBudgetEditDialog(BudgetDto budget, TableView<BudgetDto> table) {

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Edit Budget");

        VBox box = new VBox(15);
        box.setPadding(new Insets(20));

        Label label = new Label("Update Budget");
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        List<CategoryDto> categories = fetchCategories();
        ComboBox<CategoryDto> categoryBox = new ComboBox<>();

        categoryBox.getItems().addAll(categories);

        categoryBox.setValue(
                categories.stream()
                        .filter(c -> c.getCATEGORY_ID() == budget.getCATEGORY_ID())
                        .findFirst().orElse(null)
        );





        TextField amountField = new TextField(String.valueOf(budget.getAMOUNT()));

        Button updateBtn = new Button("Update");
        updateBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        updateBtn.setPrefWidth(120);

        updateBtn.setOnAction(e -> {
            try {
                double newAmount = Double.parseDouble(amountField.getText());
                int newCategoryId = categoryBox.getValue().getCATEGORY_ID();

                sendBudgetUpdate(budget, newCategoryId, newAmount);

                table.getItems().setAll(fetchBudgets(Session.getUserId()));
                popup.close();

            } catch (Exception ex) {
                showAlert("Error"+ "Invalid amount.");
            }
        });

        box.getChildren().addAll(label, categoryBox, amountField, updateBtn);

        Scene scene = new Scene(box, 350, 250);
        popup.setScene(scene);
        popup.show();
    }

    private static List<CategoryDto> fetchCategories() {
        try {
            String response = ApiClient.get("/categories");
            return new Gson().fromJson(
                    response,
                    new TypeToken<List<CategoryDto>>(){}.getType()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static List<BudgetDto> fetchBudgets(int userId) {
        try {
            String response = ApiClient.get("/budgets/all/" + userId);

            return new Gson().fromJson(
                    response,
                    new TypeToken<List<BudgetDto>>(){}.getType()
            );

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error" + "Failed to load budgets.");
            return new ArrayList<>();
        }
    }

    private static void sendBudgetUpdate(BudgetDto b, int newCategoryId, double newAmount) {
        try {

            String jsonBody = String.format(
                    "{ \"USER_ID\": %d, \"CATEGORY_ID\": %d, \"AMOUNT\": %.2f }",
                    b.getUSER_ID(),
                    newCategoryId,
                    newAmount
            );

            String response = ApiClient.put("/budgets/" + b.getBUDGET_ID(), jsonBody);

            System.out.println("Update Response: " + response);

            if (!response.contains("successfully")) {
                showAlert("Update Failed" + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error" + "Failed to update budget.");
        }
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }



}
