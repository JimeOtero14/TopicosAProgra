package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que modela el comportamiento de un cajero automático.
 * Gestiona la autenticación de usuarios y las operaciones bancarias.
 *
 * @author Jimena Otero
 */

public class CajeroModel {
    private Map<String, Cuenta> cuentas;
    private Cuenta cuentaActual;

    /**
     * Constructor que inicializa el cajero con cuentas predefinidas.
     */

    public CajeroModel() {
        cuentas = new HashMap<String, Cuenta>();
        inicializarCuentas();
    }

    /**
     * Inicializa el cajero con cuentas de ejemplo para pruebas.
     */

    private void inicializarCuentas() {
        cuentas.put("12345", new Cuenta("12345", "1111", 5000, "Juan Pérez"));
        cuentas.put("67890", new Cuenta("67890", "2222", 3000, "María García"));
        cuentas.put("34567", new Cuenta("34567", "3333", 2000, "Carlos Sánchez"));
        cuentas.put("89012", new Cuenta("89012", "4444", 1000, "Ana Rodríguez"));
    }

    /**
     * Autentica a un usuario mediante número de cuenta y PIN.
     *
     * @param numeroCuenta El número de cuenta a autenticar
     * @param pin El PIN asociado a la cuenta
     * @return true si la autenticación es exitosa, false en caso contrario
     */

    public boolean autenticacion(String numeroCuenta, String pin) {
        Cuenta cuenta = cuentas.get(numeroCuenta);
        if (cuenta != null && cuenta.validarPin(pin)) {
            this.cuentaActual = cuenta;
            return true;
        }
        return false;
    }

    /**
     * Obtiene la cuenta actualmente autenticada en el cajero.
     *
     * @return La cuenta autenticada o null si no hay sesión activa
     */

    public Cuenta getCuentaActual() {
        return this.cuentaActual;
    }

    /**
     * Consulta el saldo de la cuenta actualmente autenticada.
     *
     * @return El saldo de la cuenta autenticada o 0 si no hay sesión activa
     */

    public double consultarSaldo() {
        return this.cuentaActual != null ? cuentaActual.getSaldo() : 0;
    }

    /**
     * Realiza un retiro de la cuenta autenticada.
     *
     * @param cantidad La cantidad a retirar
     * @return true si el retiro fue exitoso, false en caso contrario
     */

    public boolean realizarRetiro(double cantidad) {
        return cuentaActual != null && cuentaActual.retirar(cantidad);
    }

    /**
     * Realiza un depósito en la cuenta autenticada.
     *
     * @param cantidad La cantidad a depositar (debe ser positiva)
     * @return true si el depósito fue exitoso, false en caso contrario
     */

    public boolean realizarDeposito(double cantidad) {
        if (cuentaActual != null && cantidad > 0) {
            cuentaActual.depositar(cantidad);
            return true;
        }
        return false;
    }

    /**
     * Verifica si existe una cuenta con el número especificado.
     *
     * @param numeroCuenta El número de cuenta a verificar
     * @return true si la cuenta existe, false en caso contrario
     */

    public boolean cuentaExistente(String numeroCuenta) {
        return cuentas.containsKey(numeroCuenta);
    }

    /**
     * Realiza una transferencia desde la cuenta autenticada a otra cuenta.
     *
     * @param numeroCuentaDestino El número de cuenta destino
     * @param cantidad La cantidad a transferir
     * @return true si la transferencia fue exitosa, false en caso contrario
     */

    //Definir el metodo para transferir
        public boolean realizarTransferencia(String numeroCuentaDestino, double cantidad) {

        if (cuentaActual == null) {
            return false;
        }

        if (cantidad <= 0) {
            return false;
        }

        if (numeroCuentaDestino.equals(cuentaActual.getNumeroCuenta())) {
            return false;
        }

        Cuenta cuentaDestino = cuentas.get(numeroCuentaDestino);
        if (cuentaDestino == null) {
            return false;
        }
        return cuentaActual.transferir(cuentaDestino, cantidad);
    }

    /**
     * Cambia el NIP de la cuenta autenticada.
     *
     * @param pinActual El PIN actual de la cuenta
     * @param nuevoPin El nuevo PIN a establecer
     * @return true si el cambio fue exitoso, false en caso contrario
     */

    public boolean cambiarNip(String pinActual, String nuevoPin) {
        if (cuentaActual == null) {
            return false;
        }

        return cuentaActual.cambiarNip(pinActual, nuevoPin);
    }
}


