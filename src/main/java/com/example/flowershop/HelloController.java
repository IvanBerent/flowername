package com.example.flowershop;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        System.out.println("Hallo Welt");
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}