package com.fooddelivery;

public class Meal extends MenuItem {
    private int extraToppings;
    private static final double TOPPING_PRICE = 1.5;

    public Meal(String name, double basePrice, int extraToppings) {
        super(name, basePrice);
        if (extraToppings < 0) {
            throw new IllegalArgumentException("Extra toppings count cannot be negative.");
        }
        this.extraToppings = extraToppings;
    }

    @Override
    public double getPrice() {
        return getBasePrice() + (extraToppings * TOPPING_PRICE);
    }
}
