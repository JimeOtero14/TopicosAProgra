package View;

import Controller.DiccionarioControlador;
import java.util.Scanner;

/**
 * Vista de consola para interactuar con el usuario
 */
public class VistaConsola {
    private final DiccionarioControlador controlador;
    private final Scanner scanner;

    public VistaConsola(String rutaArchivo) {
        this.controlador = new DiccionarioControlador(rutaArchivo);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Inicia la aplicación y muestra el menú principal
     */
    public void iniciar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    buscarPalabra();
                    break;
                case 2:
                    guardarPalabra();
                    break;
                case 3:
                    borrarPalabra();
                    break;
                case 4:
                    importarPalabras();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("\nADIOS!!");
                    break;
                default:
                    System.out.println("\nOpcion no valida. Intente nuevamente.");
            }

            if (continuar) {
                esperarEnter();
            }
        }

        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("\n===== DICCIONARIO =====");
        System.out.println("1. Buscar palabra");
        System.out.println("2. Guardar palabra");
        System.out.println("3. Borrar palabra");
        System.out.println("4. Importar palabra desde API");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void buscarPalabra() {
        System.out.print("\nIngrese la palabra a buscar: ");
        String palabra = scanner.nextLine();
        String resultado = controlador.buscarPalabra(palabra);
        System.out.println("\n" + resultado);
    }

    private void guardarPalabra() {
        System.out.print("\nIngrese la palabra a guardar: ");
        String palabra = scanner.nextLine();
        String resultado = controlador.guardarPalabra(palabra);
        System.out.println("\n" + resultado);
    }

    private void borrarPalabra() {
        System.out.print("\nIngrese la palabra a borrar: ");
        String palabra = scanner.nextLine();
        String resultado = controlador.borrarPalabra(palabra);
        System.out.println("\n" + resultado);
    }

    private void importarPalabras() {
        System.out.println("\nImportando palabras desde la API...");
        String resultado = controlador.importarPalabrasDesdeAPI(10);
        System.out.println("\n" + resultado);
    }

    private void esperarEnter() {
        System.out.print("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
}
