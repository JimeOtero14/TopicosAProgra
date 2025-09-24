package Model;

/**
 * Clase que representa una cuenta bancaria con sus operaciones básicas.
 * Permite realizar operaciones como retiros, depósitos, transferencias y cambio de NIP.
 *
 * Se implementa el patrón de diseño Builder para la creación flexible y segura
 * de instancias de Cuenta, evitando constructores con muchos parámetros.
 *
 * @author Jimena Otero
 */

public class Cuenta {
    private String numeroCuenta;
    private String pin;
    private double saldo;
    private String titular;

    /**
     * Constructor para crear una nueva cuenta bancaria.
     *
     * Queda disponible por compatibilidad, pero se recomienda usar el Builder.
     *
     * @param numeroCuenta El número único que identifica la cuenta
     * @param pin El PIN de seguridad de la cuenta (4 dígitos)
     * @param saldoInicial El saldo inicial con el que se crea la cuenta
     * @param titular El nombre del titular de la cuenta
     */
    @Deprecated
    public Cuenta(String numeroCuenta, String pin, double saldoInicial, String titular) {
        this.numeroCuenta = numeroCuenta;
        this.pin = pin;
        this.saldo = saldoInicial;
        this.titular = titular;
    }

    /**
     * Constructor privado utilizado por el Builder.
     */
    private Cuenta(Builder builder) {
        this.numeroCuenta = builder.numeroCuenta;
        this.pin = builder.pin;
        this.saldo = builder.saldoInicial;
        this.titular = builder.titular;
    }

    /**
     * Obtiene el número de cuenta.
     *
     * @return El número de cuenta
     */

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * Obtiene el PIN de la cuenta.
     *
     * @return El PIN de la cuenta
     */

    public String getPin() {
        return pin;
    }

    /**
     * Obtiene el saldo actual de la cuenta.
     *
     * @return El saldo disponible en la cuenta
     */

    public double getSaldo() {
        return saldo;
    }

    /**
     * Obtiene el nombre del titular de la cuenta.
     *
     * @return El nombre del titular
     */

    public String getTitular() {
        return titular;
    }

    // Reglas de negocio

    /**
     * Valida si el PIN ingresado coincide con el PIN de la cuenta.
     *
     * @param pinIngresado El PIN a validar
     * @return true si el PIN es correcto, false en caso contrario
     */

    public boolean validarPin(String pinIngresado){
        return this.pin.equals(pinIngresado);
    }

    /**
     * Realiza un retiro de la cuenta si hay fondos suficientes.
     *
     * @param cantidad La cantidad a retirar (debe ser positiva y menor o igual al saldo)
     * @return true si el retiro fue exitoso, false en caso contrario
     */

    public boolean retirar(double cantidad){
        if (cantidad > 0 && cantidad <= this.saldo){
            saldo -= cantidad;
            return true;
        }
        return false;
    }

    /**
     * Realiza un depósito en la cuenta.
     *
     * @param cantidad La cantidad a depositar (debe ser positiva)
     */

    public void depositar(double cantidad){
        if (cantidad > 0){
            saldo += cantidad;
        }
    }

    // De tarea diseñar los comportamientos restantes transferir, cambiar nip

    /**
     * Transfiere fondos desde esta cuenta a otra cuenta destino.
     *
     * @param cuentaDestino La cuenta que recibirá los fondos
     * @param cantidad La cantidad a transferir (debe ser positiva y menor o igual al saldo)
     * @return true si la transferencia fue exitosa, false en caso contrario
     */

    public boolean transferir(Cuenta cuentaDestino, double cantidad) {
        if (cantidad <= 0) {
            return false;
        }

        if (this.saldo < cantidad) {
            return false;
        }

        if (cuentaDestino == null) {
            return false;
        }

        if (this.retirar(cantidad)) {
            cuentaDestino.depositar(cantidad);
            return true;
        }

        return false;
    }

    /**
     * Cambia el NIP de la cuenta después de validar el PIN actual.
     *
     * @param pinActual El PIN actual de la cuenta
     * @param nuevoPin El nuevo PIN a establecer (debe ser exactamente 4 dígitos)
     * @return true si el cambio fue exitoso, false en caso contrario
     */

    public boolean cambiarNip(String pinActual, String nuevoPin) {
        if (!validarPin(pinActual)) {
            return false;
        }

        if (nuevoPin == null || nuevoPin.trim().isEmpty()) {
            return false;
        }

        if (!nuevoPin.matches("\\d{4}")) {
            return false;
        }

        this.pin = nuevoPin;
        return true;
    }

    /**
     * Builder para crear instancias de Cuenta de manera controlada.
     * Permite validaciones y un estilo fluido en la construcción del objeto.
     */
    public static class Builder {
        private String numeroCuenta;
        private String pin;
        private double saldoInicial;
        private String titular;

        public Builder conNumeroCuenta(String numeroCuenta) {
            this.numeroCuenta = numeroCuenta;
            return this;
        }

        public Builder conPin(String pin) {
            this.pin = pin;
            return this;
        }

        public Builder conSaldoInicial(double saldoInicial) {
            this.saldoInicial = saldoInicial;
            return this;
        }

        public Builder conTitular(String titular) {
            this.titular = titular;
            return this;
        }

        public Cuenta build() {
            if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) {
                throw new IllegalArgumentException("El número de cuenta es requerido");
            }
            if (titular == null || titular.trim().isEmpty()) {
                throw new IllegalArgumentException("El titular es requerido");
            }
            if (pin == null || !pin.matches("\\d{4}")) {
                throw new IllegalArgumentException("El PIN debe contener exactamente 4 dígitos");
            }
            if (saldoInicial < 0) {
                throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
            }
            return new Cuenta(this);
        }
    }
}
