package com.example.akaliautomaton;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class MainFrame extends Application {

    private TextField tfEntry;

    @Override
    public void start(Stage primaryStage) {
        Label lbEntry = new Label("Ingresa variable");
        tfEntry = new TextField();

        Button btnCheck = getButton();

        VBox mainVBox = new VBox(10);
        mainVBox.setStyle("-fx-background-color: #8080FF");
        mainVBox.getChildren().addAll(lbEntry, tfEntry, btnCheck);

        Scene scene = new Scene(mainVBox, 300, 200);

        primaryStage.setTitle("Automaton");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button getButton() {
        Button btnCheck = new Button("Validar");
        btnCheck.setOnAction(e -> {
            String entry = tfEntry.getText();
            String message;
            String formattedCases = "";
            StringBuilder concatenatedCases = new StringBuilder();
            ValidationResult validate = new Automaton().validateVariableExpression(entry);
            List<String> listCases = validate.getCases();

            if (validate.isValid()) {
                message = "Cadena válida";
                for (String state : listCases) {
                    concatenatedCases.append(state);
                    concatenatedCases.append(" -> ");
                }
                formattedCases = concatenatedCases.toString();
            } else {
                for (String state : listCases) {
                    concatenatedCases.append(state);
                    concatenatedCases.append(" -> ");
                }
                formattedCases = concatenatedCases.toString();
                message = "Cadena inválida";
            }
            showAlert("Resultado", message);
            showAlert("Estados", formattedCases);
        });
        return btnCheck;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

