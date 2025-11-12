package org.example.examen3.DAO;

import org.example.examen3.Model.Example;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar operaciones CRUD de ejemplos
 */
public class ExampleDAO {
    private Connection connection;

    public ExampleDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Inserta un nuevo ejemplo
     *
     * @param example objeto Example a insertar
     * @return ID del ejemplo insertado
     * @throws SQLException si hay error en la operación
     */
    public int insert(Example example) throws SQLException {
        String sql = "INSERT INTO examples (definition_id, example) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, example.getDefinitionId());
            stmt.setString(2, example.getExample());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("Error al insertar ejemplo");
    }

    /**
     * Actualiza un ejemplo existente
     *
     * @param example objeto Example con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException si hay error en la operación
     */
    public boolean update(Example example) throws SQLException {
        String sql = "UPDATE examples SET example = ? WHERE id_example = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, example.getExample());
            stmt.setInt(2, example.getIdExample());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un ejemplo por su ID
     *
     * @param idExample ID del ejemplo a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException si hay error en la operación
     */
    public boolean delete(int idExample) throws SQLException {
        String sql = "DELETE FROM examples WHERE id_example = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idExample);
            return stmt.executeUpdate() > 0;
        }
    }
    /**
     * Busca un ejemplo por ID
     * @param idExample ID del ejemplo
     * @return objeto Example o null si no existe
     * @throws SQLException si hay error en la operación
     */
    public Example findById(int idExample) throws SQLException {
        String sql = "SELECT * FROM examples WHERE id_example = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idExample);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToExample(rs);
            }
        }
        return null;
    }

    /**
     * Obtiene todos los ejemplos de una definición
     * @param definitionId ID de la definición
     * @return lista de ejemplos
     * @throws SQLException si hay error en la operación
     */
    public List<Example> findByDefinitionId(int definitionId) throws SQLException {
        String sql = "SELECT * FROM examples WHERE definition_id = ?";
        List<Example> examples = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, definitionId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                examples.add(mapResultSetToExample(rs));
            }
        }
        return examples;
    }

    /**
     * Mapea un ResultSet a un objeto Example
     * @param rs ResultSet con los datos
     * @return objeto Example
     * @throws SQLException si hay error al leer los datos
     */
    private Example mapResultSetToExample(ResultSet rs) throws SQLException {
        return new Example(
                rs.getInt("id_example"),
                rs.getInt("definition_id"),
                rs.getString("example")
        );
    }
}