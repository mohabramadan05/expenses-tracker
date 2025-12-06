package com.expense.client;

import com.expense.client.ui.LoginScreen;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.expense.server.Server.runServer;

public class MainClient extends Application {

    @Override
    public void start(Stage stage) {
        new LoginScreen().show(stage);
    }

    public static void main(String[] args) {
        runServer();
        launch(args);
    }
}
