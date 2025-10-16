package Decorators;

import pizza.Pizza;

public class QuesoExtra extends PizzaDecorator {

    public QuesoExtra(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescripcion() {
        return pizza.getDescripcion() + " + Queso Extra";
    }

    @Override
    public double getCosto() {
        return pizza.getCosto() + 20.0;
    }
}