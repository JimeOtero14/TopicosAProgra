package Model;

public class Cliente {
    private String nombre;
    private String email;
    private String telefono;
    private double saldo;

    public Cliente(String nombre, String email, String telefono, double saldo) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.saldo = saldo;
    }


    //Getters
    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public double getSaldo() {
        return saldo;
    }


    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
