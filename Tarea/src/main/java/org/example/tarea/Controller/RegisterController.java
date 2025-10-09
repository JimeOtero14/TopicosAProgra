package  org.example.tarea.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.tarea.Service.PasswordService;
import org.example.tarea.Service.userservice;
import org.example.tarea.Utils.ValidationUtils;

import java.io.IOException;
import java.time.LocalDate;

public class RegisterController {

    @FXML private TextField nombreCompletoField;
    @FXML private DatePicker fechaNacimientoPicker;
    @FXML private TextField usernameField;
    @FXML private TextField correoField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button registerButton;

    private userservice userService;

    public RegisterController() {
        this.userService = userservice.getInstance();
    }

    @FXML
    private void initialize() {
        // Configurar el DatePicker para que no permita fechas futuras
        fechaNacimientoPicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isAfter(LocalDate.now()));
            }
        });
    }

    @FXML
    private void handleRegister() {
        if (!validateForm()) {
            return;
        }

        String nombreCompleto = nombreCompletoField.getText();
        LocalDate fechaNacimiento = fechaNacimientoPicker.getValue();
        String username = usernameField.getText();
        String correo = correoField.getText();
        String password = passwordField.getText();

        boolean success = userService.registerUser(username, correo, password, nombreCompleto, fechaNacimiento);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Registro exitoso",
                    "¡Tu cuenta ha sido creada exitosamente! Ahora puedes iniciar sesión.");
            openLoginWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error en el registro",
                    "Ha ocurrido un error al crear tu cuenta. Por favor intenta nuevamente.");
        }
    }

    @FXML
    private void handleBackToLogin() {
        openLoginWindow();
    }

    private boolean validateForm() {
        // Validar campos vacíos
        if (ValidationUtils.isNullOrEmpty(nombreCompletoField.getText()) ||
                fechaNacimientoPicker.getValue() == null ||
                ValidationUtils.isNullOrEmpty(usernameField.getText()) ||
                ValidationUtils.isNullOrEmpty(correoField.getText()) ||
                ValidationUtils.isNullOrEmpty(passwordField.getText()) ||
                ValidationUtils.isNullOrEmpty(confirmPasswordField.getText())) {

            showAlert(Alert.AlertType.ERROR, "Campos incompletos",
                    "Por favor complete todos los campos del formulario.");
            return false;
        }

        // Validar formato de correo
        if (!ValidationUtils.isValidEmail(correoField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Correo inválido",
                    "Por favor ingrese un correo electrónico válido.");
            return false;
        }

        // Validar que las contraseñas coincidan
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Contraseñas no coinciden",
                    "Las contraseñas ingresadas no coinciden. Por favor verifique.");
            return false;
        }

        // Validar fortaleza de la contraseña
        if (!PasswordService.isStrongPassword(passwordField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Contraseña débil",
                    "La contraseña debe tener al menos 8 caracteres, incluyendo mayúsculas, minúsculas, números y caracteres especiales.");
            return false;
        }

        // Validar que el usuario no exista
        if (userService.isUsernameTaken(usernameField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Usuario no disponible",
                    "El nombre de usuario ya está en uso. Por favor elija otro.");
            return false;
        }

        // Validar que el correo no exista
        if (userService.isEmailTaken(correoField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Correo ya registrado",
                    "El correo electrónico ya está registrado. Por favor use otro correo.");
            return false;
        }

        // Validar edad (mínimo 18 años)
        LocalDate fechaNacimiento = fechaNacimientoPicker.getValue();
        if (fechaNacimiento.plusYears(18).isAfter(LocalDate.now())) {
            showAlert(Alert.AlertType.ERROR, "Edad insuficiente",
                    "Debes ser mayor de 18 años para registrarte.");
            return false;
        }

        return true;
    }

    private void openLoginWindow() {
        try {
            Stage currentStage = (Stage) nombreCompletoField.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Login - CarApp");
            stage.setScene(new Scene(root, 400, 350));
            stage.show();

            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}