package com.fooddelivery;

public class OrderLine {
    private MenuItem item;
    private int quantity;

    public OrderLine(MenuItem item, int quantity) {
        if (item == null) {
            throw new IllegalArgumentException("Menu item cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.item = item;
        this.quantity = quantity;
    }

    public MenuItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getLineTotal() {
        return item.getPrice() * quantity;
    }
}
