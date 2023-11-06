package com.example.akaliautomaton;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

public class MainFrame extends Application {

    public Button variables;
    public Button validate;
    public Button conditional;
    public Button loop;
    public Button functions;
    public Label result;
    public Label structure;
    public TextArea code;

    public String type;

    private TextField tfEntry;


    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainFrame.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setTitle("Language");
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    @FXML
    void grammarTypeValidate(ActionEvent event) {
        Node actionButton = (Node) event.getSource();
        String idButtonAction = actionButton.getId();
        String example = "";

        if (!idButtonAction.equals("validate")){
            functions.setStyle("-fx-background-color: #220070;");
            loop.setStyle("-fx-background-color: #220070;");
            conditional.setStyle("-fx-background-color: #220070;");
            variables.setStyle("-fx-background-color: #220070;");
            structure.setText(example);
        }

        switch (idButtonAction) {
            case "functions" -> {
                functions.setStyle("-fx-background-color: #890E00; -fx-text-fill: white");
                example = """
                        TD NF <> =>             NF <> =>            NF < PA > =>                     NF = nombre de funcion
                            cnd                             cnd                     cnd                                 PA = parametro (V)
                            rtn  v           ó       <=              ó        <=                                       rtn =  return
                        <=                                                                                                      V = nombre de variable
                        """;
                structure.setText(example);
                type = "functions";
            }
            case "loop" -> {
                loop.setStyle("-fx-background-color: #890E00; -fx-text-fill: white");
                example = """
                        for ( D RD | D RD | O ) =>        D = 0 ... 9\s
                            cnd                                           RD = D RD
                        <=                                                 O = + | -""";
                structure.setText(example);
                type = "loop";
            }
            case "variables" -> {
                variables.setStyle("-fx-background-color: #890E00; -fx-text-fill: white");
                example = """
                        TD V = VA ó TD V           TD = (Ent | Bool | Cdn | Dcm)
                                                                  V = nombre de variable
                                                                  VA = valor (10, 1.5, "hola", T | F)""";
                structure.setText(example);
                type = "variables";
            }
            case "conditional" ->{
                conditional.setStyle("-fx-background-color: #890E00; -fx-text-fill: white");
                example = """
                        if ( VA S VA | V S VA | VA S V | V S V ) =>         VA = valor (10, 1.5, "hola", T | F)
                            cnd                                                                        V = nombre de variable
                        <=                                                                              S = simbolos (==, <=, >=, !=)""";
                structure.setText(example);
                type = "conditional";
            }
            case "validate" -> {
                validate.setOnMousePressed(e -> {
                    validate.setStyle("-fx-background-color: #003E07; -fx-text-fill: white;");
                });
                validate.setStyle("-fx-background-color: #ffffff;");
                System.out.println();
                Automaton automaton = new Automaton();
                ValidationResult validationResult = automaton.validateLanguage(code.getText(), type);
                List<String> cases = validationResult.cases();
                if (validationResult.isValid()) {
                    result.setText("Codigo válido");
                } else {
                    String caseError = "";
                    for (String state : cases) {
                        caseError = state;
                    }
                    result.setText("Cadena inválida " + caseError);
                }
            }
        }
    }

//    private Button getButton() {
//        Button btnCheck = new Button("Validar");
//        btnCheck.setOnAction(e -> {
//            String entry = tfEntry.getText();
//            String message;
//            String formattedCases = "";
//            StringBuilder concatenatedCases = new StringBuilder();
//            ValidationResult validate = new Automaton().validateVariableExpression(entry);
//            List<String> listCases = validate.cases();
//
//            if (validate.isValid()) {
//                message = "Cadena válida";
//                for (String state : listCases) {
//                    concatenatedCases.append(state);
//                    concatenatedCases.append(" -> ");
//                }
//                formattedCases = concatenatedCases.toString();
//            } else {
//                for (String state : listCases) {
//                    concatenatedCases.append(state);
//                    concatenatedCases.append(" -> ");
//                }
//                formattedCases = concatenatedCases.toString();
//                message = "Cadena inválida";
//            }
//            showAlert("Resultado", message);
//            showAlert("Estados", formattedCases);
//        });
//        return btnCheck;
//    }

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

