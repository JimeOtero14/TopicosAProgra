package org.example.tarea.DAO;

import org.example.tarea.Model.Usuario;
import org.example.tarea.Util.DatabaseConnection;
import java.sql.*;
import java.util.Optional;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public void insertarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (username, correo, password_hash, nombre_completo, fecha_nacimiento) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getPasswordHash());
            stmt.setString(4, usuario.getNombreCompleto());
            stmt.setDate(5, Date.valueOf(usuario.getFechaNacimiento()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuario.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        return buscarUsuario(sql, username);
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE correo = ?";
        return buscarUsuario(sql, correo);
    }

    @Override
    public Optional<Usuario> buscarPorUsernameOCorreo(String usernameOrEmail) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE username = ? OR correo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usernameOrEmail);
            stmt.setString(2, usernameOrEmail);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUsuario(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existeUsername(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ?";
        return existeRegistro(sql, username);
    }

    @Override
    public boolean existeCorreo(String correo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
        return existeRegistro(sql, correo);
    }

    private Optional<Usuario> buscarUsuario(String sql, String param) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, param);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUsuario(rs));
                }
            }
        }
        return Optional.empty();
    }

    private boolean existeRegistro(String sql, String param) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, param);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setUsername(rs.getString("username"));
        usuario.setCorreo(rs.getString("correo"));
        usuario.setPasswordHash(rs.getString("password_hash"));
        usuario.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
        usuario.setNombreCompleto(rs.getString("nombre_completo"));
        usuario.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
        return usuario;
    }
}