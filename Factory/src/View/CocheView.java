package View;

import Model.Coche;
import java.util.List;
import java.util.Scanner;

public class CocheView {
    private Scanner scanner;

    public CocheView() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("\n=== SISTEMA DE GESTIÓN DE COCHES ===");
        System.out.println("1. Agregar coche");
        System.out.println("2. Mostrar todos los coches");
        System.out.println("3. Buscar coche por matrícula");
        System.out.println("4. Buscar coches por marca");
        System.out.println("5. Actualizar coche");
        System.out.println("6. Eliminar coche");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public Coche leerCoche() {
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Año: ");
        int año = scanner.nextInt();
        System.out.print("Precio: ");
        double precio = scanner.nextDouble();
        scanner.nextLine(); // Limpiar buffer

        return new Coche(matricula, marca, modelo, año, precio);
    }

    public String leerMatricula() {
        System.out.print("Ingrese la matrícula: ");
        return scanner.nextLine();
    }

    public String leerMarca() {
        System.out.print("Ingrese la marca: ");
        return scanner.nextLine();
    }

    public void mostrarCoches(List<Coche> coches) {
        System.out.println("\n=== LISTA DE COCHES ===");
        if (coches.isEmpty()) {
            System.out.println("No hay coches registrados.");
        } else {
            for (int i = 0; i < coches.size(); i++) {
                System.out.println((i + 1) + ". " + coches.get(i));
            }
        }
    }

    public void mostrarCoche(Coche coche) {
        if (coche != null) {
            System.out.println("\n=== COCHE ENCONTRADO ===");
            System.out.println(coche);
        } else {
            System.out.println("Coche no encontrado.");
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void cerrarScanner() {
        scanner.close();
    }
}
