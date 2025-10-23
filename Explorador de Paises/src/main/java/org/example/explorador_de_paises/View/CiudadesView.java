package org.example.explorador_de_paises.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.explorador_de_paises.Controller.CiudadesController;
import org.example.explorador_de_paises.Model.City;

import javafx.scene.control.TableColumn;

public class CiudadesView extends Stage {

    // Componentes que el controlador debe manipular
    public Label lblTituloPais = new Label("Ciudades de:");
    public TableView<City> tablaCiudades = new TableView<>();
    public Button btnCerrar = new Button("Cerrar");

    public CiudadesView(CiudadesController controller) {

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setPrefSize(600, 450);

        root.getStylesheets().add(getClass().getResource("/org/example/explorador_de_paises/Styles.css").toExternalForm());

        lblTituloPais.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        configurarTablaCiudades();

        btnCerrar.setOnAction(e -> controller.cerrarVentana());

        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.getChildren().add(btnCerrar);

        root.getChildren().addAll(lblTituloPais, tablaCiudades, bottomBox);

        this.setScene(new Scene(root));
        this.setTitle("Ciudades del País");
    }

    /**
     * Configura las columnas de la tabla de ciudades.
     */
    private void configurarTablaCiudades() {

        TableColumn<City, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNombre.setPrefWidth(200);

        TableColumn<City, String> colDistrito = new TableColumn<>("Distrito");
        colDistrito.setCellValueFactory(new PropertyValueFactory<>("district"));
        colDistrito.setPrefWidth(150);

        TableColumn<City, Integer> colPoblacion = new TableColumn<>("Población");
        colPoblacion.setCellValueFactory(new PropertyValueFactory<>("population"));
        colPoblacion.setPrefWidth(150);

        tablaCiudades.getColumns().addAll(colNombre, colDistrito, colPoblacion);
    }

    public void close() {
    }
}