package com.example.flowershop;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class UpdateFlowerController {

    @FXML
    public Text flowerText;

    @FXML
    private TextField amountField;

    @FXML
    private Button cancelBtn;

    @FXML
    private FlowershopController flowershopController;

    int id;
    String name;
    int amount;

    @FXML
    private void initialize(){
        // avoid nullPointerException
        Platform.runLater(() -> {
            flowerText.setText(this.name);
            amountField.setText(Integer.toString(this.amount));

            // todo regex for amountField (digits only)
        });
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setAmount(int amount){
        this.amount = amount;
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

    @FXML
    private void cancel(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void update() {
        FlowershopDAO flowershopDAO = new FlowershopDAO();
        flowershopDAO.updateFlower(id, Integer.parseInt(amountField.getText()));
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
        this.flowershopController.initialize();
    }

    public void setController(FlowershopController flowershopController) {
        this.flowershopController = flowershopController ;
    }
}
