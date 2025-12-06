package com.expense.client.ui.panels;

import com.expense.client.api.ApiClient;
import com.expense.client.session.Session;
import com.expense.common.dto.BudgetReport2Dto;
import com.expense.common.dto.BudgetReportDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class Dashboard {
    public static Pane loadDashboardPage() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20, 30, 20, 30));
        root.setStyle("-fx-background-color: #fff;");

        Label title = new Label("Dashboard");
        title.setFont(Font.font("Arial", 26));
        title.setStyle("-fx-font-weight: bold;");
        title.setTextFill(Color.web("#000"));

        HBox summaryBox = new HBox(20);
        summaryBox.setAlignment(Pos.CENTER_LEFT);

        List<BudgetReport2Dto> reports2 = fetchBudgetReports2();

        if (reports2.isEmpty()) {
            Label emptyLabel = new Label("No budget reports available.");
            emptyLabel.setFont(Font.font(16));
            emptyLabel.setTextFill(Color.GRAY);
            root.getChildren().addAll(title, emptyLabel);
        } else {
            VBox spentCard = null;
            VBox remainingCard = null;
            for (BudgetReport2Dto r : reports2) {
                spentCard = createSummaryCard(
                        "Total Spent This Month",
                        "$" + r.getSpent_this_month(),
                        "Your spending is on track.",
                        "#000"
                );
                remainingCard = createSummaryCard(
                        "Remaining Budget",
                        "$" + r.getRemaining_budget(),
                        "You're within budget.",
                        "#1aa85b"
                );
            }


            summaryBox.getChildren().addAll(spentCard, remainingCard);

        }


        // ---------- FETCH DATA ----------
        List<BudgetReportDto> reports = fetchBudgetReports();

        if (reports.isEmpty()) {
            Label emptyLabel = new Label("No budget reports available.");
            emptyLabel.setFont(Font.font(16));
            emptyLabel.setTextFill(Color.GRAY);
            root.getChildren().addAll(title, emptyLabel);
        } else {
            // ---------- CATEGORY GRID ----------
            GridPane categoryGrid = new GridPane();
            categoryGrid.setHgap(20);
            categoryGrid.setVgap(20);

            int col = 0, row = 0;
            for (BudgetReportDto r : reports) {
                VBox card = createCategoryCard(
                        r.getCategoryName(),
                        (int) r.getSpent(),
                        (int) r.getBudget(),
                        r.getStatus()
                );
                categoryGrid.add(card, col, row);
                col++;
                if (col > 2) {
                    col = 0;
                    row++;
                }
            }

            Label catTitle = new Label("Category Budgets");
            catTitle.setFont(Font.font("Arial", 20));
            catTitle.setStyle("-fx-font-weight: bold;");
            catTitle.setPadding(new Insets(10, 0, 0, 0));

            root.getChildren().addAll(title, summaryBox, catTitle, categoryGrid);
        }

        Pane pane = new Pane(root);
        pane.setStyle("-fx-background-color: white;");
        return pane;
    }

    private static VBox createCategoryCard(String category, int spent, int total, String tagType) {
        Label catLabel = new Label(category);
        catLabel.setFont(Font.font("Arial", 14));
        catLabel.setTextFill(Color.web("#000"));

        Label tagLabel = new Label(tagType);
        tagLabel.setFont(Font.font(13));
        tagLabel.setTextFill(Color.WHITE);

        switch (tagType) {
            case "Safe" ->
                    tagLabel.setStyle("-fx-background-color: #33f557; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 3 10;");
            case "Warning" ->
                    tagLabel.setStyle("-fx-background-color: #ddd32f; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 3 10;");
            case "Danger" ->
                    tagLabel.setStyle("-fx-background-color: #f53354; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 3 10;");
            default ->
                    tagLabel.setStyle("-fx-background-color: gray; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 3 10;");
        }

        HBox topRow = new HBox(catLabel, tagLabel);
        topRow.setSpacing(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label amountLabel = new Label("$" + spent + " / $" + total);
        amountLabel.setFont(Font.font("Arial", 20));
        amountLabel.setStyle("-fx-font-weight: bold;");

        double percent = total > 0 ? Math.min(1.0 * spent / total, 1.0) : 0.0;
        ProgressBar bar = new ProgressBar(percent);
        bar.setPrefWidth(260);
        bar.setStyle("-fx-accent: #2b92ff;");

        Label percentLabel = new Label((int) (percent * 100) + "% of budget spent");
        percentLabel.setFont(Font.font(12));
        percentLabel.setTextFill(Color.gray(0.45));

        VBox card = new VBox(8, topRow, amountLabel, bar, percentLabel);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 10, 0, 0, 4);");
        card.setPrefSize(300, 150);

        return card;
    }

    private static VBox createSummaryCard(String title, String value, String subtitle, String valueColor) {
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", 15));
        titleLabel.setTextFill(Color.gray(0.35));
        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Arial", 30));
        valueLabel.setTextFill(Color.web(valueColor));
        Label descLabel = new Label(subtitle);
        descLabel.setFont(Font.font("Arial", 13));
        descLabel.setTextFill(Color.gray(0.45));
        VBox box = new VBox(5, titleLabel, valueLabel, descLabel);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: white;" + "-fx-background-radius: 12;" + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 10, 0, 0, 4);");
        box.setPrefSize(455, 150);
        return box;
    }

    private static List<BudgetReportDto> fetchBudgetReports() {
        try {
            String jsonBody = "{\"userId\":" + Session.getUserId() + "}";
            String response = ApiClient.post("/reports", jsonBody);

            if (response.contains("error")) {
                System.err.println("API error: " + response);
                return new ArrayList<>();
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response, new TypeReference<List<BudgetReportDto>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static List<BudgetReport2Dto> fetchBudgetReports2() {
        try {
            String jsonBody = "{\"userId\":" + Session.getUserId() + "}";
            String response = ApiClient.post("/reports2", jsonBody);

            if (response.contains("error")) {
                System.err.println("API error: " + response);
                return new ArrayList<>();
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response, new TypeReference<List<BudgetReport2Dto>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
