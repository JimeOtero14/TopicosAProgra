package View;

import Controller.CajeroController;
import Model.CajeroModel;

/**
 * Clase principal que inicia la aplicación del cajero automático.
 * Esta clase contiene el metodo main que sirve como punto de entrada
 * para la ejecución del sistema.
 *
 * @author Jimena Otero
 */

public class CajeroAutomatico {

        /**
     * Metodo principal que inicia la aplicación del cajero automático.
     * Crea las instancias del modelo, vista y controlador, y pone en
     * marcha el sistema.
     *
     * @param args Argumentos de línea de comandos (no utilizados en esta aplicación)
     */

    public static void main(String[] args) {

        CajeroModel model = new CajeroModel();
        CajeroView view = new CajeroView();
        CajeroController controller = new CajeroController(model, view);
        controller.iniciarSistema();
    }
}
