package org.example.examen3.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase Singleton para gestionar la conexión a MySQL
 */
public class DatabaseConnection  {
    private static DatabaseConnection instance;
    private Connection connection;

    // Parámetros de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/diccionario";
    private static final String USER = "Jimena";
    private static final String PASSWORD = "ITCelaya2022KOJU";

    /**
     * Constructor privado para evitar instanciación directa
     */
    private DatabaseConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión establecida exitosamente");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no encontrado", e);
        }
    }

    /**
     * Obtiene la instancia única de la conexión (patrón Singleton)
     * @return instancia de MySQLConnection
     * @throws SQLException si hay error en la conexión
     */
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Obtiene el objeto Connection
     * @return Connection activa
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Cierra la conexión a la base de datos
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}
