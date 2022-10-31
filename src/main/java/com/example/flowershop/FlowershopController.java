package com.example.flowershop;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class FlowershopController {

    FlowershopDAO flowershopDAO = new FlowershopDAO();

    Flower flower;

    @FXML
    private Button changePerspectiveBtn;

    @FXML
    private TableView flowerTableView;

    @FXML
    private TableColumn<Integer, String> flowerIdCol;

    @FXML
    private TableColumn<Flower, String> flowerNameCol;

    @FXML
    private TableColumn<Flower, String> flowerDescriptionCol;

    @FXML
    private TableColumn<Flower, String> flowerPurchasePriceCol;

    @FXML
    private TableColumn<Flower, String> flowerSellPriceCol;

    @FXML
    private TableColumn<Flower, String> flowerAmountCol;

    @FXML
    private TableView bouquetTableView;

    @FXML
    private TableColumn<Integer, String> bouquetIdCol;

    @FXML
    private TableColumn<Bouquet, String> bouquetNameCol;

    @FXML
    private TableColumn<Bouquet, String> bouquetDescriptionCol;

    @FXML
    private TableColumn<Bouquet, String> bouquetSellPriceCol;

    @FXML
    private TableColumn<Bouquet, String> bouquetAmountCol;


    public void initialize() {
        flowerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        flowerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        flowerDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        flowerPurchasePriceCol.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        flowerSellPriceCol.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        flowerAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        ObservableList<Flower> flowers = FXCollections.observableList(flowershopDAO.queryAllFlowers());
        flowerTableView.setItems(flowers);

        bouquetIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        bouquetNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        bouquetDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        bouquetSellPriceCol.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        bouquetAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        ObservableList<Bouquet> bouquets = FXCollections.observableList(flowershopDAO.queryAllBouquets());
        bouquetTableView.setItems(bouquets);

        flowerTableView.setRowFactory(event ->{
            TableRow<Flower> row = new TableRow<>();
            row.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2) {
                    try {
                        flower = row.getItem();
                        updateFlower(flower);
                    } catch (NullPointerException | IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
            return row;
        });

    }

    public void addFlower() throws IOException {
        /*
        FXMLLoader fxmlLoader = new FXMLLoader(flowershopApplication.class.getResource("addFlowerDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Flower");
        stage.show();
         */

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addFlowerDialog.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        AddFlowerController addFlowerController = fxmlLoader.<AddFlowerController>getController();
        addFlowerController.setController(this);
        Scene scene = new Scene(root, 500, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Flower");
        stage.show();
    }

    public void addBouquet() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(flowershopApplication.class.getResource("addBouquetDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Bouquet");
        stage.show();
    }

    public void updateFlower(Flower flower) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("updateFlowerDialog.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        UpdateFlowerController updateFlowerController = fxmlLoader.<UpdateFlowerController>getController();
        updateFlowerController.setId(flower.getId());
        updateFlowerController.setName(flower.getName());
        updateFlowerController.setAmount(flower.getAmount());
        updateFlowerController.setController(this);
        Scene scene = new Scene(root, 500, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Update Flower");
        stage.show();

    }

    public void changePerspective() {
        // TODO: switch table entries view
        changePerspectiveBtn.setOnMouseClicked(event -> {
            // switch text of button
            String buttonText = changePerspectiveBtn.getText();
            if (buttonText.equals("I am a customer")) {
                changePerspectiveBtn.setText("I am a seller");
            } else {
                changePerspectiveBtn.setText("I am a customer");
            }
        });
    }

}