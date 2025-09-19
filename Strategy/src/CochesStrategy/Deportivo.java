package CochesStrategy;

public class Deportivo implements Conduccion {
    public void acelerar() {
        System.out.println("¡ACELERACIÓN MÁXIMA! Motor a altas revoluciones");
    }

    public void frenar() {
        System.out.println("Frenada deportiva con ABS activo");
    }

    public String getNombreModo() {
        return "Modo Deportivo";
    }
}