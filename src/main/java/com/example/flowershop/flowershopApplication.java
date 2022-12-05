package com.example.flowershop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class flowershopApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FlowershopDAO flowershopDAO = new FlowershopDAO();
        flowershopDAO.FlowershopDAO();

        //flowershopDAO.addFlowerColumnToBouquetTable("kamille");
        //flowershopDAO.addFlowerColumnToBouquetTable("löwenzahn");
        //flowershopDAO.deleteFlowerColumnFromBouquetTable("Rose");
        //flowershopDAO.getBouquet(1);

        //flowershopDAO.insertBouquet();

        //flowershopDAO.clearFlowerTable();
        //flowershopDAO.clearBouquetTable();
        FXMLLoader fxmlLoader = new FXMLLoader(flowershopApplication.class.getResource("flowershopView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 700);
        stage.setTitle("Flowershop");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}