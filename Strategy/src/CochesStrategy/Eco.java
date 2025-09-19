package CochesStrategy;

public class Eco implements Conduccion {
    public void acelerar() {
        System.out.println("Acelerando suavemente para ahorrar combustible");
    }

    public void frenar() {
        System.out.println("Frenando gradualmente para recuperar energía");
    }

    public String getNombreModo() {
        return "Modo Eco";
    }
}