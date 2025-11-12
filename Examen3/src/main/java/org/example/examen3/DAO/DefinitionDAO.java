package org.example.examen3.DAO;

import org.example.examen3.Model.Definition;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar operaciones CRUD de definiciones
 */
public class DefinitionDAO {
    private Connection connection;

    public DefinitionDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Inserta una nueva definición
     * @param definition objeto Definition a insertar
     * @return ID de la definición insertada
     * @throws SQLException si hay error en la operación
     */
    public int insert(Definition definition) throws SQLException {
        // Verificar que no exista una definición idéntica para la misma palabra
        if (definitionExists(definition.getWordId(), definition.getDefinition())) {
            throw new SQLException("Esta definición ya existe para esta palabra");
        }

        String sql = "INSERT INTO definitions (word_id, definition, part_of_speech) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, definition.getWordId());
            stmt.setString(2, definition.getDefinition());
            stmt.setString(3, definition.getPartOfSpeech());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("Error al insertar definición");
    }

    /**
     * Actualiza una definición existente
     * @param definition objeto Definition con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException si hay error en la operación
     */
    public boolean update(Definition definition) throws SQLException {
        String sql = "UPDATE definitions SET definition = ?, part_of_speech = ? WHERE id_definition = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, definition.getDefinition());
            stmt.setString(2, definition.getPartOfSpeech());
            stmt.setInt(3, definition.getIdDefinition());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Elimina una definición por su ID
     * @param idDefinition ID de la definición a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException si hay error en la operación
     */
    public boolean delete(int idDefinition) throws SQLException {
        String sql = "DELETE FROM definitions WHERE id_definition = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idDefinition);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Busca una definición por ID
     * @param idDefinition ID de la definición
     * @return objeto Definition o null si no existe
     * @throws SQLException si hay error en la operación
     */
    public Definition findById(int idDefinition) throws SQLException {
        String sql = "SELECT * FROM definitions WHERE id_definition = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idDefinition);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToDefinition(rs);
            }
        }
        return null;
    }

    /**
     * Obtiene todas las definiciones de una palabra
     * @param wordId ID de la palabra
     * @return lista de definiciones
     * @throws SQLException si hay error en la operación
     */
    public List<Definition> findByWordId(int wordId) throws SQLException {
        String sql = "SELECT * FROM definitions WHERE word_id = ?";
        List<Definition> definitions = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, wordId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                definitions.add(mapResultSetToDefinition(rs));
            }
        }
        return definitions;
    }

    /**
     * Verifica si una definición ya existe para una palabra
     * @param wordId ID de la palabra
     * @param definition texto de la definición
     * @return true si existe, false si no
     * @throws SQLException si hay error en la operación
     */
    private boolean definitionExists(int wordId, String definition) throws SQLException {
        String sql = "SELECT COUNT(*) FROM definitions WHERE word_id = ? AND definition = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, wordId);
            stmt.setString(2, definition);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    /**
     * Mapea un ResultSet a un objeto Definition
     * @param rs ResultSet con los datos
     * @return objeto Definition
     * @throws SQLException si hay error al leer los datos
     */
    private Definition mapResultSetToDefinition(ResultSet rs) throws SQLException {
        return new Definition(
                rs.getInt("id_definition"),
                rs.getInt("word_id"),
                rs.getString("definition"),
                rs.getString("part_of_speech")
        );
    }
}