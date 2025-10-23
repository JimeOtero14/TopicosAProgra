package org.example.explorador_de_paises.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import org.example.explorador_de_paises.DAO.CountryDAO;
import org.example.explorador_de_paises.Model.Country;
import org.example.explorador_de_paises.View.CiudadesView;
import org.example.explorador_de_paises.View.MainView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MainController {

    private MainView mainView;
    private final CountryDAO countryDAO = new CountryDAO();
    private final NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());

    public void setMainView(MainView view) {
        this.mainView = view;
    }

    public void initializeData() {
        List<String> continents = countryDAO.getContinents();
        mainView.continentComboBox.setItems(FXCollections.observableArrayList(continents));
        mainView.continentComboBox.getSelectionModel().selectFirst();

        cargarPaises();
    }

    public void configurarListenerTabla() {
        // Al llamar a esto, mainView ya no es null gracias a la corrección en Main.java
        mainView.countryTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    mostrarDetallesPais(newSelection);
                });
    }

    public void cargarPaises() {
        String continent = mainView.continentComboBox.getSelectionModel().getSelectedItem();
        String nameFilter = mainView.nameTextField.getText().trim();

        List<Country> filteredCountries = countryDAO.filterCountries(continent, nameFilter);

        ObservableList<Country> data = FXCollections.observableArrayList(filteredCountries);
        mainView.countryTableView.setItems(data);
    }

    private void mostrarDetallesPais(Country country) {
        if (country == null) {
            mainView.nameLabel.setText("");
            mainView.continentLabel.setText("");
            mainView.regionLabel.setText("");
            mainView.populationLabel.setText("");
            mainView.governmentLabel.setText("");
            mainView.headOfStateLabel.setText("");
            return;
        }

        String formattedPopulation = numberFormat.format(country.getPopulation());
        String headOfState = country.getHeadOfState();

        mainView.nameLabel.setText(country.getName());
        mainView.continentLabel.setText(country.getContinent());
        mainView.regionLabel.setText(country.getRegion());
        mainView.populationLabel.setText(formattedPopulation);
        mainView.governmentLabel.setText(country.getGovernmentForm());
        mainView.headOfStateLabel.setText(headOfState != null && !headOfState.isEmpty() ? headOfState : "N/A");
    }

    public void mostrarVentanaCiudades() {
        Country countrySelected = mainView.countryTableView.getSelectionModel().getSelectedItem();

        if (countrySelected == null) {
            new Alert(Alert.AlertType.WARNING, "Por favor, selecciona un país para ver sus ciudades.", ButtonType.OK).showAndWait();
            return;
        }

        try {
            // Nota: Este código asume que CiudadesController y CiudadesView existen y manejan la lógica de DB
            CiudadesController ciudadesController = new CiudadesController();
            CiudadesView ciudadesView = new CiudadesView(ciudadesController);
            ciudadesController.setCiudadesView(ciudadesView);

            ciudadesView.setTitle("Ciudades de " + countrySelected.getName());
            ciudadesView.initModality(Modality.WINDOW_MODAL);
            ciudadesView.initOwner(mainView.getScene().getWindow());

            ciudadesController.cargarCiudades(countrySelected);
            ciudadesView.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error al abrir la vista de ciudades: " + e.getMessage(), ButtonType.OK).showAndWait();
        }
    }
}