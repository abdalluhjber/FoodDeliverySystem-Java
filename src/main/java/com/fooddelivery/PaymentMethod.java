package com.fooddelivery;

/**
 * Interface representing a payment strategy.
 * Different payment methods may apply additional processing fees.
 */
public interface PaymentMethod {
    /**
     * Processes payment for a given order total and returns the final charged amount.
     *
     * @param amount the order total to pay
     * @return final amount charged after any payment-method-specific fee
     */
    double pay(double amount);
}
