package Model;

public class Cuenta {
    private String numeroCuenta;
    private String pin;
    private double saldo;
    private String titular;
    public Cuenta(String numeroCuenta, String pin, double saldoInicial, String titular) {
        this.numeroCuenta = numeroCuenta;
        this.pin = pin;
        this.saldo = saldoInicial;
        this.titular = titular;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getPin() {
        return pin;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getTitular() {
        return titular;
    }

    //Reglas de negocio

    public boolean validarPin(String pinIngresado){
        return this.pin.equals(pinIngresado);
    }
    public boolean retirar(double cantidad){
        if (cantidad > 0 && cantidad <= this.saldo){
            saldo -= cantidad;
            return true;
        }
        return false;
    }
    public void dpositar(double cantidad){
        if (cantidad > 0){
            saldo += cantidad;
        }
    }

    //De tarea diseñar los comportamientos restantes transferir, cambiar nip
}
