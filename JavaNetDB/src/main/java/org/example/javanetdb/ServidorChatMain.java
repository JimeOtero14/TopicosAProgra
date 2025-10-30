package org.example.javanetdb;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Servidor de Chat con almacenamiento en MySQL
 * Puerto 3306 - Base de Datos: JavaNet
 */
public class ServidorChatMain {
    private static final int PUERTO = 3306;

    // Configuración de MySQL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/JavaNet";
    private static final String DB_USER = "Jimena";
    private static final String DB_PASSWORD = "ITCelaya2022KOJU";

    public static void main(String[] args) {
        System.out.println("=== SERVIDOR DE CHAT CON MYSQL ===");

        // Cargar driver de MySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver MySQL no encontrado");
            System.err.println("Descarga: https://dev.mysql.com/downloads/connector/j/");
            return;
        }

        // Inicializar base de datos
        if (!inicializarBaseDatos()) {
            System.err.println("No se pudo inicializar la base de datos. Abortando...");
            return;
        }

        System.out.println("Iniciando servidor en puerto " + PUERTO + "...");

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor escuchando en puerto " + PUERTO);
            System.out.println("Base de datos: JavaNet (MySQL)");
            System.out.println("Esperando conexiones...\n");

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("¡Cliente conectado desde: " +
                        clienteSocket.getInetAddress().getHostAddress() + "!");

