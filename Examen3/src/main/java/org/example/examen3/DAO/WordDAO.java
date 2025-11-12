package org.example.examen3.DAO;

import org.example.examen3.Model.Word;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar operaciones CRUD de palabras
 */
public class WordDAO {
    private Connection connection;

    public WordDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Inserta una nueva palabra en la base de datos
     * @param word objeto Word a insertar
     * @return ID de la palabra insertada
     * @throws SQLException si hay error en la operación
     */
    public int insert(Word word) throws SQLException {
        // Verificar si la palabra ya existe
        if (wordExists(word.getWord(), word.getLanguage())) {
            throw new SQLException("La palabra '" + word.getWord() + "' ya existe en el diccionario");
        }

        String sql = "INSERT INTO words (word, language) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, word.getWord());
            stmt.setString(2, word.getLanguage());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("Error al insertar palabra");
    }

    /**
     * Actualiza una palabra existente
     * @param word objeto Word con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException si hay error en la operación
     */
    public boolean update(Word word) throws SQLException {
        String sql = "UPDATE words SET word = ?, language = ? WHERE id_word = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, word.getWord());
            stmt.setString(2, word.getLanguage());
            stmt.setInt(3, word.getIdWord());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Elimina una palabra por su ID
     * @param idWord ID de la palabra a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException si hay error en la operación
     */
    public boolean delete(int idWord) throws SQLException {
        String sql = "DELETE FROM words WHERE id_word = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idWord);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Busca una palabra por ID
     * @param idWord ID de la palabra
     * @return objeto Word o null si no existe
     * @throws SQLException si hay error en la operación
     */
    public Word findById(int idWord) throws SQLException {
        String sql = "SELECT * FROM words WHERE id_word = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idWord);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToWord(rs);
            }
        }
        return null;
    }

    /**
     * Busca palabras por coincidencia exacta
     * @param word palabra a buscar
     * @return lista de palabras encontradas
     * @throws SQLException si hay error en la operación
     */
    public List<Word> findByExactMatch(String word) throws SQLException {
        String sql = "SELECT * FROM words WHERE word = ?";
        List<Word> words = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, word);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                words.add(mapResultSetToWord(rs));
            }
        }
        return words;
    }

    /**
     * Busca palabras por coincidencia parcial
     * @param word fragmento de palabra a buscar
     * @return lista de palabras encontradas
     * @throws SQLException si hay error en la operación
     */
    public List<Word> findByPartialMatch(String word) throws SQLException {
        String sql = "SELECT * FROM words WHERE word LIKE ?";
        List<Word> words = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + word + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                words.add(mapResultSetToWord(rs));
            }
        }
        return words;
    }

    /**
     * Busca palabras por idioma
     * @param language código de idioma
     * @return lista de palabras encontradas
     * @throws SQLException si hay error en la operación
     */
    public List<Word> findByLanguage(String language) throws SQLException {
        String sql = "SELECT * FROM words WHERE language = ?";
        List<Word> words = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, language);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                words.add(mapResultSetToWord(rs));
            }
        }
        return words;
    }

    /**
     * Obtiene todas las palabras
     * @return lista con todas las palabras
     * @throws SQLException si hay error en la operación
     */
    public List<Word> findAll() throws SQLException {
        String sql = "SELECT * FROM words ORDER BY word";
        List<Word> words = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                words.add(mapResultSetToWord(rs));
            }
        }
        return words;
    }

    /**
     * Verifica si una palabra ya existe
     * @param word palabra a verificar
     * @param language idioma de la palabra
     * @return true si existe, false si no
     * @throws SQLException si hay error en la operación
     */
    private boolean wordExists(String word, String language) throws SQLException {
        String sql = "SELECT COUNT(*) FROM words WHERE word = ? AND language = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, word);
            stmt.setString(2, language);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    /**
     * Mapea un ResultSet a un objeto Word
     * @param rs ResultSet con los datos
     * @return objeto Word
     * @throws SQLException si hay error al leer los datos
     */
    private Word mapResultSetToWord(ResultSet rs) throws SQLException {
        return new Word(
                rs.getInt("id_word"),
                rs.getString("word"),
                rs.getString("language"),
                rs.getTimestamp("created_at")
        );
    }
}