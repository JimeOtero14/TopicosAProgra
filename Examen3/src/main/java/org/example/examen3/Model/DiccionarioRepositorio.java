package org.example.examen3.Model;

import org.example.examen3.DAO.DatabaseConnection;



/**
 * Repositorio para gestionar palabras en archivo de texto
 * Implementa el patrón Singleton
 */
public class DiccionarioRepositorio {
    private static DiccionarioRepositorio instancia;
    private final String DatabaseConnection;

    private DiccionarioRepositorio(String DatabaseConnection) {
        this.DatabaseConnection = DatabaseConnection;
        DatabaseConnection();
    }

    private void DatabaseConnection() {

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

    public boolean existe(String palabraNormalizada) {
        return false;
    }

    public boolean eliminar(String palabraNormalizada) {
        return false;
    }
}