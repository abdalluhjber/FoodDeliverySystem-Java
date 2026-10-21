package com.fooddelivery;

/**
 * Represents a single line item in an Order, linking a MenuItem to its chosen quantity.
 */
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

    /**
     * Calculates the total cost for this order line (item final price * quantity).
     *
     * @return line total amount
     */
    public double getLineTotal() {
        return item.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("%d x %s = $%.2f", quantity, item.getName(), getLineTotal());
    }
}
