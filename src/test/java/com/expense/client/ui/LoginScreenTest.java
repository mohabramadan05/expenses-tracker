package com.expense.client.ui;

import com.expense.client.api.ApiService;
import com.expense.client.session.Session;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class LoginScreenTest {

    @Start
    private void start(Stage stage) {
        new LoginScreen(mock(ApiService.class)).show(stage);
    }

    @Test
    void testEmptyFieldsShowsError(FxRobot robot) {
        robot.clickOn("#loginButton");

        Label error = robot.lookup("#errorLabel").queryAs(Label.class);

        assertTrue(error.isVisible());
        assertEquals("Please fill all fields.", error.getText());
    }

    @Test
    void testWrongLogin(FxRobot robot) throws Exception {
        ApiService api = mock(ApiService.class);
        when(api.login("mohab", "wrong")).thenReturn("{\"error\":\"Invalid\"}");

        robot.clickOn("#usernameField").write("mohab");
        robot.clickOn("#passwordField").write("wrong");
        robot.clickOn("#loginButton");

        Label error = robot.lookup("#errorLabel").queryAs(Label.class);

        assertTrue(error.isVisible());
        assertTrue(error.getText().contains("failed"));
    }

    @Test
    void testSuccessfulLogin(FxRobot robot) throws Exception {

        // Arrange ---- mock API return
        ApiService api = mock(ApiService.class);
        when(api.login("Mohab", "1"))
                .thenReturn("{\"userId\": 23, \"username\": \"Mohab\"}");

        // Build screen with mocked API
        LoginScreen screen = new LoginScreen(api);

        // Start UI
        FxToolkit.setupFixture(() -> {
            Stage stage = new Stage();
            screen.show(stage);
        });

        // Act ---- simulate user typing
        robot.clickOn("#usernameField").write("Mohab");
        robot.clickOn("#passwordField").write("1");
        robot.clickOn("#loginButton");

        // Assert ---- userId stored in session
        assertEquals(23, Session.getUserId());
    }


}
