package Decorators;

import pizza.Pizza;

public class Peperoni extends PizzaDecorator {

    public Peperoni(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescripcion() {
        return pizza.getDescripcion() + " + Peperoni";
    }

    @Override
    public double getCosto() {
        return pizza.getCosto() + 8.0;
    }
}