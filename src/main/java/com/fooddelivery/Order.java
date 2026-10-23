package com.fooddelivery;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int counter = 1000;

    private int orderNumber;
    private String customerName;
    private List<OrderLine> lines;
    private OrderStatus status;
    private DeliveryOption delivery;

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

    // Adds one unit of the item
    public void addItem(MenuItem item) {
        addItem(item, 1);
    }

    // Adds the item with the specified quantity
    public void addItem(MenuItem item, int qty) {
        if (this.status != OrderStatus.NEW) {
            System.out.println("Cannot add items: order is not NEW (current status: " + status + ").");
            return;
        }
        if (item == null) {
            throw new IllegalArgumentException("Menu item cannot be null.");
        }
        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.lines.add(new OrderLine(item, qty));
    }

    public double getSubtotal() {
        double subtotal = 0.0;
        for (OrderLine line : lines) {
            subtotal += line.getLineTotal();
        }
        return subtotal;
    }

    public double getTotal() {
        return getSubtotal() + delivery.getFee();
    }

    public void pay(PaymentMethod method) {
        if (this.status != OrderStatus.NEW) {
            System.out.println("Cannot pay: order is not NEW (current status: " + status + ").");
            return;
        }
        if (lines.isEmpty()) {
            System.out.println("Cannot pay: order has no items.");
            return;
        }
        if (method == null) {
            throw new IllegalArgumentException("Payment method cannot be null.");
        }
        method.pay(getTotal());
        this.status = OrderStatus.PAID;
    }

    public void deliver() {
        if (this.status != OrderStatus.PAID) {
            System.out.println("Cannot deliver: order is not PAID (current status: " + status + ").");
            return;
        }
        this.status = OrderStatus.DELIVERED;
    }

    public void cancel() {
        if (this.status != OrderStatus.NEW) {
            System.out.println("Cannot cancel: order is not NEW (current status: " + status + ").");
            return;
        }
        this.status = OrderStatus.CANCELLED;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public DeliveryOption getDelivery() {
        return delivery;
    }
}
