package org.example.tarea;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.tarea.DBConfig.DataConfig;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Iniciando CarApp...");

        // Inicializar la base de datos
        DataConfig.initializeDatabase();

        // Cargar FXML desde resources
        URL fxmlUrl = getClass().getResource("/com/carapp/views/login.fxml");
        if (fxmlUrl == null) {
            System.err.println("No se pudo encontrar login.fxml");
            return;
        }

        Parent root = FXMLLoader.load(fxmlUrl);
        primaryStage.setTitle("CarApp - Login");
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.out.println("Lanzando aplicación...");
        launch(args);
    }
}