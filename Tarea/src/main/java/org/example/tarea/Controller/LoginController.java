package org.example.tarea.Controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.tarea.DAO.UsuarioDAO;
import org.example.tarea.Factory.DAOFactory;
import org.example.tarea.Model.Usuario;
import org.example.tarea.Util.PasswordUtil;

import java.util.Optional;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button loginButton;
    @FXML private Button registerButton;

    private UsuarioDAO usuarioDAO;

    @FXML
    public void initialize() {
        // Inicializar DAO usando Factory Pattern
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        usuarioDAO = factory.getUsuarioDAO();

        // Limpiar mensaje de error al inicio
        errorLabel.setText("");
    }

    @FXML
    private void handleLogin() {
        errorLabel.setText("");

        String usernameOrEmail = usernameField.getText().trim();
        String password = passwordField.getText();

        // Validar campos vacíos
        if (usernameOrEmail.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Por favor, completa todos los campos");
            return;
        }

        try {
            // Buscar usuario en la base de datos
            Optional<Usuario> usuarioOpt = usuarioDAO.buscarPorUsernameOCorreo(usernameOrEmail);

            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();

                // Verificar contraseña usando BCrypt
                if (PasswordUtil.checkPassword(password, usuario.getPasswordHash())) {
                    // Login exitoso
                    abrirVentanaBienvenida(usuario);
                } else {
                    errorLabel.setText("Contraseña incorrecta");
                }
            } else {
                errorLabel.setText("Usuario no encontrado");
            }

        } catch (Exception e) {
            errorLabel.setText("Error al iniciar sesión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/tarea/View/register.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(new Scene(root, 500, 700));
            stage.setTitle("Registro de Usuario");

        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Error al cargar ventana de registro");
        }
    }

    private void abrirVentanaBienvenida(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/tarea/View/welcome.fxml"));
            Parent root = loader.load();

            // Pasar datos del usuario al controlador de bienvenida
            WelcomeController welcomeController = loader.getController();
            welcomeController.setUsuario(usuario);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 500));
            stage.setTitle("Bienvenido");

        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Error al cargar ventana de bienvenida");
        }
    }
}
