package org.example.tarea;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // IMPORTANTE: La ruta debe coincidir con la ubicación del FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/tarea/View/login.fxml"));

            // Verificar si el recurso existe
            if (loader.getLocation() == null) {
                System.err.println("ERROR: No se encontró el archivo login.fxml");
                System.err.println("Verifica que esté en: src/main/resources/org/example/tarea/login.fxml");
                return;
            }

            Parent root = loader.load();
            Scene scene = new Scene(root, 450, 500);

            primaryStage.setTitle("Sistema de Login");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
        }
    }

    @Override
    public void stop() {
        // Cerrar conexión de base de datos al cerrar la aplicación
        org.example.tarea.Util.DatabaseConnection.closeConnection();
        System.out.println("Aplicación cerrada correctamente");
    }

    public static void main(String[] args) {
        launch(args);
    }
}