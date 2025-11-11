package org.example.examen3.DAO;

import org.example.examen3.Model.Palabra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.List;

import static org.example.examen3.DAO.DatabaseConnection.connection;


public class PalabraDAO {


    public PalabraDAO(Connection connection) {
        super();
    }

    public static List<Palabra> filterwords(String continent, String nameFilter) {
        return null;
    }

    public boolean save(Palabra palabra) {
        String sql = "INSERT INTO words (word, language) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, palabra.word());
            stmt.setString(2, palabra.language());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            return true;
        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Palabra palabra) {
        String sql = "UPDATE words SET word = ?, language = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, palabra.word());
            stmt.setString(2, palabra.language());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }


    public boolean delete(Long id) {
        String sql = "DELETE FROM words WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
}
