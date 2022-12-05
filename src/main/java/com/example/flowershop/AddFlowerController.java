package com.example.flowershop;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class AddFlowerController {

    @FXML
    private Text nameText;

    @FXML
    private TextField nameField;

    @FXML
    private Text descriptionText;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Text purchasePriceText;

    @FXML
    private TextField purchasePriceField;

    @FXML
    private Text sellPriceText;

    @FXML
    private TextField sellPriceField;

    @FXML
    private TextField amountField;

    @FXML
    private FlowershopController flowershopController;

    @FXML
    private Button cancelBtn;

    boolean everythingCorrect;

    public void initialize(){

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

        // regex for amountField allowing only digits
        amountField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")){
                    amountField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    private void save(){
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String purchasePrice = purchasePriceField.getText();
        String sellPrice = sellPriceField.getText();
        String amountInput = amountField.getText();

        everythingCorrect = true;

        // check for empty fields
        check(name, nameText);
        check(description, descriptionText);
        check(purchasePrice, purchasePriceText);
        check(sellPrice, sellPriceText);

        // something is not entered
        if (!everythingCorrect) {
            return;
        } else {
            // check for sell price 20% above purchase price
            float purchasePriceFloat = Float.parseFloat(purchasePriceField.getText());
            float sellPriceFloat = Float.parseFloat(sellPriceField.getText());

            purchasePriceFloat = purchasePriceFloat * 0.2f + purchasePriceFloat;

            if (!(sellPriceFloat >= purchasePriceFloat)) {
                showAlert(purchasePriceFloat);
                return;
            }
        }

        int amount;
        if (amountInput.equals("")) {
            amount = 0;
        } else {
            amount = Integer.parseInt(amountInput);
        }

        // TODO make regex for .xx ,  x  , .  ,  x.
        Flower flower = new Flower(name, description, Float.parseFloat(purchasePrice), Float.parseFloat(sellPrice), amount);

        FlowershopDAO flowershopDAO = new FlowershopDAO();
        flowershopDAO.insertFlower(flower);
        //flowershopDAO.addFlowerColumnToCompositionsTable(flower.getName());
        this.flowershopController.initialize();

        System.out.println("saved flower");
    }

    @FXML
    private void cancel(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void plus() {
        int amount;
        if (amountField.getText().equals("")){
            amount = 0;
        } else {
            amount = Integer.parseInt(amountField.getText());
        }
        amount = amount + 1;
        amountField.setText(Integer.toString(amount));
    }

    @FXML
    private void minus() {
        int amount;
        if (amountField.getText().equals("")){
            amountField.setText("0");
        } else {
            amount = Integer.parseInt(amountField.getText());
            if (amount > 0) {
                amount = amount - 1;
                amountField.setText(Integer.toString(amount));
            }
        }
    }

    private void check(String input, Text text){
        if (input.equals("")) {
            text.setFill(Color.RED);
            everythingCorrect = false;
        } else {
            text.setFill(Color.BLACK);
        }
    }

    public void setController(FlowershopController flowershopController) {
        this.flowershopController = flowershopController ;
    }

    private void showAlert(float price){
        DecimalFormat df = new DecimalFormat("0.00");

        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.getButtonTypes().add(ok);
        alert.setTitle("Invalid sell price");
        alert.setContentText("Sell price has to be at least 20% above purchase price.\nMinimum for this flower" +
                " is currently " + df.format(price) + "€");
        alert.showAndWait();
    }
}
