module com.example.akaliautomaton {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.akaliautomaton to javafx.fxml;
    exports com.example.akaliautomaton;
}