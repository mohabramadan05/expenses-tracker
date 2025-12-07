package com.expense.client.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static com.expense.client.ui.panels.AddExpense.loadAddExpensePage;
import static com.expense.client.ui.panels.Dashboard.loadDashboardPage;
import static com.expense.client.ui.panels.Expenses.loadViewExpensesPage;
import static com.expense.client.ui.panels.ManageBudgets.loadManageBudgetsPage;

public class LandingPage {

    private Button dashboardBtn;
    private Button addExpenseBtn;
    private Button viewExpensesBtn;
    private Button manageBudgetBtn;
    private StackPane contentPane;

    public void show(Stage stage) {
        // ---------- LEFT NAVIGATION ----------
        dashboardBtn = createNavButton("Dashboard");
        addExpenseBtn = createNavButton("Add Expense");
        viewExpensesBtn = createNavButton("View Expenses");
        manageBudgetBtn = createNavButton("Manage Budgets");

        // Set fx:id for TestFX
        dashboardBtn.setId("dashboardBtn");
        addExpenseBtn.setId("addExpenseBtn");
        viewExpensesBtn.setId("viewExpensesBtn");
        manageBudgetBtn.setId("manageBudgetBtn");

        VBox navBar = new VBox(15, dashboardBtn, addExpenseBtn, viewExpensesBtn, manageBudgetBtn);
        navBar.setPadding(new Insets(30, 15, 30, 15));
        navBar.setPrefWidth(200);
        navBar.setStyle("-fx-background-color: #fafafa;");

        // ---------- CONTENT AREA ----------
        contentPane = new StackPane();
        contentPane.setPadding(new Insets(20));
        contentPane.setStyle("-fx-background-color: #fff;");

        // Default section: Dashboard
        highlightButton(dashboardBtn);
        contentPane.getChildren().setAll(loadDashboardPage());

        // Navigation actions
        dashboardBtn.setOnAction(e -> navigateTo(dashboardBtn, loadDashboardPage()));
        addExpenseBtn.setOnAction(e -> navigateTo(addExpenseBtn, loadAddExpensePage()));
        viewExpensesBtn.setOnAction(e -> navigateTo(viewExpensesBtn, loadViewExpensesPage()));
        manageBudgetBtn.setOnAction(e -> navigateTo(manageBudgetBtn, loadManageBudgetsPage()));

        // ---------- MAIN LAYOUT ----------
        HBox root = new HBox(navBar, contentPane);
        root.setStyle("-fx-background-color: #fff;");
        HBox.setHgrow(contentPane, Priority.ALWAYS);

        Scene scene = new Scene(root, 1250, 720);
        stage.setScene(scene);
        stage.setTitle("Expenses Tracker");
        stage.show();
    }

    private void navigateTo(Button button, Pane page) {
        highlightButton(button);
        contentPane.getChildren().setAll(page);
    }

    // ---------- NAV BUTTON CREATION ----------
    private Button createNavButton(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font(16));
        btn.setTextFill(Color.WHITE);
        btn.setPrefWidth(180);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setStyle(defaultButtonStyle());
        return btn;
    }

    // ---------- ACTIVE HIGHLIGHT LOGIC ----------
    private void highlightButton(Button activeBtn) {
        dashboardBtn.setStyle(defaultButtonStyle());
        addExpenseBtn.setStyle(defaultButtonStyle());
        viewExpensesBtn.setStyle(defaultButtonStyle());
        manageBudgetBtn.setStyle(defaultButtonStyle());

        activeBtn.setStyle("-fx-background-color: #3da8f5; -fx-text-fill: #fff; -fx-padding: 10 15; -fx-background-radius: 8;");
    }

    private String defaultButtonStyle() {
        return "-fx-background-color: transparent; -fx-text-fill: grey; -fx-padding: 10 15; -fx-background-radius: 8;";
    }

    // ---------- GETTER FOR TESTFX ----------
    public StackPane getContentPane() {
        return contentPane;
    }
}
