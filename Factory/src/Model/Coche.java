package Model;

public class Coche {
    private String matricula;
    private String marca;
    private String modelo;
    private int año;
    private double precio;

    public Coche(String matricula, String marca, String modelo, int año, double precio) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.precio = precio;
    }

    // Getters y Setters
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAño() { return año; }
    public void setAño(int año) { this.año = año; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public String toString() {
        return String.format("Matrícula: %s, Marca: %s, Modelo: %s, Año: %d, Precio: $%.2f",
                matricula, marca, modelo, año, precio);
    }
}
