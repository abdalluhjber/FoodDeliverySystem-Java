package com.fooddelivery;

public class CashPayment implements PaymentMethod {

    @Override
    public double pay(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Payment amount cannot be negative.");
        }
        return amount;
    }
}
