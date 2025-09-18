package View;

import Model.DAO;
import Model.Coche;
import Controller.CocheController;
import Factory.CocheFactory;

public class Main {
    public static void main(String[] args) {
        // Crear instancias
        DAO<Coche> cocheDAO = new DAO<>();
        CocheView view = new CocheView();
        CocheController controller = new CocheController(cocheDAO, view);

        // Agregar algunos coches de ejemplo usando Factory
        cocheDAO.agregar(CocheFactory.crearCoche("ABC-123", "Toyota", "Corolla", 2022, 25000.0));
        cocheDAO.agregar(CocheFactory.crearCoche("XYZ-789", "Honda", "Civic", 2023, 27000.0));
        cocheDAO.agregar(CocheFactory.crearCoche("DEF-456", "Ford", "Focus", 2021, 22000.0));
        cocheDAO.agregar(CocheFactory.crearCoche("GHI-789", "Toyota", "RAV4", 2023, 32000.0));

        // Iniciar la aplicación
        controller.iniciar();
    }
}
