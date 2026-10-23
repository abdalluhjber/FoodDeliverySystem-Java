package com.fooddelivery;

public class Drink extends MenuItem {
    private boolean large;
    private static final double LARGE_SURCHARGE = 1.0;

    public Drink(String name, double basePrice, boolean large) {
        super(name, basePrice);
        this.large = large;
    }

    @Override
    public double getPrice() {
        if (large) {
            return getBasePrice() + LARGE_SURCHARGE;
        } else {
            return getBasePrice();
        }
    }
}
