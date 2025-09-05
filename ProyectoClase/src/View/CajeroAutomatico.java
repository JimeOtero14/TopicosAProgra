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
     * Este metodo es ahora genérico para soportar cualquier tipo de Cuenta (T extends Cuenta).
     * Para usos típicos, se usa Cuenta por defecto.
     */
    public static <T extends Model.Cuenta> void main(String[] args) {
        CajeroModel model = new CajeroModel();
        CajeroView view = new CajeroView();
        CajeroController controller = new CajeroController(model, view);
        controller.iniciarSistema();
    }
}
