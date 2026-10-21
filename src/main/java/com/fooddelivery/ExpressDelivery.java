package com.fooddelivery;

/**
 * Express delivery option with a fixed fee of $5.
 */
public class ExpressDelivery implements DeliveryOption {
    private static final double FEE = 5.0;

    @Override
    public double getFee() {
        return FEE;
    }

    @Override
    public String toString() {
        return "Express Delivery ($" + FEE + ")";
    }
}
