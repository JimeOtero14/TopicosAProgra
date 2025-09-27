import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
        public static void main(String[] args) {
            String url = "jdbc:mysql://localhost:3306/escuela";
            String user = "Jimena";
            String pass = "ITCelaya2022KOJU";

            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                EstudianteDAO dao = new EstudianteDAOImpl(conn);

                // Insertar
                dao.insertar(new Estudiante(0, "Oscar", "oscar@mail.com"));

                // Listar
                for (Estudiante e : dao.listar()) {
                    System.out.println(e.getId() + " - " + e.getNombre());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}