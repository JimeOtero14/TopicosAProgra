package Controller;

import Model.DAO;
import Model.Coche;
import View.CocheView;
import java.util.List;

public class CocheController {
    private DAO<Coche> cocheDAO;
    private CocheView view;

    public CocheController(DAO<Coche> cocheDAO, CocheView view) {
        this.cocheDAO = cocheDAO;
        this.view = view;
    }

    public void iniciar() {
        int opcion;
        do {
            view.mostrarMenu();
            opcion = obtenerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 7);

        view.cerrarScanner();
    }

    private int obtenerOpcion() {
        try {
            return new java.util.Scanner(System.in).nextInt();
        } catch (Exception e) {
            return 0;
        }
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                agregarCoche();
                break;
            case 2:
                mostrarTodosLosCoches();
                break;
            case 3:
                buscarCochePorMatricula();
                break;
            case 4:
                buscarCochesPorMarca();
                break;
            case 5:
                actualizarCoche();
                break;
            case 6:
                eliminarCoche();
                break;
            case 7:
                view.mostrarMensaje("¡Hasta luego!");
                break;
            default:
                view.mostrarMensaje("Opción inválida. Intente nuevamente.");
        }
    }

    private void agregarCoche() {
        Coche coche = view.leerCoche();
        cocheDAO.agregar(coche);
        view.mostrarMensaje("Coche agregado exitosamente.");
    }

    private void mostrarTodosLosCoches() {
        List<Coche> coches = cocheDAO.obtenerTodos();
        view.mostrarCoches(coches);
    }

    private void buscarCochePorMatricula() {
        String matricula = view.leerMatricula();
        Coche coche = cocheDAO.obtenerPorMatricula(matricula);
        view.mostrarCoche(coche);
    }

    private void buscarCochesPorMarca() {
        String marca = view.leerMarca();
        List<Coche> coches = cocheDAO.buscarPorMarca(marca);
        view.mostrarCoches(coches);
    }

    private void actualizarCoche() {
        String matricula = view.leerMatricula();
        Coche cocheExistente = cocheDAO.obtenerPorMatricula(matricula);

        if (cocheExistente != null) {
            view.mostrarMensaje("Coche actual: " + cocheExistente);
            view.mostrarMensaje("Ingrese los nuevos datos:");
            Coche nuevoCoche = view.leerCoche();

            boolean actualizado = cocheDAO.actualizar(matricula, nuevoCoche);
            if (actualizado) {
                view.mostrarMensaje("Coche actualizado exitosamente.");
            } else {
                view.mostrarMensaje("Error al actualizar el coche.");
            }
        } else {
            view.mostrarMensaje("Coche no encontrado.");
        }
    }

    private void eliminarCoche() {
        String matricula = view.leerMatricula();
        boolean eliminado = cocheDAO.eliminar(matricula);

        if (eliminado) {
            view.mostrarMensaje("Coche eliminado exitosamente.");
        } else {
            view.mostrarMensaje("Coche no encontrado.");
        }
    }
}
