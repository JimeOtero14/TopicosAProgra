/**
 * Interfaz que define el contrato para una fábrica de autos.
 * Cada fábrica concreta deberá implementar el método createCar().
 */
interface CarFactory {
    /**
     * Método para crear un automóvil.
     * @return un objeto que implementa la interfaz Car.
     */
    Car createCar();
}

/**
 * Interfaz que define el comportamiento de un automóvil.
 * Cada auto deberá implementar el método assemble().
 */
interface Car {
    /**
     * Ensambla el automóvil.
     */
    void assemble();
}

/**
 * Clase que representa un automóvil tipo Sedan.
 * Implementa la interfaz Car.
 */
class Sedan implements Car {
    /**
     * Muestra el mensaje de ensamblaje para un Sedan.
     */
    @Override
    public void assemble() {
        System.out.println("Assembling a sedan car.");
    }
}

/**
 * Clase que representa un automóvil tipo SUV.
 * Implementa la interfaz Car.
 */
class SUV implements Car {
    /**
     * Muestra el mensaje de ensamblaje para un SUV.
     */
    @Override
    public void assemble() {
        System.out.println("Assembling an SUV car.");
    }
}

/**
 * Fábrica concreta para crear automóviles tipo Sedan.
 * Implementa la interfaz CarFactory.
 */
class SedanFactory implements CarFactory {
    /**
     * Crea un automóvil tipo Sedan.
     * @return un nuevo objeto de tipo Sedan.
     */
    @Override
    public Car createCar() {
        return new Sedan();
    }
}

/**
 * Fábrica concreta para crear automóviles tipo SUV.
 * Implementa la interfaz CarFactory.
 */
class SUVFactory implements CarFactory {
    /**
     * Crea un automóvil tipo SUV.
     * @return un nuevo objeto de tipo SUV.
     */
    @Override
    public Car createCar() {
        return new SUV();
    }
}

/**
 * Clase principal que demuestra el uso del patrón Factory Method
 * para crear y ensamblar diferentes tipos de automóviles.
 */
public class Main {
    /**
     * Método principal de ejecución.
     * Crea fábricas de autos y ensambla diferentes tipos de vehículos.
     * @param args argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        CarFactory sedanFactory = new SedanFactory();
        Car sedan = sedanFactory.createCar();
        sedan.assemble();

        CarFactory suvFactory = new SUVFactory();
        Car suv = suvFactory.createCar();
        suv.assemble();
    }
}
