package Decorators;

import pizza.Pizza;

public class PizzaDecorator implements Pizza {
    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public String getDescripcion() {
        return pizza.getDescripcion();
    }

    @Override
    public double getCosto() {
        return pizza.getCosto();
    }
}
