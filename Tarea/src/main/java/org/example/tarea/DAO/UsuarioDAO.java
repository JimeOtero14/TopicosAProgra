package org.example.tarea.DAO;

import org.example.tarea.Model.Usuario;

import java.sql.SQLException;
import java.util.Optional;

public interface UsuarioDAO {
    void insertarUsuario(Usuario usuario) throws SQLException;
    Optional<Usuario> buscarPorUsername(String username) throws SQLException;
    Optional<Usuario> buscarPorCorreo(String correo) throws SQLException;
    Optional<Usuario> buscarPorUsernameOCorreo(String usernameOrEmail) throws SQLException;
    boolean existeUsername(String username) throws SQLException;
    boolean existeCorreo(String correo) throws SQLException;
}
