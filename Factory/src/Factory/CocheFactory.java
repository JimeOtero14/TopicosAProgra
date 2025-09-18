package Factory;

import Model.Coche;

public class CocheFactory {

    // Factory Method para crear coches
    public static Coche crearCoche(String matricula, String marca, String modelo, int año, double precio) {
        return new Coche(matricula, marca, modelo, año, precio);
    }

    // Factory Method para crear coche por defecto
    public static Coche crearCochePorDefecto() {
        return new Coche("SIN-MAT", "Sin Marca", "Sin Modelo", 2023, 0.0);
    }

    // Factory Method para crear coche de prueba
    public static Coche crearCocheDePrueba() {
        return new Coche("TEST-001", "Toyota", "Corolla", 2022, 25000.0);
    }
}