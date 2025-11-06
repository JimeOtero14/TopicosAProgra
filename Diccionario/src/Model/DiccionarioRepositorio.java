package Model;

import Util.Validador;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repositorio para gestionar palabras en archivo de texto
 * Implementa el patrón Singleton
 */
public class DiccionarioRepositorio implements IRepositorio<String> {
    private static DiccionarioRepositorio instancia;
    private final String rutaArchivo;

    private DiccionarioRepositorio(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        inicializarArchivo();
    }

    /**
     * Obtiene la instancia única del repositorio (Singleton)
     */
    public static DiccionarioRepositorio obtenerInstancia(String rutaArchivo) {
        if (instancia == null) {
            instancia = new DiccionarioRepositorio(rutaArchivo);
        }
        return instancia;
    }

    /**
     * Inicializa el archivo si no existe
     */
    private void inicializarArchivo() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                // Agregar palabras iniciales
                String[] palabrasIniciales = {
                        "casa", "perro", "gato", "sol", "luna",
                        "mar", "rio", "monte", "flor", "arbol"
                };
                for (String palabra : palabrasIniciales) {
                    agregar(palabra);
                }
            } catch (IOException e) {
                System.err.println("Error al crear archivo: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean existe(String palabra) {
        String palabraNormalizada = Validador.normalizar(palabra);
        return obtenerTodos().stream()
                .anyMatch(p -> p.equals(palabraNormalizada));
    }

    @Override
    public boolean agregar(String palabra) {
        if (!Validador.esValida(palabra)) {
            return false;
        }

        String palabraNormalizada = Validador.normalizar(palabra);

        if (existe(palabraNormalizada)) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(rutaArchivo, true))) {
            writer.write(palabraNormalizada);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar palabra: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(String palabra) {
        String palabraNormalizada = Validador.normalizar(palabra);

        if (!existe(palabraNormalizada)) {
            return false;
        }

        List<String> palabras = obtenerTodos().stream()
                .filter(p -> !p.equals(palabraNormalizada))
                .collect(Collectors.toList());

        return reescribirArchivo(palabras);
    }

    @Override
    public List<String> obtenerTodos() {
        List<String> palabras = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String palabra = linea.trim();
                if (!palabra.isEmpty() && Validador.esValida(palabra)) {
                    palabras.add(Validador.normalizar(palabra));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
        }
        return palabras;
    }

    /**
     * Reescribe todo el archivo con una nueva lista de palabras
     */
    private boolean reescribirArchivo(List<String> palabras) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(rutaArchivo))) {
            for (String palabra : palabras) {
                writer.write(palabra);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error al reescribir archivo: " + e.getMessage());
            return false;
        }
    }
}