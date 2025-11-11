package org.example.examen3.Controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.examen3.DAO.DatabaseConnection;
import org.example.examen3.DAO.PalabraDAO;
import org.example.examen3.Model.DiccionarioRepositorio;
import org.example.examen3.Model.Palabra;
import org.example.examen3.Util.Validador;
import org.example.examen3.View.Vista;

import java.util.List;

public class DiccionarioControlador {
    private final DiccionarioRepositorio repositorio;

    public DiccionarioControlador() {
        this.repositorio = DiccionarioRepositorio.obtenerInstancia(diccionario);
    }

    public String buscarPalabra(String palabra) {
        if (!Validador.esValida(palabra)) {
            return "Entrada invalida. Solo se permiten letras.";
        }

        String palabraNormalizada = Validador.normalizar(palabra);

        if (DatabaseConnection.existe(palabraNormalizada)) {
            return "La palabra '" + palabraNormalizada + "' se encuentra en el diccionario.";
        } else {
            return "La palabra '" + palabraNormalizada + "' no está en el diccionario.";
        }
    }


    public String guardarPalabra(String palabra) {
        if (!Validador.esValida(palabra)) {
            return "Entrada invalida. Solo se permiten letras.";
        }

        String palabraNormalizada = Validador.normalizar(palabra);

        if (DatabaseConnection.existe(palabraNormalizada)) {
            return "La palabra '" + palabraNormalizada + "' ya existe en el diccionario.";
        }

        if (DatabaseConnection.agregar(palabraNormalizada)) {
            return "La palabra '" + palabraNormalizada + "' ha sido agregada exitosamente.";
        } else {
            return "Error al agregar la palabra.";
        }
    }


    public String borrarPalabra(String palabra) {
        if (!Validador.esValida(palabra)) {
            return "Entrada invalida. Solo se permiten letras.";
        }

        String palabraNormalizada = Validador.normalizar(palabra);

        if (!repositorio.existe(palabraNormalizada)) {
            return "No se puede borrar, la palabra '" + palabraNormalizada
                    + "' no está en el diccionario.";
        }

        if (repositorio.eliminar(palabraNormalizada)) {
            return "La palabra '" + palabraNormalizada + "' ha sido eliminada exitosamente.";
        } else {
            return "Error al eliminar la palabra.";
        }
    }

    public static void cargarPalabras() {
        String continent = (String) Vista.getSelectionModel().getSelectedItem();
        String nameFilter = Vista.nameTextField.getText().trim();

        List<Palabra> filteredCountries = PalabraDAO.filterwords(continent, nameFilter);

        ObservableList<Palabra> data = FXCollections.observableArrayList(filteredCountries);
    }

    public void mostrarVentanaPalabras() {
            Palabra wordSelected = (Palabra) Vista.getSelectionModel().getSelectedItem();

            if (wordSelected == null) {
                new Alert(Alert.AlertType.WARNING, "Por favor, selecciona una palabra para ver su significado.", ButtonType.OK).showAndWait();
                return;
            }

            try {
                DiccionarioControlador diccionarioControlador = new DiccionarioControlador();

                DiccionarioControlador.cargarPalabras(wordSelected);
                Vista.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error al abrir la vista: " + e.getMessage(), ButtonType.OK).showAndWait();
            }
        }

    private static void cargarPalabras(Palabra wordSelected) {
    }


}