package com.fooddelivery;

/**
 * Abstract class representing a generic menu item in the restaurant.
 * Each menu item has a name and a base price, and subclasses calculate their
 * final price based on their specific configuration.
 */
public abstract class MenuItem {
    private String name;
    private double basePrice;

    public MenuItem(String name, double basePrice) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Menu item name cannot be null or empty.");
        }
        if (basePrice < 0) {
            throw new IllegalArgumentException("Base price cannot be negative.");
        }
        this.name = name;
        this.basePrice = basePrice;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    /**
     * Calculates the final price of the menu item.
     * Implemented specifically by each concrete subclass (Meal, Drink).
     *
     * @return final price of the item
     */
    public abstract double getPrice();

    @Override
    public String toString() {
        return String.format("%s (Base: $%.2f, Final: $%.2f)", name, basePrice, getPrice());
    }
}
