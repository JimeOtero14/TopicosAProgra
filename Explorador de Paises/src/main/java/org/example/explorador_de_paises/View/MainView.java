package org.example.explorador_de_paises.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.explorador_de_paises.Controller.MainController;
import org.example.explorador_de_paises.Model.Country; // Usa la clase Country

public class MainView extends BorderPane {

    // Componentes que el controlador necesita acceder y manipular
    public ComboBox<String> continentComboBox = new ComboBox<>();
    public TextField nameTextField = new TextField();
    public TableView<Country> countryTableView = new TableView<>(); // Tipo Country
    public Button searchButton = new Button("Buscar");
    public Button showCitiesButton = new Button("Ver Ciudades");

    // Labels para el panel derecho de detalles
    public Label nameLabel = new Label();
    public Label continentLabel = new Label();
    public Label regionLabel = new Label();
    public Label populationLabel = new Label();
    public Label governmentLabel = new Label();
    public Label headOfStateLabel = new Label();

    public MainView(MainController controller) {

        // Asignación de acciones
        searchButton.setOnAction(e -> controller.cargarPaises());
        showCitiesButton.setOnAction(e -> controller.mostrarVentanaCiudades());

        // Estilos
        // CORRECCIÓN AQUÍ: Usamos ruta relativa si Styles.css está junto a MainView.java
        this.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        this.getStyleClass().add("root-pane");
        this.setPrefSize(1000, 600); // Tamaño sugerido

        // Paneles
        this.setTop(createTopPanel());
        this.setCenter(createCountryTable());
        this.setRight(createDetailsPanel());
    }

    private VBox createTopPanel() {
        VBox topVBox = new VBox(10);
        topVBox.getStyleClass().add("search-panel");
        topVBox.setPadding(new Insets(15));

        Label title = new Label("Explorador de Países - World Database");
        title.getStyleClass().add("title");
        title.setFont(new Font("System Bold", 24));

        HBox controlsHBox = new HBox(10);
        controlsHBox.setAlignment(Pos.CENTER_LEFT);

        nameTextField.setPromptText("Buscar país...");
        nameTextField.setPrefWidth(200);
        searchButton.getStyleClass().add("search-button");

        controlsHBox.getChildren().addAll(
                new Label("Continente:"), continentComboBox,
                new Label("Nombre:"), nameTextField,
                searchButton
        );

        topVBox.getChildren().addAll(title, controlsHBox);
        return topVBox;
    }

    private TableView<Country> createCountryTable() {
        countryTableView.getStyleClass().add("table-view");

        // Configuración de columnas, usando nombres de propiedades en inglés

        TableColumn<Country, String> codeColumn = new TableColumn<>("Código");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        codeColumn.setPrefWidth(80);

        TableColumn<Country, String> nameColumn = new TableColumn<>("País");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(200);

        TableColumn<Country, String> continentColumn = new TableColumn<>("Continente");
        continentColumn.setCellValueFactory(new PropertyValueFactory<>("continent"));
        continentColumn.setPrefWidth(150);

        TableColumn<Country, Integer> populationColumn = new TableColumn<>("Población");
        populationColumn.setCellValueFactory(new PropertyValueFactory<>("population"));
        populationColumn.setPrefWidth(120);

        countryTableView.getColumns().addAll(codeColumn, nameColumn, continentColumn, populationColumn);
        BorderPane.setMargin(countryTableView, new Insets(0, 5, 0, 15)); // Margen para separar del borde izquierdo y del panel derecho
        return countryTableView;
    }

    private VBox createDetailsPanel() {
        VBox detailsVBox = new VBox(15);
        detailsVBox.getStyleClass().add("details-panel");
        detailsVBox.setPadding(new Insets(20));
        detailsVBox.setPrefWidth(300);

        Label title = new Label("Detalles del País");
        title.getStyleClass().add("section-title");
        title.setFont(new Font("System Bold", 18));

        Separator separator = new Separator();

        // Configuración de Labels de detalles (para que el controlador los rellene)
        nameLabel.getStyleClass().add("value-label"); nameLabel.setWrapText(true);
        continentLabel.getStyleClass().add("value-label");
        regionLabel.getStyleClass().add("value-label"); regionLabel.setWrapText(true);
        populationLabel.getStyleClass().add("value-label");
        governmentLabel.getStyleClass().add("value-label"); governmentLabel.setWrapText(true);
        headOfStateLabel.getStyleClass().add("value-label"); headOfStateLabel.setWrapText(true);

        showCitiesButton.getStyleClass().add("cities-button");
        showCitiesButton.setMaxWidth(Double.MAX_VALUE);

        detailsVBox.getChildren().addAll(
                title,
                separator,
                createDetailField("Nombre:", nameLabel),
                createDetailField("Continente:", continentLabel),
                createDetailField("Región:", regionLabel),
                createDetailField("Población:", populationLabel),
                createDetailField("Forma de Gobierno:", governmentLabel),
                createDetailField("Jefe de Estado:", headOfStateLabel),
                showCitiesButton
        );
        return detailsVBox;
    }

    // Metodo auxiliar para construir pares Label-Value
    private VBox createDetailField(String labelText, Label valueLabel) {
        VBox field = new VBox(5);
        Label label = new Label(labelText);
        label.getStyleClass().add("label-bold");
        field.getChildren().addAll(label, valueLabel);
        return field;
    }
}