package org.example.tarea.DBConfig;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class SecurityConfig {
    private static final int BCRYPT_COST = 12;

    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(BCRYPT_COST, password.toCharArray());
    }

    public static boolean verifyPassword(String password, String hash) {
        return BCrypt.verifyer().verify(password.toCharArray(), hash.toCharArray()).verified;
    }
}