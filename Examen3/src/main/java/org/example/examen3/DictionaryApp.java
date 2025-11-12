package org.example.examen3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación Diccionario Digital
 * Implementa el patrón MVC con JavaFX
 */
public class DictionaryApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainView.fxml"));
            Parent root = loader.load();

            // Configurar la escena
            Scene scene = new Scene(root);

            // Configurar el stage
            primaryStage.setTitle("Diccionario Digital");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.setMinWidth(1000);
            primaryStage.setMinHeight(700);

            // Mostrar la ventana
            primaryStage.show();

            System.out.println("Aplicación iniciada correctamente");

        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        System.out.println("Cerrando aplicación...");
        // Aquí se pueden agregar operaciones de limpieza si es necesario
    }

    /**
     * Método main para lanzar la aplicación
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        launch(args);
    }
}
