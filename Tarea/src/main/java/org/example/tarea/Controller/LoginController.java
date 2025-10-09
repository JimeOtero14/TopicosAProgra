package  org.example.tarea.Controller;

import org.example.tarea.Service.*;
import org.example.tarea.Utils.ValidationUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private userservice authService;

    public LoginController() {
        this.authService = AuthService.getInstance();
    }

    @FXML
    private void initialize() {
        // Inicialización si es necesaria
    }

    @FXML
    private void handleLogin() {
        String usernameOrEmail = usernameField.getText();
        String password = passwordField.getText();

        if (ValidationUtils.isNullOrEmpty(usernameOrEmail) || ValidationUtils.isNullOrEmpty(password)) {
            showAlert("Error", "Por favor ingrese usuario/correo y contraseña");
            return;
        }

        boolean isAuthenticated = authService.login(usernameOrEmail, password);

        if (isAuthenticated) {
            openWelcomeWindow();
        } else {
            showAlert("Error de autenticación",
                    "Usuario/correo o contraseña incorrectos. Por favor verifique sus credenciales.");
        }
    }

    @FXML
    private void handleRegister() {
        try {
            Stage currentStage = (Stage) usernameField.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/register.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Registro - CarApp");
            stage.setScene(new Scene(root, 500, 600));
            stage.show();

            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la ventana de registro");
        }
    }

    private void openWelcomeWindow() {
        try {
            Stage currentStage = (Stage) usernameField.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/welcome.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Bienvenido - CarApp");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();

            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la ventana de bienvenida");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}