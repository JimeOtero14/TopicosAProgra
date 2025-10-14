package org.example.tarea.Strategy;

public interface ValidationStrategy {
    boolean validate(String input);
    String getErrorMessage();
}
