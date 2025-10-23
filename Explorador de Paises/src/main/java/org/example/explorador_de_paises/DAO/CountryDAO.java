package org.example.explorador_de_paises.DAO;

import org.example.explorador_de_paises.Model.Country;
import org.example.explorador_de_paises.DAO.DatabaseConnection; // Asumiendo que DatabaseConnection está en DAO

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO {

    /**
     * Obtiene la lista de continentes únicos de la base de datos.
     */
    public List<String> getContinents() {
        List<String> continents = new ArrayList<>();
        String sql = "SELECT DISTINCT Continent FROM country ORDER BY Continent";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            continents.add("Todos");
            while (rs.next()) {
                continents.add(rs.getString("Continent"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener continentes: " + e.getMessage());
            e.printStackTrace();
        }
        return continents;
    }

    /**
     * Obtiene una lista de países basada en el continente y un filtro de nombre.
     */
    public List<Country> filterCountries(String continent, String nameFilter) {
        List<Country> countries = new ArrayList<>();

        // Asegúrate de que los nombres de las columnas sean correctos
        String sql = "SELECT Code, Name, Continent, Region, SurfaceArea, IndepYear, Population, " +
                "LifeExpectancy, GNP, GovernmentForm, HeadOfState, Capital FROM country WHERE 1=1";

        List<Object> params = new ArrayList<>();

        if (continent != null && !continent.equals("Todos")) {
            sql += " AND Continent = ?";
            params.add(continent);
        }
        if (nameFilter != null && !nameFilter.trim().isEmpty()) {
            sql += " AND Name LIKE ?";
            params.add("%" + nameFilter.trim() + "%");
        }

        sql += " ORDER BY Name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    // CORRECCIÓN CLAVE PARA ClassCastException:
                    // Usamos rs.getObject() para ver si el valor es NULL.
                    // Si no es NULL, usamos rs.getDouble() para obtener el valor numérico,
                    // evitando el cast directo de BigDecimal a Double que causaba el error.
                    Double lifeExpectancy = rs.getObject("LifeExpectancy") == null ? null : rs.getDouble("LifeExpectancy");
                    Double gnp = rs.getObject("GNP") == null ? null : rs.getDouble("GNP");

                    Country country = new Country(
                            rs.getString("Code"),
                            rs.getString("Name"),
                            rs.getString("Continent"),
                            rs.getString("Region"),
                            rs.getDouble("SurfaceArea"),
                            (Integer) rs.getObject("IndepYear"),
                            rs.getInt("Population"),
                            lifeExpectancy, // Usamos la variable segura
                            gnp,           // Usamos la variable segura
                            rs.getString("GovernmentForm"),
                            rs.getString("HeadOfState"),
                            (Integer) rs.getObject("Capital")
                    );
                    countries.add(country);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al filtrar países: " + e.getMessage());
            e.printStackTrace();
        }
        return countries;
    }
}