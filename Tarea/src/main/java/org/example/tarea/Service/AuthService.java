package org.example.tarea.Service;

import org.example.tarea.DBConfig.DataConfig;
import org.example.tarea.Model.User;
import org.example.tarea.Model.Builder.UserBuilder;

import java.sql.*;
import java.time.LocalDate;

public class AuthService {
    private static userservice instance;

    private void UserService() {}

    public static userservice getInstance() {
        if (instance == null) {
            instance = new userservice();
        }
        return instance;
    }

    public boolean isUsernameTaken(String username) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ?";

        try (Connection conn = DataConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isEmailTaken(String email) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";

        try (Connection conn = DataConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean registerUser(String username, String correo, String password,
                                String nombreCompleto, LocalDate fechaNacimiento) {
        String sql = "INSERT INTO usuarios (username, correo, password_hash, nombre_completo, fecha_nacimiento) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DataConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String passwordHash = PasswordService.hashPassword(password);

            pstmt.setString(1, username);
            pstmt.setString(2, correo);
            pstmt.setString(3, passwordHash);
            pstmt.setString(4, nombreCompleto);
            pstmt.setDate(5, Date.valueOf(fechaNacimiento));

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User authenticate(String usernameOrEmail, String password) {
        String sql = "SELECT * FROM usuarios WHERE username = ? OR correo = ?";

        try (Connection conn = DataConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usernameOrEmail);
            pstmt.setString(2, usernameOrEmail);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");

                if (PasswordService.verifyPassword(password, storedHash)) {
                    // Usar el patrón Builder para crear el objeto User
                    return new UserBuilder(
                            rs.getString("username"),
                            rs.getString("correo"),
                            rs.getString("nombre_completo")
                    )
                            .id(rs.getInt("id"))
                            .passwordHash(storedHash)
                            .fechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime())
                            .fechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate())
                            .build();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection conn = DataConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new UserBuilder(
                        rs.getString("username"),
                        rs.getString("correo"),
                        rs.getString("nombre_completo")
                )
                        .id(rs.getInt("id"))
                        .passwordHash(rs.getString("password_hash"))
                        .fechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime())
                        .fechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate())
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}