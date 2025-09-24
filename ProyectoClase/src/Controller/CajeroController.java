package Controller;

import Model.CajeroModel;
import View.CajeroView;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador principal que coordina las operaciones entre el modelo y la vista.
 * Gestiona el flujo de la aplicación y las interacciones del usuario.
 *
 * @author Jimena Otero
 */

public class CajeroController {
    private CajeroModel model;
    private CajeroView view;
    private boolean sistemaActivo;
    private Map<Integer, OperacionStrategy> estrategias;

    private interface OperacionStrategy {
        void ejecutar();
    }

    /**
     * Constructor que inicializa el controlador con el modelo y la vista.
     *
     * @param model El modelo del cajero automático
     * @param view La vista del cajero automático
     */

    public CajeroController(CajeroModel model, CajeroView view){
        this.model = model;
        this.view = view;
        this.sistemaActivo = true;
        inicializarEstrategias();
    }

    private void inicializarEstrategias() {
        estrategias = new HashMap<>();
        estrategias.put(1, () -> {
            double saldo = model.consultarSaldo();
            view.mostrarSaldo(saldo);
        });
        estrategias.put(2, () -> {
            double cantidad = view.solicitarCantidad("Retirar");
            if (cantidad <= 0) {
                view.mostrarMensaje("Cantidad inválida");
                return;
            }
            if (model.realizarRetiro(cantidad)) {
                view.mostrarMensaje("Retiro exitoso de "+cantidad);
            } else {
                view.mostrarMensaje("Fondos insuficientes");
            }
        });
        estrategias.put(3, () -> {
            double cantidad = view.solicitarCantidad("Depositar");
            if (cantidad <= 0) {
                view.mostrarMensaje("Error en la cantidad");
                return;
            }
            if (model.realizarDeposito(cantidad)) {
                view.mostrarMensaje("Depósito exitoso de "+cantidad);
            } else {
                view.mostrarMensaje("Error al procesar el deposito");
            }
        });
        estrategias.put(4, () -> {
            String destino = view.solicitarCuentaDestino();
            double cantidad = view.solicitarCantidad("Transferir");
            if (cantidad <= 0) {
                view.mostrarMensaje("Cantidad inválida");
                return;
            }
            if (model.realizarTransferencia(destino, cantidad)) {
                view.mostrarMensaje("Transferencia exitosa de "+cantidad+" a "+destino);
            } else {
                view.mostrarMensaje("No se pudo completar la transferencia");
            }
        });
        estrategias.put(5, () -> {
            String pinActual = view.solicitarPinActual();
            String nuevoPin = view.solicitarNuevoPin();
            if (model.cambiarNip(pinActual, nuevoPin)) {
                view.mostrarMensaje("PIN cambiado correctamente");
            } else {
                view.mostrarMensaje("No se pudo cambiar el PIN");
            }
        });
    }

    /**
     * Inicia el sistema del cajero automático.
     * Este metodo contiene el bucle principal de la aplicación.
     */

    public void iniciarSistema(){
        view.mostrarBienvenida();
        while (sistemaActivo){
            if(autenticarUsuario()){
                ejecutarMenuPrincipal();
            }else{
                view.mostrarMensaje("Credenciales incorrectas");
            }
        }
        view.mostrarMensaje("Gracias por usar nuestro cajero");
    }

    /**
     * Autentica al usuario solicitando credenciales.
     *
     * @return true si las credenciales son válidas, false en caso contrario
     */

    private boolean autenticarUsuario(){
        String numeroCuenta = view.solicitarNumeroCuenta();
        String pin = view.solicitarPin();
        return model.autenticacion(numeroCuenta, pin);
    }

    /**
     * Ejecuta el menú principal después de una autenticación exitosa.
     * Gestiona las opciones del usuario durante la sesión activa.
     */

    private void ejecutarMenuPrincipal(){
        boolean sessionActive = true;
        while(sessionActive){
            view.mostrarMenuPrincipal(model.getCuentaActual().getTitular());
            int opcion = view.leerOpcion();
            if (opcion == 9) {
                sessionActive = false;
                continue;
            }
            OperacionStrategy estrategia = estrategias.get(opcion);
            if (estrategia != null) {
                estrategia.ejecutar();
            } else {
                view.mostrarMensaje("Opción inválida");
            }
        }
    }

    /**
     * Consulta y muestra el saldo de la cuenta actual.
     */

    public void consultarSaldo(){
        double saldo = model.consultarSaldo();
        view.mostrarSaldo(saldo);
    }

    /**
     * Realiza la operación de retiro de fondos.
     * Valida la cantidad y ejecuta la operación en el modelo.
     */

    private void realizarRetiro(){
        double cantidad = view.solicitarCantidad("Retirar");
        if (cantidad <= 0) {
            view.mostrarMensaje("Cantidad inválida");
            return;
        }
        if (model.realizarRetiro(cantidad)) {
            view.mostrarMensaje("Retiro exitoso de "+cantidad);
        }else{
            view.mostrarMensaje("Fondos insuficientes");
        }
    }

    /**
     * Realiza la operación de depósito de fondos.
     * Valida la cantidad y ejecuta la operación en el modelo.
     */

    public void realizarDeposito(){
        double cantidad = view.solicitarCantidad("Depositar");
        if (cantidad <= 0) {
            view.mostrarMensaje("Error en la cantidad");
            return;
        }
        if (model.realizarDeposito(cantidad)) {
            view.mostrarMensaje("Depósito exitoso de "+cantidad);
        }else{
            view.mostrarMensaje("Error al procesar el deposito");
        }
    }
}
