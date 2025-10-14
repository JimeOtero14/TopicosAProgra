package org.example.tarea.Strategy;

public class UsernameValidationStrategy implements ValidationStrategy {
    private String errorMessage = "";

    @Override
    public boolean validate(String username) {
        if (username == null || username.trim().isEmpty()) {
            errorMessage = "El nombre de usuario no puede estar vacío";
            return false;
        }

        if (username.length() < 3) {
            errorMessage = "El nombre de usuario debe tener al menos 3 caracteres";
            return false;
        }

        if (username.length() > 50) {
            errorMessage = "El nombre de usuario no puede exceder 50 caracteres";
            return false;
        }

        if (!username.matches("^[a-zA-Z0-9_.-]+$")) {
            errorMessage = "El nombre de usuario solo puede contener letras, números, guiones y puntos";
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