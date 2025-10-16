package Decorators;

import pizza.Pizza;

public class Champiniones extends PizzaDecorator {

    public Champiniones(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescripcion() {
        return pizza.getDescripcion() + " + Champiñones";
    }

    @Override
    public double getCosto() {
        return pizza.getCosto() + 10.0;
    }
}