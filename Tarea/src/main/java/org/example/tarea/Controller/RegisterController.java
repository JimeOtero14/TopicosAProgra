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
import org.example.tarea.Strategy.*;
import org.example.tarea.Util.PasswordUtil;

import java.time.LocalDate;

public class RegisterController {

    @FXML private TextField nombreCompletoField;
    @FXML private DatePicker fechaNacimientoPicker;
    @FXML private TextField usernameField;
    @FXML private TextField correoField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorLabel;
    @FXML private Label successLabel;
    @FXML private Button registerButton;
    @FXML private Button backButton;

    private UsuarioDAO usuarioDAO;
    private ValidationStrategy emailValidator;
    private ValidationStrategy passwordValidator;
    private ValidationStrategy usernameValidator;
    private ValidationStrategy nombreValidator;

    @FXML
    public void initialize() {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        usuarioDAO = factory.getUsuarioDAO();

        emailValidator = new EmailValidationStrategy();
        passwordValidator = new PasswordValidationStrategy();
        usernameValidator = new UsernameValidationStrategy();
        nombreValidator = new NotEmptyValidation("Nombre completo");

        errorLabel.setText("");
        successLabel.setText("");
    }

    @FXML
    private void handleRegister() {
        errorLabel.setText("");
        successLabel.setText("");

        String nombreCompleto = nombreCompletoField.getText().trim();
        LocalDate fechaNacimiento = fechaNacimientoPicker.getValue();
        String username = usernameField.getText().trim();
        String correo = correoField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!nombreValidator.validate(nombreCompleto)) {
            errorLabel.setText(nombreValidator.getErrorMessage());
            return;
        }

        if (fechaNacimiento == null) {
            errorLabel.setText("Debes seleccionar tu fecha de nacimiento");
            return;
        }

        if (fechaNacimiento.isAfter(LocalDate.now().minusYears(13))) {
            errorLabel.setText("Debes tener al menos 13 años para registrarte");
            return;
        }

        if (!usernameValidator.validate(username)) {
            errorLabel.setText(usernameValidator.getErrorMessage());
            return;
        }

        if (!emailValidator.validate(correo)) {
            errorLabel.setText(emailValidator.getErrorMessage());
            return;
        }

        if (!passwordValidator.validate(password)) {
            errorLabel.setText(passwordValidator.getErrorMessage());
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Las contraseñas no coinciden");
            return;
        }

        try {
            if (usuarioDAO.existeUsername(username)) {
                errorLabel.setText("El nombre de usuario ya está registrado");
                return;
            }

            if (usuarioDAO.existeCorreo(correo)) {
                errorLabel.setText("El correo electrónico ya está registrado");
                return;
            }

            String passwordHash = PasswordUtil.hashPassword(password);

            Usuario nuevoUsuario = new Usuario(
                    username,
                    correo,
                    passwordHash,
                    nombreCompleto,
                    fechaNacimiento
            );

            usuarioDAO.insertarUsuario(nuevoUsuario);

            successLabel.setText("¡Registro exitoso! Puedes iniciar sesión ahora.");
            limpiarCampos();

            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    javafx.application.Platform.runLater(this::handleBackToLogin);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            errorLabel.setText("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/tarea/View/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root, 450, 500));
            stage.setTitle("Iniciar Sesión");

        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Error al cargar ventana de login");
        }
    }

    private void limpiarCampos() {
        nombreCompletoField.clear();
        fechaNacimientoPicker.setValue(null);
        usernameField.clear();
        correoField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }
}