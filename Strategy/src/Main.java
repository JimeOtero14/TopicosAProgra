import CochesStrategy.*;
import CocheContexto.Coche;

public class Main {
    public static void main(String[] args) {
        Coche miCoche = new Coche("Toyota Corolla");

        System.out.println("=== PRUEBA DE MODOS DE CONDUCCIÓN ===");

        // Modo por defecto (Comfort)
        miCoche.mostrarModoActual();
        miCoche.acelerar();
        miCoche.frenar();

        System.out.println("\n--- Cambiando a Modo Deportivo ---");
        miCoche.setModoConduccion(new Deportivo());
        miCoche.acelerar();
        miCoche.frenar();

        System.out.println("\n--- Cambiando a Modo Eco ---");
        miCoche.setModoConduccion(new Eco());
        miCoche.acelerar();
        miCoche.frenar();

        System.out.println("\n--- Volviendo a Modo Comfort ---");
        miCoche.setModoConduccion(new Comfort());
        miCoche.acelerar();
        miCoche.frenar();
    }
}