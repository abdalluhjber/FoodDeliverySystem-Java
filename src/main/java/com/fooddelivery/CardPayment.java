package com.fooddelivery;

public class CardPayment implements PaymentMethod {
    private static final double FEE = 0.02;

    @Override
    public double pay(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Payment amount cannot be negative.");
        }
        return amount * (1.0 + FEE);
    }
}
