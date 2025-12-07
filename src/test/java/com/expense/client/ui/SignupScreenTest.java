package com.expense.client.ui;

import com.expense.client.api.ApiService;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class SignupScreenTest {

    private ApiService api;

    @Start
    private void start(Stage stage) {
        api = mock(ApiService.class);

        SignupScreen screen = new SignupScreen(api);
        screen.show(stage);
    }

    @Test
    void testSignupSuccess(FxRobot robot) throws Exception {

        when(api.signup("Mohab", "mohab@gmail.com", "123"))
                .thenReturn("{\"success\": true}");

        robot.clickOn("#nameField").write("Mohab");
        robot.clickOn("#emailField").write("mohab@gmail.com");
        robot.clickOn("#passwordField").write("123");
        robot.clickOn("#confirmPasswordField").write("123");
        robot.clickOn("#createAccountButton");

        // Verify API called correctly
        verify(api, times(1)).signup("Mohab", "mohab@gmail.com", "123");
    }

    @Test
    void testEmptyFields(FxRobot robot) {
        robot.clickOn("#createAccountButton");

        Label error = robot.lookup("#errorLabel").queryAs(Label.class);
        assertTrue(error.isVisible());
        assertEquals("Please fill all fields.", error.getText());
    }

    @Test
    void testPasswordMismatch(FxRobot robot) {
        robot.clickOn("#nameField").write("Mohab");
        robot.clickOn("#emailField").write("m@gmail.com");
        robot.clickOn("#passwordField").write("111");
        robot.clickOn("#confirmPasswordField").write("222");

        robot.clickOn("#createAccountButton");

        Label error = robot.lookup("#errorLabel").queryAs(Label.class);

        assertTrue(error.isVisible());
        assertEquals("Passwords do not match!", error.getText());
    }

}
