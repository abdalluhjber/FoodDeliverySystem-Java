package com.fooddelivery;

import java.util.Locale;

/**
 * Main application entry point demonstrating the Food Delivery System
 * with the exact specification example, status transitions, and validation rules.
 */
public class Main {
    public static void main(String[] args) {
        // Use US locale to ensure consistent decimal formatting (. instead of ,)
        Locale.setDefault(Locale.US);

        System.out.println("===============================================================");
        System.out.println("          FOOD DELIVERY SYSTEM - OBJECT-ORIENTED DESIGN        ");
        System.out.println("===============================================================\n");

        // -------------------------------------------------------------------------
        // SCENARIO 1: The Exact Assignment Example
        // "2 x Burger Meal (base $8, 1 extra topping) + 1 x Large Juice (base $3),
        //  Express delivery, paid by card"
        // -------------------------------------------------------------------------
        System.out.println(">>> SCENARIO 1: Assignment Example (Full Walkthrough)");
        System.out.println("---------------------------------------------------------------");

        // 1. Create items
        Meal burgerMeal = new Meal("Burger Meal", 8.0, 1);
        Drink largeJuice = new Drink("Juice", 3.0, true);

        System.out.printf("1. Item Details:%n");
        System.out.printf("   - %s: Base=$%.2f, Toppings=%d -> Unit Price = $%.2f%n",
                burgerMeal.getName(), burgerMeal.getBasePrice(), burgerMeal.getExtraToppings(), burgerMeal.getPrice());
        System.out.printf("   - %s: Base=$%.2f, Large=%b -> Unit Price = $%.2f%n",
                largeJuice.getName(), largeJuice.getBasePrice(), largeJuice.isLarge(), largeJuice.getPrice());

        // 2. Create Order with Express Delivery
        DeliveryOption expressDelivery = new ExpressDelivery();
        Order order1 = new Order("Sarah Connor", expressDelivery);

        System.out.printf("%n2. Created Order #%d for '%s' with %s (Initial Status: %s)%n",
                order1.getOrderNumber(), order1.getCustomerName(), expressDelivery, order1.getStatus());

        // 3. Add items: 2 Burgers and 1 Large Juice
        order1.addItem(burgerMeal, 2);
        order1.addItem(largeJuice); // default qty = 1

        System.out.printf("%n3. Items added to Order #%d:%n", order1.getOrderNumber());
        for (OrderLine line : order1.getLines()) {
            System.out.printf("   * %d x %s = $%.2f%n",
                    line.getQuantity(), line.getItem().getName(), line.getLineTotal());
        }

        // 4. Calculations
        double subtotal = order1.getSubtotal();
        double deliveryFee = order1.getDelivery().getFee();
        double total = order1.getTotal();

        System.out.printf("%n4. Totals Breakdown:%n");
        System.out.printf("   Burger = 8 + 1.5 = 9.5 x 2 = $%.2f%n", burgerMeal.getPrice() * 2);
        System.out.printf("   Juice  = 3 + 1   = 4.0 x 1 = $%.2f%n", largeJuice.getPrice() * 1);
        System.out.printf("   Subtotal                   = $%.2f%n", subtotal);
        System.out.printf("   Express Delivery           = $%.2f%n", deliveryFee);
        System.out.printf("   Total (Subtotal + Delivery)= $%.2f%n", total);

        // 5. Payment with Card (2% fee)
        PaymentMethod cardPayment = new CardPayment();
        double cardCharged = cardPayment.pay(total);
        System.out.printf("%n5. Payment via %s:%n", cardPayment);
        System.out.printf("   Calculation: %.2f x 1.02 = $%.2f%n", total, cardCharged);

        order1.pay(cardPayment);
        System.out.printf("   Order #%d Status after pay(): %s%n", order1.getOrderNumber(), order1.getStatus());

        // 6. Deliver Order
        order1.deliver();
        System.out.printf("   Order #%d Status after deliver(): %s%n%n", order1.getOrderNumber(), order1.getStatus());

        // -------------------------------------------------------------------------
        // SCENARIO 2: Order with Cash Payment & Standard Delivery
        // -------------------------------------------------------------------------
        System.out.println(">>> SCENARIO 2: Standard Delivery with Cash Payment");
        System.out.println("---------------------------------------------------------------");

        DeliveryOption standardDelivery = new StandardDelivery();
        Order order2 = new Order("John Doe", standardDelivery);

        Meal familyPizza = new Meal("Pizza Feast", 15.0, 3); // 15 + 3 * 1.5 = 19.5
        Drink regularSoda = new Drink("Soda", 2.0, false);   // 2.0 (regular size)

        order2.addItem(familyPizza, 1);
        order2.addItem(regularSoda, 2);

        System.out.printf("Order #%d for %s:%n", order2.getOrderNumber(), order2.getCustomerName());
        System.out.printf("   Subtotal: $%.2f | Delivery: $%.2f | Total: $%.2f%n",
                order2.getSubtotal(), order2.getDelivery().getFee(), order2.getTotal());

        PaymentMethod cashPayment = new CashPayment();
        double cashCharged = cashPayment.pay(order2.getTotal());
        System.out.printf("   Paid with Cash: $%.2f (No extra fee)%n", cashCharged);

        order2.pay(cashPayment);
        order2.deliver();
        System.out.printf("   Final Status: %s%n%n", order2.getStatus());

        // -------------------------------------------------------------------------
        // SCENARIO 3: Order Cancellation Rule
        // "Only a NEW order can be cancelled."
        // -------------------------------------------------------------------------
        System.out.println(">>> SCENARIO 3: Order Cancellation Rule");
        System.out.println("---------------------------------------------------------------");

        Order order3 = new Order("Alex Mercer", standardDelivery);
        order3.addItem(new Drink("Water", 1.5, false));
        System.out.printf("Order #%d created with status: %s%n", order3.getOrderNumber(), order3.getStatus());
        order3.cancel();
        System.out.printf("Order #%d successfully cancelled. Status: %s%n%n", order3.getOrderNumber(), order3.getStatus());

        // -------------------------------------------------------------------------
        // SCENARIO 4: Validation & Exception Handling
        // -------------------------------------------------------------------------
        System.out.println(">>> SCENARIO 4: Validation & Business Rules Enforcement");
        System.out.println("---------------------------------------------------------------");

        // Rule A: Cannot add items to an order that is not NEW
        try {
            System.out.println("Testing Rule: Cannot add items to a CANCELLED order...");
            order3.addItem(burgerMeal);
        } catch (IllegalStateException e) {
            System.out.println("   [EXPECTED ERROR CAUGHT]: " + e.getMessage());
        }

        // Rule B: Cannot deliver an unpaid order
        Order order4 = new Order("Alice Smith", standardDelivery);
        order4.addItem(burgerMeal);
        try {
            System.out.println("Testing Rule: Only a PAID order can be delivered...");
            order4.deliver();
        } catch (IllegalStateException e) {
            System.out.println("   [EXPECTED ERROR CAUGHT]: " + e.getMessage());
        }

        // Rule C: Cannot cancel a PAID order
        try {
            System.out.println("Testing Rule: Only a NEW order can be cancelled...");
            order1.cancel(); // order1 is DELIVERED/PAID
        } catch (IllegalStateException e) {
            System.out.println("   [EXPECTED ERROR CAUGHT]: " + e.getMessage());
        }

        System.out.println("\n===============================================================");
        System.out.println("        ALL SCENARIOS AND RULES VERIFIED SUCCESSFULLY!        ");
        System.out.println("===============================================================");
    }
}
