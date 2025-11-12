package org.example.examen3.DAO;

import org.example.examen3.Model.Synonym;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar operaciones CRUD de sinónimos
 */
public class SynonymDAO {
    private Connection connection;

    public SynonymDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Inserta un nuevo sinónimo
     * @param synonym objeto Synonym a insertar
     * @return ID del sinónimo insertado
     * @throws SQLException si hay error en la operación
     */
    public int insert(Synonym synonym) throws SQLException {
        String sql = "INSERT INTO synonyms (word_id, synonym, synonym_lang) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, synonym.getWordId());
            stmt.setString(2, synonym.getSynonym());
            stmt.setString(3, synonym.getSynonymLang());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("Error al insertar sinónimo");
    }

    /**
     * Actualiza un sinónimo existente
     * @param synonym objeto Synonym con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException si hay error en la operación
     */
    public boolean update(Synonym synonym) throws SQLException {
        String sql = "UPDATE synonyms SET synonym = ?, synonym_lang = ? WHERE id_syn = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, synonym.getSynonym());
            stmt.setString(2, synonym.getSynonymLang());
            stmt.setInt(3, synonym.getIdSyn());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un sinónimo por su ID
     * @param idSyn ID del sinónimo a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException si hay error en la operación
     */
    public boolean delete(int idSyn) throws SQLException {
        String sql = "DELETE FROM synonyms WHERE id_syn = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSyn);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Busca un sinónimo por ID
     * @param idSyn ID del sinónimo
     * @return objeto Synonym o null si no existe
     * @throws SQLException si hay error en la operación
     */
    public Synonym findById(int idSyn) throws SQLException {
        String sql = "SELECT * FROM synonyms WHERE id_syn = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSyn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToSynonym(rs);
            }
        }
        return null;
    }

    /**
     * Obtiene todos los sinónimos de una palabra
     * @param wordId ID de la palabra
     * @return lista de sinónimos
     * @throws SQLException si hay error en la operación
     */
    public List<Synonym> findByWordId(int wordId) throws SQLException {
        String sql = "SELECT * FROM synonyms WHERE word_id = ?";
        List<Synonym> synonyms = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, wordId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                synonyms.add(mapResultSetToSynonym(rs));
            }
        }
        return synonyms;
    }

    /**
     * Mapea un ResultSet a un objeto Synonym
     * @param rs ResultSet con los datos
     * @return objeto Synonym
     * @throws SQLException si hay error al leer los datos
     */
    private Synonym mapResultSetToSynonym(ResultSet rs) throws SQLException {
        return new Synonym(
                rs.getInt("id_syn"),
                rs.getInt("word_id"),
                rs.getString("synonym"),
                rs.getString("synonym_lang")
        );
    }
}