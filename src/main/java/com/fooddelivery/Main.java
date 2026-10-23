package com.fooddelivery;

public class Main {
    public static void main(String[] args) {
        // Create menu items
        Meal burgerMeal = new Meal("Burger Meal", 8.0, 1);
        Drink largeJuice = new Drink("Juice", 3.0, true);

        System.out.println("Burger Meal unit price: " + burgerMeal.getPrice());
        System.out.println("Juice unit price: " + largeJuice.getPrice());

        // Create order with express delivery
        Order order = new Order("Sarah Connor", new ExpressDelivery());
        System.out.println("Order #" + order.getOrderNumber() + " created for " + order.getCustomerName());
        System.out.println("Status: " + order.getStatus());

        // Add items: 2 Burger Meals and 1 large Juice
        order.addItem(burgerMeal, 2);
        order.addItem(largeJuice);

        // Calculate totals
        double subtotal = order.getSubtotal();
        double total = order.getTotal();
        System.out.println("Subtotal: " + subtotal);
        System.out.println("Delivery fee: " + order.getDelivery().getFee());
        System.out.println("Total: " + total);

        // Card payment amount (calculated here, payment executed once inside order.pay)
        // total * 1.02 = 28.56 (card adds 2% fee)
        double cardAmount = Math.round(total * 1.02 * 100.0) / 100.0;
        System.out.println("Card amount: " + cardAmount);

        // Pay the order (payment is executed once inside this method)
        PaymentMethod card = new CardPayment();
        order.pay(card);
        System.out.println("Status after payment: " + order.getStatus());

        // Deliver the order
        order.deliver();
        System.out.println("Status after delivery: " + order.getStatus());

        // --- Validation checks ---
        System.out.println();
        System.out.println("--- Validation checks ---");

        // Cannot add items after payment
        order.addItem(burgerMeal);

        // Cannot deliver a delivered order
        order.deliver();

        // Cannot cancel a delivered order
        order.cancel();

        // Cannot pay again
        order.pay(card);

        // New order: cancel test
        Order order2 = new Order("John Doe", new StandardDelivery());
        order2.addItem(new Drink("Water", 1.5, false));
        System.out.println("Order #" + order2.getOrderNumber() + " status: " + order2.getStatus());
        order2.cancel();
        System.out.println("Order #" + order2.getOrderNumber() + " status after cancel: " + order2.getStatus());

        // Cannot deliver an unpaid order
        Order order3 = new Order("Alice", new StandardDelivery());
        order3.addItem(burgerMeal);
        order3.deliver();

        // Cannot pay an empty order
        Order order4 = new Order("Bob", new StandardDelivery());
        order4.pay(new CashPayment());
    }
}
