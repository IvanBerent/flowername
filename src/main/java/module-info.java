module com.example.flowershop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.flowershop to javafx.fxml;
    exports com.example.flowershop;
}