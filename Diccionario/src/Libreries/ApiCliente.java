package Libreries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Cliente para consumir la API de palabras aleatorias
 */
public class ApiCliente {
    private static final String API_URL = "https://random-word-api.herokuapp.com/word?number=";

    /**
     * Obtiene palabras aleatorias desde la API
     * @param cantidad Número de palabras a obtener
     * @return Lista de palabras obtenidas
     */
    public List<String> obtenerPalabrasAleatorias(int cantidad) {
        List<String> palabras = new ArrayList<>();
        try {
            URL url = new URL(API_URL + cantidad);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // La API devuelve: ["word1","word2","word3"]
                palabras = parsearJsonArray(response.toString());
            }
        } catch (Exception e) {
            System.err.println("Error al conectar con la API: " + e.getMessage());
        }
        return palabras;
    }

    /**
     * Formato esperado: ["palabra1","palabra2","palabra3"]
     */
    private List<String> parsearJsonArray(String json) {
        List<String> palabras = new ArrayList<>();

        // Remover corchetes y espacios
        json = json.trim();
        if (json.startsWith("[") && json.endsWith("]")) {
            json = json.substring(1, json.length() - 1);
        }

        // Dividir por comas y limpiar comillas
        if (!json.isEmpty()) {
            String[] elementos = json.split(",");
            for (String elemento : elementos) {
                // Remover comillas y espacios
                String palabra = elemento.trim()
                        .replace("\"", "")
                        .replace("'", "");
                if (!palabra.isEmpty()) {
                    palabras.add(palabra);
                }
            }
        }

        return palabras;
    }
}