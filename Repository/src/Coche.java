public class Coche {
    private String id;
    private String marca;
    private String modelo;
    private int año;
    private double precio;
    private String color;

    // Constructor
    public Coche(String id, String marca, String modelo, int año, double precio, String color) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.precio = precio;
        this.color = color;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAño() { return año; }
    public void setAño(int año) { this.año = año; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    @Override
    public String toString() {
        return String.format("Coche{id='%s', marca='%s', modelo='%s', año=%d, precio=%.2f, color='%s'}",
                id, marca, modelo, año, precio, color);
    }
}