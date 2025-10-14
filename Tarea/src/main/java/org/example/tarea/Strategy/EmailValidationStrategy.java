package org.example.tarea.Strategy;
public class EmailValidationStrategy implements ValidationStrategy {
    private String errorMessage = "";

    @Override
    public boolean validate(String email) {
        if (email == null || email.trim().isEmpty()) {
            errorMessage = "El correo electrónico no puede estar vacío";
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            errorMessage = "Formato de correo electrónico inválido";
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