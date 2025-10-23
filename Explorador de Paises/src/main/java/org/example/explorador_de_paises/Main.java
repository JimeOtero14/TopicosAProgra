package org.example.explorador_de_paises;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.example.explorador_de_paises.Controller.MainController;
import org.example.explorador_de_paises.View.MainView;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {

        // 1. Crear el controlador
        MainController controller = new MainController();

        // 2. Crear la vista (¡Instanciación directa!)
        MainView mainView = new MainView(controller);

        // 3. Inyectar la vista en el controlador (CRÍTICO para evitar el NullPointerException en MainController)
        controller.setMainView(mainView);

        // 4. Configurar listeners y cargar datos (se ejecuta después de que this.mainView está configurado)
        controller.configurarListenerTabla();
        controller.initializeData();

        // 5. Configurar el Stage
        Scene scene = new Scene(mainView, 1000, 600);

        primaryStage.setTitle("Explorador de Países");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(500);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}