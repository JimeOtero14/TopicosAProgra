import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/tap2";
    private static final String USER = "Jimena";
    private static final String PASSWORD = "ITCelaya2022KOJU";

    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver MySQL no encontrado", e);
            }
        }
        return connection;
    }

    public static void ensureUsuarios2Table() throws SQLException {
        String ddl = "CREATE TABLE IF NOT EXISTS usuarios2 (" +
                     "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                     "nombre VARCHAR(100) NOT NULL," +
                     "email VARCHAR(100) NOT NULL," +
                     "password VARCHAR(255) NOT NULL," +
                     "fecha_registro DATE NOT NULL" +
                     ")";
        try (Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate(ddl);
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
