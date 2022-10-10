package com.example.flowershop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class flowershopController {

    public void addFlower() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(flowershopApplication.class.getResource("addFlowerDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 200, 200);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Flower");
        stage.show();
    }

    public void addBouquet() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(flowershopApplication.class.getResource("addBouquetDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 200, 200);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Bouquet");
        stage.show();
    }
}