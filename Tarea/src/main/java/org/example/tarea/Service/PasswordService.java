package org.example.tarea.Service;

import org.example.tarea.DBConfig.SecurityConfig;

public class PasswordService {

    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    public static String hashPassword(String password) {
        return SecurityConfig.hashPassword(password);
    }

    public static boolean verifyPassword(String password, String hash) {
        return SecurityConfig.verifyPassword(password, hash);
    }
}