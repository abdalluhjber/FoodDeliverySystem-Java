package com.fooddelivery;

/**
 * Interface representing a delivery strategy.
 * Allows new delivery options to be added in the future without modifying the Order class.
 */
public interface DeliveryOption {
    /**
     * Returns the delivery fee associated with this delivery method.
     *
     * @return delivery fee amount
     */
    double getFee();
}
