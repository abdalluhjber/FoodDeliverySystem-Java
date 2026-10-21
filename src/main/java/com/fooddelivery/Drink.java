package com.fooddelivery;

/**
 * Concrete menu item representing a Drink.
 * Drink pricing calculation: base price, + $1 if large.
 */
public class Drink extends MenuItem {
    private boolean large;
    private static final double LARGE_SURCHARGE = 1.0;

    public Drink(String name, double basePrice, boolean large) {
        super(name, basePrice);
        this.large = large;
    }

    public boolean isLarge() {
        return large;
    }

    @Override
    public double getPrice() {
        return getBasePrice() + (large ? LARGE_SURCHARGE : 0.0);
    }

    @Override
    public String toString() {
        return String.format("Drink: %s [Size: %s, Unit Price: $%.2f]",
                getName(), (large ? "Large" : "Regular"), getPrice());
    }
}
