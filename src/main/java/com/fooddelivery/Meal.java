package com.fooddelivery;

/**
 * Concrete menu item representing a Meal.
 * Meal pricing calculation: base price + $1.5 per extra topping.
 */
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

    public int getExtraToppings() {
        return extraToppings;
    }

    @Override
    public double getPrice() {
        return getBasePrice() + (extraToppings * TOPPING_PRICE);
    }

    @Override
    public String toString() {
        return String.format("Meal: %s [Extra toppings: %d, Unit Price: $%.2f]",
                getName(), extraToppings, getPrice());
    }
}
