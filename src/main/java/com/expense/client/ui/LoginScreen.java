package com.expense.client.ui;

import com.expense.client.api.ApiClient;
import com.expense.client.session.Session;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginScreen {

    public void show(Stage stage) {

        // ---------- Logo ----------
        ImageView logoImageView = new ImageView(
                new Image(getClass().getResourceAsStream("/logo.png"))
        );
        logoImageView.setFitHeight(70);
        logoImageView.setPreserveRatio(true);

        // ---------- Header Text ----------
        Label titleLabel = new Label("Welcome Back!");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setTextFill(Color.web("#000"));

        Label subtitleLabel = new Label("Login to manage your expenses and budget");
        subtitleLabel.setFont(Font.font("Arial", 13));
        subtitleLabel.setTextFill(Color.gray(0.3));

        // ---------- Username Field ----------
        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(Font.font("Arial", 15));
        usernameLabel.setTextFill(Color.web("#000"));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setPrefWidth(280);

        VBox usernameBox = new VBox(5, usernameLabel, usernameField);
        usernameBox.setAlignment(Pos.CENTER_LEFT);

        // ---------- Password Field ----------
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Arial", 15));
        passwordLabel.setTextFill(Color.web("#000"));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefWidth(280);

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setFont(Font.font(13));
        errorLabel.setVisible(false);

        VBox passwordBox = new VBox(5, passwordLabel, passwordField,errorLabel);
        passwordBox.setAlignment(Pos.CENTER_LEFT);

        // ---------- Login Button ----------
        Button loginButton = new Button("Login");
        loginButton.setStyle(
                "-fx-background-color: #4285f4; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 10 25; " +
                        "-fx-font-size: 15px; " +
                        "-fx-background-radius: 10;"
        );
        loginButton.setPrefWidth(150);

        Button signupButton = new Button("Signup");
        signupButton.setStyle(
                "-fx-background-color: white;  " +
                        "-fx-text-fill: black; " +
                        "-fx-padding: 10 25; " +
                        "-fx-font-size: 15px; " +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-color: black;"
        );
        signupButton.setPrefWidth(150);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            errorLabel.setVisible(false);

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Please fill all fields.");
                errorLabel.setVisible(true);
                return;
            }

            // JSON Body
            String jsonBody = String.format(
                    "{\"username\":\"%s\", \"password\":\"%s\"}",
                    username, password
            );

            try {
                String response = ApiClient.post("/users/login", jsonBody);

                System.out.println("API Response: " + response);

                // Check for error
                if (response.contains("error")) {
                    errorLabel.setText("Login failed: " + response);
                    errorLabel.setVisible(true);
                    return;
                }

                // Parse JSON manually (simple way)
                int userId = Integer.parseInt(
                        response.split("\"userId\":")[1].split(",")[0].trim()
                );

                // Store in global session
                Session.setUserId(userId);

                // Open landing page
                new LandingPage().show(new Stage());
                stage.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                errorLabel.setText("Login failed: " +  ex.getMessage());
                errorLabel.setVisible(true);
            }
        });

        signupButton.setOnAction(e -> {
            new SignupScreen().show(new Stage());
            stage.close();
        });

        HBox buttonBox = new HBox(15, loginButton, signupButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        // ---------- Main Login Box ----------
        VBox loginBox = new VBox(15,
                logoImageView,
                titleLabel,
                subtitleLabel,
                usernameBox,
                passwordBox,
                buttonBox
        );

        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(30));
        loginBox.setMaxWidth(350);   // <-- Good size for form


        // ---------- Root Layout ----------
        VBox root = new VBox(loginBox);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #ffffff;");
        root.setPrefSize(1250, 705);

        // ---------- Scene ----------
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Expenses Tracker");
        stage.show();
    }
}
