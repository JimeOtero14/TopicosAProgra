package org.example.tarea;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.tarea.TerminalBuilder.terminalView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
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
        Thread TerminalThread = new Thread(() -> {
            try {
                terminalView.run();
            } catch (Throwable t) {
                System.err.println("Programa terminal finalizó: " + t.getMessage());
            }
        }, "Terminal-Thread");
        // Hilo daemon para no bloquear el cierre de la app JavaFX, de no tener este hilo
        // JavaFX no podría cerrarse hasta que la terminal termine manualmente
        TerminalThread.setDaemon(true);
        TerminalThread.start();
        launch(args);
    }
}