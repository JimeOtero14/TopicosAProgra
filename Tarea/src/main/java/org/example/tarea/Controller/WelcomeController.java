package  org.example.tarea.Controller;

import org.example.tarea.Model.User;
import org.example.tarea.Service.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class WelcomeController {

    @FXML private Label welcomeLabel;
    @FXML private Label userInfoLabel;
    @FXML private Label registrationDateLabel;

    private userservice authService;

    @FXML
    private void initialize() {
        authService = AuthService.getInstance();
        displayUserInfo();
    }

    private void displayUserInfo() {
        User currentUser = authService.getCurrentUser();
        if (currentUser != null) {
            welcomeLabel.setText("¡Bienvenido a CarApp, " + currentUser.getNombreCompleto() + "!");

            userInfoLabel.setText("Usuario: " + currentUser.getUsername() +
                    " | Correo: " + currentUser.getCorreo() +
                    " | Fecha de nacimiento: " +
                    currentUser.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            registrationDateLabel.setText("Te registraste el: " +
                    currentUser.getFechaRegistro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm")));
        }
    }

    @FXML
    private void handleLogout() {
        authService.logout();
        openLoginWindow();
    }

    private void openLoginWindow() {
        try {
            Stage currentStage = (Stage) welcomeLabel.getScene().getWindow();

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
}