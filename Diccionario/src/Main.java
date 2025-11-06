import View.VistaConsola;

/**
 * Clase principal que inicia la aplicación
 */
public class Main {
    public static void main(String[] args) {
        String rutaArchivo = "diccionario.txt";
        VistaConsola vista = new VistaConsola(rutaArchivo);
        vista.iniciar();
    }
}