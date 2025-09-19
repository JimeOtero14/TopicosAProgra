package CochesStrategy;

public class Comfort implements Conduccion {
    public void acelerar() {
        System.out.println("Aceleración suave y confortable");
    }

    public void frenar() {
        System.out.println("Frenado suave para máximo confort");
    }

    public String getNombreModo() {
        return "Modo Comfort";
    }
}