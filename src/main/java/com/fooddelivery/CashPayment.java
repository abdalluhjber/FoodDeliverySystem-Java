package com.fooddelivery;

/**
 * Cash payment method: no extra fee is charged.
 */
public class CashPayment implements PaymentMethod {

    @Override
    public double pay(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Payment amount cannot be negative.");
        }
        return amount;
    }

    @Override
    public String toString() {
        return "Cash Payment (No fee)";
    }
}
