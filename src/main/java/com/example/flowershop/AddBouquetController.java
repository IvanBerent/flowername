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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private Button cancelBtn;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private FlowershopController flowershopController;

    boolean everythingCorrect;


    FlowershopDAO flowershopDAO = new FlowershopDAO();

    public void initialize(){

        // TODO combobox

        ObservableList<String> observableList = FXCollections.observableList(flowershopDAO.getFlowerNames());

        comboBox.setItems(observableList);
        comboBox.getSelectionModel().select(0);

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

        if (!everythingCorrect){
            return;
        }

        // remove button from node list
        int nodes = scrollPaneContent.getChildren().size();

        // content list [[flowername, amount]]
        List<List<String>> contentList = new ArrayList<>();
        scrollPaneContent.getChildren().remove(nodes - 1);
        // go through scrollpane content and get the content of the hboxes (String, String) -> (combobox, TextField)
        for (int i = 0; i < scrollPaneContent.getChildren().size(); i++){
            HBox hBox = (HBox) scrollPaneContent.getChildren().get(i);

            // get the values
            ComboBox<String> comboBox = (ComboBox<String>) hBox.getChildren().get(0);
            TextField amountField = (TextField) hBox.getChildren().get(1);

            String comboboxValue = comboBox.getValue();
            String amountText = amountField.getText();

            List<String> addElement = new ArrayList<>();
            addElement.add(comboboxValue);
            addElement.add(amountText);
            contentList.add(addElement);
        }
        scrollPaneContent.getChildren().add(addFlowerBtn);
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

        //todo regex only ints
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

    public void setController(FlowershopController flowershopController) {
        this.flowershopController = flowershopController ;
    }
}
