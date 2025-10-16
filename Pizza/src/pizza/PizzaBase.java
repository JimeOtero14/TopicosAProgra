package pizza;

public class PizzaBase implements Pizza {

    @Override
    public String getDescripcion() {
        return "Pizza base (masa + salsa + queso)";
    }

    @Override
    public double getCosto() {
        return 80.0; // Precio base de la pizza
    }
}