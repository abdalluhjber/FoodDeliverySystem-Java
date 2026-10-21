package com.fooddelivery;

/**
 * Automated test suite for the Food Delivery System.
 * Tests all mathematical calculations, business rules, state transitions,
 * and error handling cases. Can be executed as a standalone application.
 */
public class FoodDeliverySystemTest {

    private static int testsRun = 0;
    private static int testsPassed = 0;

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   RUNNING FOOD DELIVERY SYSTEM AUTOMATED TESTS  ");
        System.out.println("=================================================");

        testOrderNumberSequence();
        testMealPriceCalculation();
        testDrinkPriceCalculation();
        testExampleScenarioCalculations();
        testDeliveryOptions();
        testPaymentCalculations();
        testOrderLifecycleNewPaidDelivered();
        testOrderCancellationRule();
        testCannotAddItemsToPaidOrCancelledOrder();
        testCannotDeliverUnpaidOrder();
        testCannotCancelPaidOrder();
        testCannotPayEmptyOrder();
        testExtensibility();

        System.out.println("=================================================");
        System.out.printf("   TEST RESULTS: %d / %d PASSED%n", testsPassed, testsRun);
        System.out.println("=================================================");

        if (testsPassed != testsRun) {
            System.err.println("SOME TESTS FAILED!");
            System.exit(1);
        } else {
            System.out.println("ALL TESTS PASSED WITH 100% SUCCESS!");
        }
    }

    private static void assertTrue(String testName, boolean condition) {
        testsRun++;
        if (condition) {
            testsPassed++;
            System.out.println(" [PASS] " + testName);
        } else {
            System.err.println(" [FAIL] " + testName);
        }
    }

    private static void assertEquals(String testName, double expected, double actual, double delta) {
        testsRun++;
        if (Math.abs(expected - actual) <= delta) {
            testsPassed++;
            System.out.println(" [PASS] " + testName);
        } else {
            System.err.printf(" [FAIL] %s: expected %.4f but got %.4f%n", testName, expected, actual);
        }
    }

    private static void testOrderNumberSequence() {
        Order.resetCounter(1000);
        Order o1 = new Order("Customer 1", new StandardDelivery());
        Order o2 = new Order("Customer 2", new ExpressDelivery());
        Order o3 = new Order("Customer 3", new StandardDelivery());

        assertTrue("Order numbers start at 1001", o1.getOrderNumber() == 1001);
        assertTrue("Order numbers increment to 1002", o2.getOrderNumber() == 1002);
        assertTrue("Order numbers increment to 1003", o3.getOrderNumber() == 1003);
    }

    private static void testMealPriceCalculation() {
        Meal m0 = new Meal("Burger", 8.0, 0);
        Meal m1 = new Meal("Burger", 8.0, 1);
        Meal m3 = new Meal("Burger", 8.0, 3);

        assertEquals("Meal with 0 extra toppings is base price", 8.0, m0.getPrice(), 0.001);
        assertEquals("Meal with 1 extra topping is base + 1.5", 9.5, m1.getPrice(), 0.001);
        assertEquals("Meal with 3 extra toppings is base + 4.5", 12.5, m3.getPrice(), 0.001);
    }

    private static void testDrinkPriceCalculation() {
        Drink dRegular = new Drink("Juice", 3.0, false);
        Drink dLarge = new Drink("Juice", 3.0, true);

        assertEquals("Regular drink is base price", 3.0, dRegular.getPrice(), 0.001);
        assertEquals("Large drink is base price + 1.0", 4.0, dLarge.getPrice(), 0.001);
    }

    private static void testExampleScenarioCalculations() {
        // Example from assignment:
        // 2 x Burger Meal (base $8, 1 extra topping) = 9.5 * 2 = 19.0
        // 1 x Large Juice (base $3) = 4.0 * 1 = 4.0
        // Express delivery = 5.0
        // Subtotal = 23.0
        // Total = 28.0
        // Paid by card = 28 * 1.02 = 28.56
        Order order = new Order("Test Customer", new ExpressDelivery());
        Meal burger = new Meal("Burger Meal", 8.0, 1);
        Drink juice = new Drink("Large Juice", 3.0, true);

        order.addItem(burger, 2);
        order.addItem(juice); // quantity = 1

        assertEquals("Assignment Example: Burger unit price", 9.5, burger.getPrice(), 0.001);
        assertEquals("Assignment Example: Juice unit price", 4.0, juice.getPrice(), 0.001);
        assertEquals("Assignment Example: Subtotal = 23.0", 23.0, order.getSubtotal(), 0.001);
        assertEquals("Assignment Example: Total with Express delivery = 28.0", 28.0, order.getTotal(), 0.001);

        PaymentMethod card = new CardPayment();
        double charged = card.pay(order.getTotal());
        assertEquals("Assignment Example: Card payment = 28.56", 28.56, charged, 0.001);
    }

    private static void testDeliveryOptions() {
        DeliveryOption std = new StandardDelivery();
        DeliveryOption exp = new ExpressDelivery();

        assertEquals("Standard delivery fee is $2", 2.0, std.getFee(), 0.001);
        assertEquals("Express delivery fee is $5", 5.0, exp.getFee(), 0.001);
    }

    private static void testPaymentCalculations() {
        PaymentMethod cash = new CashPayment();
        PaymentMethod card = new CardPayment();

        assertEquals("Cash has 0% fee", 100.0, cash.pay(100.0), 0.001);
        assertEquals("Card adds 2% fee", 102.0, card.pay(100.0), 0.001);
    }

    private static void testOrderLifecycleNewPaidDelivered() {
        Order order = new Order("John", new StandardDelivery());
        order.addItem(new Drink("Water", 1.0, false));

        assertTrue("Initial status is NEW", order.getStatus() == OrderStatus.NEW);

        order.pay(new CashPayment());
        assertTrue("Status after pay() is PAID", order.getStatus() == OrderStatus.PAID);

        order.deliver();
        assertTrue("Status after deliver() is DELIVERED", order.getStatus() == OrderStatus.DELIVERED);
    }

    private static void testOrderCancellationRule() {
        Order order = new Order("John", new StandardDelivery());
        order.addItem(new Drink("Water", 1.0, false));

        order.cancel();
        assertTrue("Only NEW order can be cancelled -> CANCELLED", order.getStatus() == OrderStatus.CANCELLED);
    }

    private static void testCannotAddItemsToPaidOrCancelledOrder() {
        // Test adding to PAID order
        Order order1 = new Order("John", new StandardDelivery());
        order1.addItem(new Drink("Water", 1.0, false));
        order1.pay(new CashPayment());

        boolean caughtPaid = false;
        try {
            order1.addItem(new Drink("Cola", 2.0, false));
        } catch (IllegalStateException e) {
            caughtPaid = true;
        }
        assertTrue("Cannot add items while order is PAID", caughtPaid);

        // Test adding to CANCELLED order
        Order order2 = new Order("John", new StandardDelivery());
        order2.cancel();

        boolean caughtCancelled = false;
        try {
            order2.addItem(new Drink("Cola", 2.0, false));
        } catch (IllegalStateException e) {
            caughtCancelled = true;
        }
        assertTrue("Cannot add items while order is CANCELLED", caughtCancelled);
    }

    private static void testCannotDeliverUnpaidOrder() {
        Order order = new Order("John", new StandardDelivery());
        order.addItem(new Drink("Water", 1.0, false));

        boolean caught = false;
        try {
            order.deliver();
        } catch (IllegalStateException e) {
            caught = true;
        }
        assertTrue("Only a PAID order can be delivered (cannot deliver NEW)", caught);
    }

    private static void testCannotCancelPaidOrder() {
        Order order = new Order("John", new StandardDelivery());
        order.addItem(new Drink("Water", 1.0, false));
        order.pay(new CashPayment());

        boolean caught = false;
        try {
            order.cancel();
        } catch (IllegalStateException e) {
            caught = true;
        }
        assertTrue("Only a NEW order can be cancelled (cannot cancel PAID)", caught);
    }

    private static void testCannotPayEmptyOrder() {
        Order order = new Order("John", new StandardDelivery());
        boolean caught = false;
        try {
            order.pay(new CashPayment());
        } catch (IllegalStateException e) {
            caught = true;
        }
        assertTrue("Cannot pay for an empty order without lines", caught);
    }

    private static void testExtensibility() {
        // Verifies open-closed principle: a new delivery option and payment method can be plugged in
        DeliveryOption droneDelivery = new DeliveryOption() {
            @Override
            public double getFee() {
                return 7.5;
            }
        };

        PaymentMethod cryptoPayment = new PaymentMethod() {
            @Override
            public double pay(double amount) {
                return amount * 1.01; // 1% fee
            }
        };

        Order order = new Order("Futuristic Customer", droneDelivery);
        order.addItem(new Meal("Space Burger", 10.0, 2)); // 10 + 3 = 13.0
        assertEquals("Subtotal with custom delivery", 13.0, order.getSubtotal(), 0.001);
        assertEquals("Total with custom drone delivery ($7.5)", 20.5, order.getTotal(), 0.001);

        double charged = cryptoPayment.pay(order.getTotal());
        assertEquals("Payment with custom payment method", 20.5 * 1.01, charged, 0.001);
        order.pay(cryptoPayment);
        assertTrue("Order paid with custom payment method", order.getStatus() == OrderStatus.PAID);
    }
}
