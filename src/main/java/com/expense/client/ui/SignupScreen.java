package com.expense.client.ui;

import com.expense.client.api.ApiClient;
import com.expense.client.api.ApiService;
import com.expense.client.api.ApiServiceImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SignupScreen {

    private final ApiService apiService;

    public SignupScreen(ApiService apiService) {
        this.apiService = apiService;
    }

    public SignupScreen() {
        this.apiService = new ApiServiceImpl(); // real implementation
    }


    public void show(Stage stage) {

        // ---------- LEFT SIGNUP CARD ----------
        ImageView logoIcon = new ImageView(new Image(
                getClass().getResourceAsStream("/logo.png")));
        logoIcon.setFitHeight(50);
        logoIcon.setPreserveRatio(true);

        Label subtitle = new Label("Start managing your money like a boss.");
        subtitle.setFont(Font.font("Arial", 14));
        subtitle.setTextFill(Color.gray(0.35));

        // Form fields
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.setPrefWidth(310);

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setPrefWidth(310);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Create a password");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm your password");

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setFont(Font.font(13));
        errorLabel.setVisible(false);

        VBox formFields = new VBox(12,
                new Label("Full Name"), nameField,
                new Label("Email Address"), emailField,
                new Label("Password"), passwordField,
                new Label("Confirm Password"), confirmPasswordField,
                errorLabel
        );

        formFields.getChildren().forEach(node -> {
            if (node instanceof Label lbl) {
                lbl.setFont(Font.font("Arial", 14));
                lbl.setTextFill(Color.web("#000"));
            }
            if (node instanceof TextField tf) {
                tf.setPrefWidth(310);
            }
        });

        Button createAccountButton = new Button("Create Account");
        createAccountButton.setStyle(
                "-fx-background-color: #4285f4;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 10 15;"
        );
        createAccountButton.setPrefWidth(310);

        createAccountButton.setOnAction(e -> {

            String fullName = nameField.getText();
            String username = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            errorLabel.setVisible(false);

            // Validate fields
            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                errorLabel.setText("Please fill all fields.");
                errorLabel.setVisible(true);
                return;
            }

            if (!password.equals(confirmPassword)) {
                errorLabel.setText("Passwords do not match!");
                errorLabel.setVisible(true);
                return;
            }

            // Prepare JSON
            String jsonBody = String.format(
                    "{\"fullName\":\"%s\", \"username\":\"%s\", \"password\":\"%s\"}",
                    fullName, username, password
            );

            try {
                String response = apiService.signup(fullName, username, password);
                System.out.println("Signup API Response: " + response);

                if (response.contains("error")) {
                    errorLabel.setText("Signup failed: " + response);
                    errorLabel.setVisible(true);
                    return;
                }

                // SUCCESS → Redirect to login
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Account Created");
                alert.setHeaderText("Signup Successful!");
                alert.setContentText("You can now login with your credentials.");
                alert.showAndWait();

                new LoginScreen().show(new Stage());
                stage.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                errorLabel.setText("Error connecting to server!");
                errorLabel.setVisible(true);
            }
        });

        // OR Divider
        Label orLabel = new Label("OR");
        orLabel.setTextFill(Color.gray(0.5));

        Separator sep1 = new Separator();
        Separator sep2 = new Separator();

        HBox orBox = new HBox(sep1, orLabel, sep2);
        orBox.setSpacing(10);
        orBox.setAlignment(Pos.CENTER);

        Button googleButton = new Button("Sign in");
        googleButton.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #d1d1d1;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;" +
                        "-fx-padding: 10 15;"
        );
        googleButton.setPrefWidth(310);

        googleButton.setOnAction(
                event -> {
                    new LoginScreen().show(new Stage());
                    stage.close();
                }
        );

        VBox signupCard = new VBox(20, logoIcon, subtitle, formFields,
                createAccountButton, orBox, googleButton);
        signupCard.setPadding(new Insets(30));
        signupCard.setPrefWidth(380);

        // ---------- IMAGE ----------
        ImageView illustration = new ImageView(
                new Image(getClass().getResourceAsStream("/ill.png"))
        );
        illustration.setFitWidth(430);
        illustration.setPreserveRatio(true);

        HBox centerLayout = new HBox(160, signupCard, illustration);
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setPadding(new Insets(90, 40, 40, 40));

        nameField.setId("nameField");
        emailField.setId("emailField");
        passwordField.setId("passwordField");
        confirmPasswordField.setId("confirmPasswordField");
        createAccountButton.setId("createAccountButton");
        errorLabel.setId("errorLabel");

        VBox root = new VBox(centerLayout);
        root.setStyle("-fx-background-color: #ffffff;");
        root.setPrefSize(1250, 720);

        stage.setScene(new Scene(root));
        stage.setTitle("Expense Tracker");
        stage.show();
    }
}
