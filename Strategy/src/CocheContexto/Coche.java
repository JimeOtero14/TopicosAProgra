package CocheContexto;
import CochesStrategy.Conduccion;

public class Coche {
    private String modelo;
    private Conduccion modoConduccion;

    public Coche(String modelo) {
        this.modelo = modelo;
        this.modoConduccion = new CochesStrategy.Comfort(); // Modo por defecto
    }

    public void setModoConduccion(Conduccion modo) {
        this.modoConduccion = modo;
        System.out.println("Modo cambiado a: " + modo.getNombreModo());
    }

    public void acelerar() {
        System.out.print(modelo + " - ");
        modoConduccion.acelerar();
    }

    public void frenar() {
        System.out.print(modelo + " - ");
        modoConduccion.frenar();
    }

    public void mostrarModoActual() {
        System.out.println(modelo + " está en: " + modoConduccion.getNombreModo());
    }
}