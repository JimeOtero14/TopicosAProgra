package org.example.tarea.Strategy;

public class PasswordValidationStrategy implements ValidationStrategy {
    private String errorMessage = "";

    @Override
    public boolean validate(String password) {
        if (password == null || password.isEmpty()) {
            errorMessage = "La contraseña no puede estar vacía";
            return false;
        }

        if (password.length() < 8) {
            errorMessage = "La contraseña debe tener al menos 8 caracteres";
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            errorMessage = "La contraseña debe contener al menos una mayúscula";
            return false;
        }

        if (!password.matches(".*[a-z].*")) {
            errorMessage = "La contraseña debe contener al menos una minúscula";
            return false;
        }

        if (!password.matches(".*\\d.*")) {
            errorMessage = "La contraseña debe contener al menos un número";
            return false;
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            errorMessage = "La contraseña debe contener al menos un carácter especial";
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