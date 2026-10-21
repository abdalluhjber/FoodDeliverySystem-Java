package com.fooddelivery;

/**
 * Card payment method: adds a 2% processing fee (FEE = 0.02).
 */
public class CardPayment implements PaymentMethod {
    private static final double FEE = 0.02;

    @Override
    public double pay(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Payment amount cannot be negative.");
        }
        return amount * (1.0 + FEE);
    }

    public static double getFeeRate() {
        return FEE;
    }

    @Override
    public String toString() {
        return String.format("Card Payment (%.0f%% fee)", FEE * 100);
    }
}
