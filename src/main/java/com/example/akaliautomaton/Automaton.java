package com.example.akaliautomaton;

import java.util.*;

public class Automaton {
    private static String state;

    private String type;

    public ValidationResult validateLanguage (String code, String type) {
        String[] codeArray = code.split("\\s+");
        ValidationResult validationResult = null;
        switch (type) {
            case "functions" -> {
                validationResult = validateFunctionExpression(codeArray);
            }
            case "loop" -> {
                validationResult = validateLoopExpression(codeArray);
            }
            case "conditional" -> {
                validationResult = validateConditionalExpression(codeArray);
            }
            case "variables" -> {
                validationResult = validateVariableExpression(codeArray);
            }
        }
        return validationResult;
    }


    public ValidationResult validateVariableExpression(String[] codeArray) {
//        String[] elements = text.split("\\s+");
        List<String> states = new ArrayList<>();
        state = "q0";
        for (String element : codeArray) {
            states.add(state);
            switch (state) {
                case "q0" -> {
                    if (element.matches("Ent|Cdn|Bool|Dcm|Cad")) {
                        type = element;
                        state = "variable";
                    }
                }
                case "variable" -> {
                    if (element.matches("[a-zA-Z0-9]+")) {
                        state = "equals";
                    }
                }
                case "equals" -> {
                    if (element.matches("=")) {
                        state = "value";
                    }
                    if (element.isEmpty()) {
                        state = "final";
                    }
                }
                case "value" -> {
                    switch (type) {
                        case "Ent" -> {
                            if (element.matches("-?\\d+")) {
                                state = "final";
                            }
                        }
                        case "Dcm" -> {
                            if (element.matches("\\d+\\.\\d+")) {
                                state = "final";
                            }
                        }
                        case "Cdn" -> {
                            if (element.matches("'[a-zA-Z0-9]+'")) {
                                state = "final";
                            }
                        }
                        case "Bool" -> {
                            if (element.matches("[VT]")) {
                                state = "final";
                            }
                        }
                    }
                }
            }
        }
        boolean isValid = state.equals("final") || state.equals("equals");
        return new ValidationResult(isValid, states);
    }

    public ValidationResult validateConditionalExpression(String[] codeArray){
//        String[] elements = text.split("\\s+");
        List<String> states = new ArrayList<>();
        state = "q0";
        for (String element: codeArray){
            states.add(state);
            switch (state) {
                case "q0" -> {
                    if (element.equals("if")){
                        state = "conditionInitial";
                    }
                }
                case "conditionInitial" -> {
                    if (element.equals("(")) {
                        state = "initialContentCondition";
                    }
                }
                case "initialContentCondition" -> {
                    if (!element.isBlank() || element.matches("[a-zA-Z0-9]+")){
                        state = "condition";
                    }
                }
                case "condition" -> {
                    if (element.matches("[<>]=?|!=|==")){
                        state = "endContentCondition";
                    }
                }
                case "endContentCondition" -> {
                    if (!element.isBlank() || element.matches("[a-zA-Z0-9]+")){
                        state = "conditionEnd";
                    }
                }
                case "conditionEnd" -> {
                    if (element.equals(")")) {
                        state = "initialContent";
                    }
                }
                case "initialContent" -> {
                    if (element.equals("=>")){
                        state = "content";
                    }
                }
                case "content" -> {
                    if (element.matches("[a-zA-Z0-9]+")){
                        state = "endContent";
                    }
                }
                case "endContent" -> {
                    if (element.equals("<=")){
                        state = "final";
                    }
                }
            }
        }
        boolean isValid = state.equals("final");
        return new ValidationResult(isValid, states);
    }

    public static ValidationResult validateLoopExpression(String[] codeArray) {
//        String[] elements = text.split("\\s+");
        List<String> states = new ArrayList<>();
        state = "q0";
        for (String element: codeArray){
            states.add(state);
            switch (state) {
                case "q0" -> {
                    if (element.equals("for")){
                        state = "loopInitial";
                    }
                }
                case "loopInitial" -> {
                    if (element.equals("(")) {
                        state = "initialContentLoop";
                    }
                }
                case "initialContentLoop" -> {
                    if (element.matches("\\d+")){
                        state = "firstSeparation";
                    }
                }
                case "firstSeparation" -> {
                    if (element.equals("|")){
                        state = "secondContentLoop";
                    }
                }
                case "secondContentLoop" -> {
                    if (element.matches("\\d+")){
                        state = "secondSeparation";
                    }
                }
                case "secondSeparation" -> {
                    if (element.equals("|")){
                        state = "thirdContentLoop";
                    }
                }
                case "thirdContentLoop" -> {
                    if (element.matches("[+-]")){
                        state = "loopEnd";
                    }
                }
                case "loopEnd" -> {
                    if (element.equals(")")) {
                        state = "initialContent";
                    }
                }
                case "initialContent" -> {
                    if (element.equals("=>")){
                        state = "content";
                    }
                }
                case "content" -> {
                    if (element.matches("[a-zA-Z0-9]+")){
                        state = "endContent";
                    }
                }
                case "endContent" -> {
                    if (element.equals("<=")){
                        state = "final";
                    }
                }
            }
        }
        boolean isValid = state.equals("final");
        return new ValidationResult(isValid, states);
    }

    public ValidationResult validateFunctionExpression(String[] codeArray){
//        String[] elements = text.split("\\s+");
        List<String> states = new ArrayList<>();
        state = "q0";
        boolean pass = true;
        for (String element: codeArray){
            if (!element.matches("Ent|Cdn|Bool|Dcm|Cad") && pass) {
                state = "functionName";
                pass = false;
            }
            states.add(state);
            switch (state){
                case "q0" -> {
                    if (element.matches("Ent|Cdn|Bool|Dcm|Cad")) {
                        type = element;
                        state = "functionName";
                    }
                }
                case "functionName" ->{
                    if (element.matches("[a-zA-Z0-9]+")){
                        state = "functionInitial";
                    }
                }
                case "functionInitial" -> {
                    if (element.equals("<")){
                        state = "functionParameter";
                    } else if (element.equals("<>")){
                        state = "initialContent";
                    }
                }
                case "functionParameter" -> {
                    if (element.matches("[a-zA-Z0-9]+")){
                        state = "functionEnd";
                    }
                }
                case "functionEnd" -> {
                    if (element.equals(">")){
                        state = "initialContent";
                    } else if (element.equals("<>")){
                        state = "initialContent";
                    }
                }
                case "initialContent" -> {
                    if (element.equals("=>")){
                        state = "content";
                    }
                }
                case "content" -> {
                    if (element.matches("[a-zA-Z0-9]+")){
                        state = "endContent";
                    } else if (!type.isBlank()){
                        state = "return";
                    }
                }
                case "return" -> {
                    if (element.equals("rtn")){
                        state = "elementReturned";
                    }
                }
                case "elementReturned" -> {
                    if (element.matches("[a-zA-Z0-9]+")){
                       state = "endContent";
                    }
                }
                case "endContent" -> {
                    if (element.equals("<=")){
                        state = "final";
                    }
                }
            }
        }
        boolean isValid = state.equals("final");
        return new ValidationResult(isValid, states);
    }
}
