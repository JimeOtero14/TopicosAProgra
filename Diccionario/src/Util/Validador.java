package Util;

import java.util.regex.Pattern;

/**
 * Clase utilitaria para validación de palabras
 */
public class Validador {
    private static final Pattern PATRON_PALABRA = Pattern.compile("^[a-zA-Z]+$");

    /**
     * Valida que una palabra contenga solo letras (a-z, A-Z)
     * @param palabra La palabra a validar
     * @return true si es válida, false en caso contrario
     */
    public static boolean esValida(String palabra) {
        return palabra != null && !palabra.trim().isEmpty()
                && PATRON_PALABRA.matcher(palabra.trim()).matches();
    }

    /**
     * Normaliza una palabra eliminando espacios y convirtiendo a minúsculas
     * @param palabra La palabra a normalizar
     * @return La palabra normalizada
     */
    public static String normalizar(String palabra) {
        return palabra != null ? palabra.trim().toLowerCase() : "";
    }
}