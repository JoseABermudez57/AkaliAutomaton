package com.example.akaliautomaton;

import java.util.List;

public record ValidationResult(boolean isValid, List<String> cases) {
}
