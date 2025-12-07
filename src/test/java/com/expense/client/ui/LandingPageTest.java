package com.expense.client.ui;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class LandingPageTest extends ApplicationTest {

    private LandingPage landingPage;

    @Override
    public void start(Stage stage) {
        landingPage = new LandingPage();
        landingPage.show(stage);
    }
    private boolean containsLabelText(Pane pane, String text) {
        for (javafx.scene.Node node : pane.getChildren()) {
            if (node instanceof Label label && label.getText().contains(text)) {
                return true;
            } else if (node instanceof Pane childPane) {
                if (containsLabelText(childPane, text)) return true;
            } else if (node instanceof VBox vbox) {
                if (containsLabelText(vbox, text)) return true;
            }
        }
        return false;
    }


    @Test
    void testDefaultPane() {
        StackPane content = landingPage.getContentPane();
        Pane currentPane = (Pane) content.getChildren().get(0);

        assertTrue(containsLabelText(currentPane, "Dashboard"),
                "Dashboard pane should be displayed");
    }

    @Test
    void testAddExpenseButtonNavigation() {
        clickOn("#addExpenseBtn");

        StackPane content = landingPage.getContentPane();
        Pane currentPane = (Pane) content.getChildren().get(0);

        assertTrue(containsLabelText(currentPane, "Add New Expense"),
                "Add Expense pane should be displayed");
    }


    @Test
    void testDashboardButtonNavigation() {
        clickOn("#dashboardBtn");

        StackPane content = landingPage.getContentPane();
        Pane currentPane = (Pane) content.getChildren().get(0);

        assertTrue(containsLabelText(currentPane, "Dashboard"),
                "Dashboard pane should be displayed");
    }

    @Test
    void testViewExpensesButtonNavigation() {
        clickOn("#viewExpensesBtn");


        StackPane content = landingPage.getContentPane();
        Pane currentPane = (Pane) content.getChildren().get(0);

        assertTrue(containsLabelText(currentPane, "Expense List"),
                "View Expenses pane should be displayed");

    }

    @Test
    void testManageBudgetsButtonNavigation() {
        clickOn("#manageBudgetBtn");

        StackPane content = landingPage.getContentPane();
        Pane currentPane = (Pane) content.getChildren().get(0);

        assertTrue(containsLabelText(currentPane, "Manage Budgets"),
                "Manage Budgets pane should be displayed");
    }
}
