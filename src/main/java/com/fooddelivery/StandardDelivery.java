package com.fooddelivery;

/**
 * Standard delivery option with a fixed fee of $2.
 */
public class StandardDelivery implements DeliveryOption {
    private static final double FEE = 2.0;

    @Override
    public double getFee() {
        return FEE;
    }

    @Override
    public String toString() {
        return "Standard Delivery ($" + FEE + ")";
    }
}
