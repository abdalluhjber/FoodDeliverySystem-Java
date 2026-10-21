package com.fooddelivery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a customer order in the Food Delivery System.
 * Manages order lifecycle, order lines, calculations, delivery, and payment.
 */
public class Order {
    private static int counter = 1000;

    private int orderNumber;
    private String customerName;
    private List<OrderLine> lines;
    private OrderStatus status;
    private DeliveryOption delivery;

    /**
     * Constructs a new Order with the specified customer name and delivery option.
     * Generates a unique sequential order number starting from 1001.
     * Sets initial status to NEW.
     *
     * @param customerName name of the customer placing the order
     * @param delivery     chosen delivery option
     */
    public Order(String customerName, DeliveryOption delivery) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty.");
        }
        if (delivery == null) {
            throw new IllegalArgumentException("Delivery option cannot be null.");
        }
        this.orderNumber = ++counter;
        this.customerName = customerName;
        this.delivery = delivery;
        this.lines = new ArrayList<>();
        this.status = OrderStatus.NEW;
    }

    /**
     * Adds an item to the order with a default quantity of 1.
     * Items can only be added while the order is NEW.
     *
     * @param item the menu item to add
     */
    public void addItem(MenuItem item) {
        addItem(item, 1);
    }

    /**
     * Adds an item to the order with a specified quantity.
     * Items can only be added while the order is NEW.
     *
     * @param item the menu item to add
     * @param qty  the quantity of the item
     */
    public void addItem(MenuItem item, int qty) {
        if (this.status != OrderStatus.NEW) {
            throw new IllegalStateException("Items can only be added while the order is NEW. Current status: " + status);
        }
        if (item == null) {
            throw new IllegalArgumentException("Menu item cannot be null.");
        }
        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.lines.add(new OrderLine(item, qty));
    }

    /**
     * Calculates the subtotal of the order: sum of (item price * quantity).
     *
     * @return subtotal amount
     */
    public double getSubtotal() {
        double subtotal = 0.0;
        for (OrderLine line : lines) {
            subtotal += line.getLineTotal();
        }
        return subtotal;
    }

    /**
     * Calculates the total of the order: Subtotal + delivery fee.
     *
     * @return total order amount
     */
    public double getTotal() {
        return getSubtotal() + delivery.getFee();
    }

    /**
     * Processes payment for the order using the provided payment method.
     * The order becomes PAID after payment.
     * Payment can only be made on a NEW order with at least one item.
     *
     * @param method the payment method to execute the transaction
     */
    public void pay(PaymentMethod method) {
        if (this.status != OrderStatus.NEW) {
            throw new IllegalStateException("Payment can only be processed for a NEW order. Current status: " + status);
        }
        if (lines.isEmpty()) {
            throw new IllegalStateException("Cannot pay for an empty order. Please add at least one item.");
        }
        if (method == null) {
            throw new IllegalArgumentException("Payment method cannot be null.");
        }

        // Process payment with method fee applied if any
        double charged = method.pay(getTotal());
        this.status = OrderStatus.PAID;
    }

    /**
     * Marks the order as DELIVERED.
     * Rule: Only a PAID order can be delivered.
     */
    public void deliver() {
        if (this.status != OrderStatus.PAID) {
            throw new IllegalStateException("Only a PAID order can be delivered. Current status: " + status);
        }
        this.status = OrderStatus.DELIVERED;
    }

    /**
     * Cancels the order.
     * Rule: Only a NEW order can be cancelled.
     */
    public void cancel() {
        if (this.status != OrderStatus.NEW) {
            throw new IllegalStateException("Only a NEW order can be cancelled. Current status: " + status);
        }
        this.status = OrderStatus.CANCELLED;
    }

    // Getters
    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<OrderLine> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public DeliveryOption getDelivery() {
        return delivery;
    }

    /**
     * Helper method to reset the static counter (useful for unit testing).
     */
    public static void resetCounter(int initialValue) {
        counter = initialValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Order #%d | Customer: %s | Status: %s | Delivery: %s%n",
                orderNumber, customerName, status, delivery.getClass().getSimpleName()));
        sb.append("Order Lines:\n");
        for (OrderLine line : lines) {
            sb.append(String.format("  - %s (Qty: %d) => $%.2f%n",
                    line.getItem().getName(), line.getQuantity(), line.getLineTotal()));
        }
        sb.append(String.format("Subtotal: $%.2f%n", getSubtotal()));
        sb.append(String.format("Delivery Fee: $%.2f%n", delivery.getFee()));
        sb.append(String.format("Total: $%.2f%n", getTotal()));
        return sb.toString();
    }
}
