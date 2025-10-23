package org.example.explorador_de_paises.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.explorador_de_paises.DAO.DatabaseConnection;
import org.example.explorador_de_paises.Model.Country;
import org.example.explorador_de_paises.Model.City;
import org.example.explorador_de_paises.View.CiudadesView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CiudadesController {

    private CiudadesView ciudadesView;

    /**
     * Inyecta la referencia de la vista de ciudades.
     */
    public void setCiudadesView(CiudadesView view) {
        this.ciudadesView = view;
    }

    /**
     * Carga las ciudades del país seleccionado en la tabla de la vista.
     * La lógica de acceso a datos está contenida aquí (sin usar CityDAO).
     */
    public void cargarCiudades(Country country) {
        if (country == null) return;

        ciudadesView.lblTituloPais.setText("Ciudades de: " + country.getName());
        String countryCode = country.getCode();

        List<City> cityList = getCitiesFromDatabase(countryCode);

        ObservableList<City> cityData = FXCollections.observableArrayList(cityList);
        ciudadesView.tablaCiudades.setItems(cityData);
    }

    /**
     * FUNCIÓN CRÍTICA: Obtiene las ciudades del país de la base de datos.
     * La lógica de la base de datos está aquí, reemplazando a CityDAO.
     */
    private List<City> getCitiesFromDatabase(String countryCode) {
        List<City> cities = new ArrayList<>();

        String sql = "SELECT ID, Name, CountryCode, District, Population FROM city WHERE CountryCode = ? ORDER BY Population DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, countryCode);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    City city = new City(
                            rs.getInt("ID"),
                            rs.getString("Name"),
                            rs.getString("CountryCode"),
                            rs.getString("District"),
                            rs.getInt("Population")
                    );
                    cities.add(city);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ciudades de la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return cities;
    }

    /**
     * Cierra la ventana de ciudades (acción para el botón "Cerrar").
     */
    public void cerrarVentana() {
        if (ciudadesView != null) {
            ciudadesView.close();
        }
    }
}