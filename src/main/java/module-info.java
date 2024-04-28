module com.example.shifrhilla {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.shifrhilla to javafx.fxml;
    exports com.example.shifrhilla;
}