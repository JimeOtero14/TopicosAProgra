package org.example.tarea.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.tarea.Model.Usuario;

import java.time.format.DateTimeFormatter;

public class WelcomeController {

    @FXML private Label nombreUsuarioLabel;
    @FXML private Label usernameLabel;
    @FXML private Label fechaRegistroLabel;
    @FXML private Button logoutButton;

    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        mostrarInformacionUsuario();
    }

    private void mostrarInformacionUsuario() {
        if (usuario != null) {
            nombreUsuarioLabel.setText(usuario.getNombreCompleto());
            usernameLabel.setText("@" + usuario.getUsername());

            if (usuario.getFechaRegistro() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                String fechaFormateada = usuario.getFechaRegistro().format(formatter);
                fechaRegistroLabel.setText("Miembro desde: " + fechaFormateada);
            }
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/tarea/View/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root, 450, 500));
            stage.setTitle("Iniciar Sesión");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
