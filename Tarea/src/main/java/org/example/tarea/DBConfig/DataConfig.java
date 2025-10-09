package org.example.tarea.DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/carapp_db";
    private static final String USER = "Jimena";
    private static final String PASSWORD = "ITCelaya2022KOJU";

    static {
        try {
            // Registrar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error cargando el driver de MySQL", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {
        // Primero crear la base de datos si no existe
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS carapp_db";
        String useDatabaseSQL = "USE carapp_db";
        String createTableSQL = "CREATE TABLE IF NOT EXISTS usuarios (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(50) UNIQUE NOT NULL, correo VARCHAR(100) UNIQUE NOT NULL, password_hash VARCHAR(255) NOT NULL, fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP, nombre_completo VARCHAR(100) NOT NULL, fecha_nacimiento DATE NOT NULL)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Crear base de datos
            stmt.execute(createDatabaseSQL);
            stmt.execute(useDatabaseSQL);

            // Crear tabla
            stmt.execute(createTableSQL);
            System.out.println("✅ Base de datos y tabla creadas correctamente");

            // Insertar usuario de prueba
            insertSampleUser();

        } catch (SQLException e) {
            System.err.println("❌ Error al inicializar la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertSampleUser() {
        String insertUserSQL = "INSERT IGNORE INTO usuarios (username, correo, password_hash, nombre_completo, fecha_nacimiento) VALUES ('admin', 'admin@carapp.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewdCvWObkPO4G0yG', 'Administrador CarApp', '1990-01-01')";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(insertUserSQL);
            System.out.println("✅ Usuario de prueba creado: admin / Admin123!");

        } catch (SQLException e) {
            System.err.println("⚠️  No se pudo insertar usuario de prueba: " + e.getMessage());
        }
    }
}