                new Thread(new ManejadorCliente(clienteSocket)).start();
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Inicializa la base de datos y crea la tabla de mensajes
     */
    private static boolean inicializarBaseDatos() {
        Connection conn = null;
        Statement stmt = null;

        try {
            // Primero, conectar sin especificar la base de datos para crearla si no existe
            String urlSinDB = "jdbc:mysql://localhost:3306/";
            conn = DriverManager.getConnection(urlSinDB, DB_USER, DB_PASSWORD);
            stmt = conn.createStatement();

            // Crear la base de datos JavaNet si no existe
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS JavaNet " +
                    "CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
            System.out.println("✓ Base de datos 'JavaNet' verificada/creada");

            stmt.close();
            conn.close();

            // Ahora conectar a la base de datos JavaNet
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = conn.createStatement();

            // Crear la tabla mensajes si no existe
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS mensajes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "cliente_ip VARCHAR(45) NOT NULL," +
                    "mensaje TEXT NOT NULL," +
                    "respuesta TEXT NOT NULL," +
                    "fecha_hora DATETIME NOT NULL," +
                    "INDEX idx_cliente_ip (cliente_ip)," +
                    "INDEX idx_fecha_hora (fecha_hora)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";

            stmt.executeUpdate(sqlCreateTable);
            System.out.println("Tabla 'mensajes' verificada/creada");

            return true;

        } catch (SQLException e) {
            System.err.println("✗ Error inicializando base de datos: " + e.getMessage());
            System.err.println("  Verifica que MySQL esté ejecutándose");
            System.err.println("  Usuario: " + DB_USER);
            System.err.println("  Asegúrate de tener permisos para crear bases de datos");
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Obtiene una conexión a la base de datos
     */
    private static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * Guarda un mensaje en la base de datos
     */
    public static void guardarMensaje(String clienteIp, String mensaje, String respuesta) {
        String sql = "INSERT INTO mensajes(cliente_ip, mensaje, respuesta, fecha_hora) " +
                "VALUES(?, ?, ?, ?)";

        try (Connection conn = obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            LocalDateTime ahora = LocalDateTime.now();

            pstmt.setString(1, clienteIp);
            pstmt.setString(2, mensaje);
            pstmt.setString(3, respuesta);
            pstmt.setTimestamp(4, Timestamp.valueOf(ahora));

            pstmt.executeUpdate();

            System.out.println("  [MySQL] Mensaje guardado: " + mensaje);

        } catch (SQLException e) {
            System.err.println("✗ Error guardando mensaje en MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Consulta el historial de mensajes
     */
    public static String consultarHistorial(String clienteIp) {
        StringBuilder historial = new StringBuilder();
        historial.append("\n=== HISTORIAL DE MENSAJES (MySQL) ===\n");

        String sql = "SELECT mensaje, respuesta, fecha_hora FROM mensajes " +
                "WHERE cliente_ip = ? " +
                "ORDER BY id DESC LIMIT 50";

        try (Connection conn = obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, clienteIp);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while (rs.next()) {
                count++;
                Timestamp timestamp = rs.getTimestamp("fecha_hora");
                String fechaHora = timestamp.toLocalDateTime().format(formatter);

                historial.append(String.format("\n[%s]\nCliente: %s\nServidor: %s\n",
                        fechaHora,
                        rs.getString("mensaje"),
                        rs.getString("respuesta")));
            }

            if (count == 0) {
                historial.append("\nNo hay mensajes en el historial.\n");
            } else {
                historial.append(String.format("\nTotal: %d mensajes\n", count));
            }

            historial.append("=== FIN DEL HISTORIAL ===\n");

        } catch (SQLException e) {
            historial.append("\n✗ Error consultando historial: " + e.getMessage() + "\n");
            e.printStackTrace();
        }

        return historial.toString();
    }

    /**
     * Obtiene estadísticas de la base de datos
     */
    public static String obtenerEstadisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append("\n=== ESTADÍSTICAS DEL SERVIDOR ===\n");

        try (Connection conn = obtenerConexion();
             Statement stmt = conn.createStatement()) {

            // Total de mensajes
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total FROM mensajes");
            if (rs.next()) {
                stats.append("Total de mensajes: ").append(rs.getInt("total")).append("\n");
            }

            // Clientes únicos
            rs = stmt.executeQuery("SELECT COUNT(DISTINCT cliente_ip) as clientes FROM mensajes");
            if (rs.next()) {
                stats.append("Clientes únicos: ").append(rs.getInt("clientes")).append("\n");
            }

            // Último mensaje
            rs = stmt.executeQuery("SELECT fecha_hora FROM mensajes ORDER BY id DESC LIMIT 1");
            if (rs.next()) {
                stats.append("Último mensaje: ").append(rs.getString("fecha_hora")).append("\n");
            }

            stats.append("=== FIN DE ESTADÍSTICAS ===\n");

        } catch (SQLException e) {
            stats.append("Error obteniendo estadísticas: ").append(e.getMessage()).append("\n");
        }

        return stats.toString();
    }

    /**
     * Maneja cada cliente en un hilo separado
     */
    private static class ManejadorCliente implements Runnable {
        private Socket socket;
        private BufferedReader entrada;
        private PrintWriter salida;
        private String clienteIp;

        public ManejadorCliente(Socket socket) {
            this.socket = socket;
            this.clienteIp = socket.getInetAddress().getHostAddress();
        }

        @Override
        public void run() {
            try {
                entrada = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                salida = new PrintWriter(
                        socket.getOutputStream(), true
                );

                salida.println("¡Bienvenido al servidor de chat con MySQL!");
                salida.println("Base de datos: JavaNet");
                salida.println("Comandos disponibles:");
                salida.println("  - HISTORIAL: Ver todos tus mensajes anteriores");
                salida.println("  - STATS: Ver estadísticas del servidor");
                salida.println("  - SALIR: Desconectar del servidor");
                salida.println("Reglas: Mensajes con '?' se responden en MAYÚSCULAS");

                String mensajeCliente;

                while ((mensajeCliente = entrada.readLine()) != null) {
                    System.out.println("[" + clienteIp + "] Cliente dice: " + mensajeCliente);

                    // Comandos especiales
                    if (mensajeCliente.equalsIgnoreCase("HISTORIAL")) {
                        String historial = consultarHistorial(clienteIp);
                        salida.println(historial);
                        System.out.println("  [CMD] Enviando historial a " + clienteIp);
                        continue;
                    }

                    if (mensajeCliente.equalsIgnoreCase("STATS")) {
                        String stats = obtenerEstadisticas();
                        salida.println(stats);
                        System.out.println("  [CMD] Enviando estadísticas a " + clienteIp);
                        continue;
                    }

                    if (mensajeCliente.equalsIgnoreCase("SALIR")) {
                        salida.println("servidor: ¡hasta pronto!");
                        System.out.println("[" + clienteIp + "] Cliente se desconectó");
                        break;
                    }

                    // Procesar mensaje normal
                    String respuesta = procesarMensaje(mensajeCliente);
                    salida.println(respuesta);

                    // Guardar en MySQL
                    guardarMensaje(clienteIp, mensajeCliente, respuesta);

                    System.out.println("  Servidor responde: " + respuesta);
                }

            } catch (IOException e) {
                System.err.println("Error manejando cliente: " + e.getMessage());
            } finally {
                cerrarConexion();
            }
        }

        private String procesarMensaje(String mensaje) {
            if (mensaje == null || mensaje.isEmpty()) {
                return "mensaje vacío";
            }

            if (mensaje.trim().endsWith("?")) {
                return "SERVIDOR: " + mensaje.toUpperCase();
            } else {
                return "servidor: " + mensaje.toLowerCase();
            }
        }

        private void cerrarConexion() {
            try {
                if (entrada != null) entrada.close();
                if (salida != null) salida.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }
}