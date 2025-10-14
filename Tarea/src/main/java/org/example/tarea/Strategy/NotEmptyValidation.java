package org.example.tarea.Strategy;

public class NotEmptyValidation implements ValidationStrategy {
    private String fieldName;
    private String errorMessage = "";

    public NotEmptyValidation(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean validate(String input) {
        if (input == null || input.trim().isEmpty()) {
            errorMessage = "El campo " + fieldName + " no puede estar vacío";
            return false;
        }
        errorMessage = "";
        return true;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
