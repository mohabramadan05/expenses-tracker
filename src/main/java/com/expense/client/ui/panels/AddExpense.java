package com.expense.client.ui.panels;

import com.expense.client.api.ApiClient;
import com.expense.client.session.Session;
import com.expense.common.dto.CategoryDto;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AddExpense {
    public static Pane loadAddExpensePage() {

        // Root container (Card Style)
        VBox card = new VBox(20);
        card.setPadding(new Insets(30));
        card.setStyle("-fx-background-color: white;");

        // Title
        Label title = new Label("Add New Expense");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Form Grid
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);

        // Category Dropdown
        Label lblCategory = new Label("Category");
        ComboBox<CategoryDto> categoryCombo = new ComboBox<>();
        categoryCombo.setPrefWidth(200);
        categoryCombo.setPromptText("Select Category");
        loadCategories(categoryCombo);


        // Amount Field
        Label lblAmount = new Label("Amount");
        TextField amountField = new TextField();
        amountField.setPromptText("0.00");
        amountField.setPrefWidth(150);

        // Date Picker
        Label lblDate = new Label("Date");
        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setPrefWidth(200);

        // Add to Grid
        grid.add(lblCategory, 0, 0);
        grid.add(categoryCombo, 0, 1);

        grid.add(lblAmount, 1, 0);
        grid.add(amountField, 1, 1);

        grid.add(lblDate, 2, 0);
        grid.add(datePicker, 2, 1);

        // Notes Area
        Label lblNotes = new Label("Notes / Description");
        TextArea notesArea = new TextArea();
        notesArea.setPromptText("Add a detailed description for this expense...");
        notesArea.setPrefHeight(120);
        notesArea.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

        // Submit Button
        Button submitBtn = new Button("Submit Expense");
        submitBtn.setMinWidth(300);
        submitBtn.setPadding(new Insets(12));
        submitBtn.setStyle(
                "-fx-background-color: #2196F3;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 6;" +
                        "-fx-font-size: 14;"
        );

        submitBtn.setOnAction(e -> {
            CategoryDto selected = categoryCombo.getValue();
            String amountStr = amountField.getText();
            LocalDate date = datePicker.getValue();
            String note = notesArea.getText();

            // Input validation
            if (selected == null || amountStr.isEmpty() || date == null) {
                showAlert("Please fill all required fields!");
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    showAlert("Amount must be positive!");
                    return;
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid amount format!");
                return;
            }

            ZonedDateTime zonedDateTime = date.atStartOfDay(ZoneOffset.UTC); // UTC timezone
            String dateCreated = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));


            // Build JSON payload
            String json = String.format(
                    "{ \"USER_ID\": %d, \"CATEGORY_ID\": %d, \"AMOUNT\": %.2f, \"NOTE\": \"%s\", \"DATE_CREATED\": \"%s\" }",
                    Session.getUserId(),
                    selected.getCATEGORY_ID(),
                    amount,
                    note.replace("\"", "\\\""), // escape quotes
                    dateCreated
            );

            // Run API call in a background thread
            new Thread(() -> {
                try {
                    String response = ApiClient.post("/expenses", json);

                    Platform.runLater(() -> {
                        if (response.contains("error")) {
                            showAlert("Failed to add expense: " + response);
                        } else {
                            showAlert("Expense added successfully!");
                            // Clear form
                            categoryCombo.getSelectionModel().clearSelection();
                            amountField.clear();
                            notesArea.clear();
                            datePicker.setValue(LocalDate.now());
                        }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Platform.runLater(() -> showAlert("Error connecting to server."));
                }
            }).start();
        });


        submitBtn.setOnMouseEntered(e -> submitBtn.setStyle(
                "-fx-background-color: #1976D2; -fx-text-fill: white;" +
                        "-fx-background-radius: 6; -fx-font-size: 14;"
        ));
        submitBtn.setOnMouseExited(e -> submitBtn.setStyle(
                "-fx-background-color: #2196F3; -fx-text-fill: white;" +
                        "-fx-background-radius: 6; -fx-font-size: 14;"
        ));

        // Layout Assembly
        VBox notesBox = new VBox(5, lblNotes, notesArea);

        card.getChildren().addAll(title, grid, notesBox, submitBtn);

        Pane pane = new Pane(card);
        pane.setPadding(new Insets(20));
        pane.setStyle("-fx-background-color: #fff;");

        return pane;
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }

    private static void loadCategories(ComboBox<CategoryDto> comboBox) {
        new Thread(() -> {
            try {
                String response = ApiClient.get("/categories");
                Gson gson = new Gson();
                CategoryDto[] categories = gson.fromJson(response, CategoryDto[].class);

                Platform.runLater(() -> {
                    comboBox.getItems().setAll(categories);
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    comboBox.getItems().clear();
                    comboBox.getItems().add(new CategoryDto() {
                        @Override public String toString() { return "Failed to load"; }
                    });
                });
            }
        }).start();
    }

}
