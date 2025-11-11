package org.example.examen3.View;

import com.sun.javafx.charts.Legend;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.examen3.Controller.DiccionarioControlador;

public class Vista extends BorderPane {
    public static Legend countryTableView;
    public ComboBox<String> wordsComboBox = new ComboBox<>();
    public static TextField nameTextField = new TextField();
    public Button searchButton = new Button("Buscar");
    public Button showWordsButton = new Button("Ver Palabras");

    public Label nameLabel = new Label();
    public Label WordLabel = new Label();

    public Vista(DiccionarioControlador controller) {

        searchButton.setOnAction(e -> controller.cargarPalabras());
        showWordsButton.setOnAction(e -> controller.mostrarVentanaPalabras());

        this.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        this.getStyleClass().add("root-pane");
        this.setPrefSize(1000, 600);

        this.setTop(createTopPanel());
        this.setRight(createDetailsPanel());
    }

    public static void showAndWait() {
        
    }

    public static ChoiceDialog<Object> getSelectionModel() {
        return null;
    }

    private Node createDetailsPanel() {
        return new VBox(wordsComboBox, nameTextField, searchButton, showWordsButton);
    }

    private VBox createTopPanel() {
        VBox topVBox = new VBox(10);
        topVBox.getStyleClass().add("search-panel");
        topVBox.setPadding(new Insets(15));

        Label title = new Label("Diccionario Digital - Diccionario Database");
        title.getStyleClass().add("title");
        title.setFont(new Font("System Bold", 24));

        HBox controlsHBox = new HBox(10);
        controlsHBox.setAlignment(Pos.CENTER_LEFT);

        nameTextField.setPromptText("Buscar país...");
        nameTextField.setPrefWidth(200);
        searchButton.getStyleClass().add("search-button");

        controlsHBox.getChildren().addAll(
                new Label("Palabras:"), wordsComboBox,
                new Label("Nombre:"), nameTextField,
                searchButton
        );

        topVBox.getChildren().addAll(title, controlsHBox);
        return topVBox;
    }
    private VBox createDetailField(String labelText, Label valueLabel) {
        VBox field = new VBox(5);
        Label label = new Label(labelText);
        label.getStyleClass().add("label-bold");
        field.getChildren().addAll(label, valueLabel);
        return field;
    }
}