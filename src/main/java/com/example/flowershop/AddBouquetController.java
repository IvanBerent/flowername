package com.example.flowershop;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddBouquetController {

    @FXML
    private TextField nameField;

    @FXML
    private Text nameText;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Text descText;

    @FXML
    private TextField purchasePriceField;

    @FXML
    private TextField sellPriceField;

    @FXML
    private Text sellPriceText;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox scrollPaneContent;

    @FXML
    private Button addFlowerBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<String> comboBox;

    boolean everythingCorrect;

    FlowershopDAO flowershopDAO = new FlowershopDAO();

    public void initialize(){

        // TODO combobox

        ObservableList<String> observableList = FXCollections.observableList(flowershopDAO.getFlowerNames());

        comboBox.setItems(observableList);
        comboBox.getSelectionModel().select(0);


        // regex for purchase price field allowing only one dot and only two numbers after dot
        purchasePriceField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*\\.?\\d{0,2}")){
                    purchasePriceField.setText(oldValue);
                }
            }
        });

        // regex for sell price field allowing only one dot and only two numbers after dot
        sellPriceField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*\\.?\\d{0,2}")){
                    sellPriceField.setText(oldValue);
                }
            }
        });

    }

    public void save(){
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String sellPrice = sellPriceField.getText();

        everythingCorrect = true;

        check(name, nameText);
        check(description, descText);
        check(sellPrice, sellPriceText);

        if (everythingCorrect){
            System.out.println("saved boquet");
        }

    }

    public void cancel() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    // maybe need to return hbox with the elements
    public void addBouquet(){
        scrollPaneContent.getChildren().remove(addFlowerBtn);
        HBox hBox = new HBox();
        hBox.setSpacing(20);

        ComboBox<String> comboBox = new ComboBox<>();

        ObservableList<String> observableList = FXCollections.observableList(flowershopDAO.getFlowerNames());

        comboBox.setItems(observableList);
        comboBox.getSelectionModel().select(0);

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        hBox.getChildren().addAll(comboBox, amountField);
        scrollPaneContent.getChildren().addAll(hBox, addFlowerBtn);
    }

    private void check(String input, Text text){
        if (input.equals("")) {
            text.setFill(Color.RED);
            everythingCorrect = false;
        } else {
            text.setFill(Color.BLACK);
        }
    }
}
