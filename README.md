# Food Delivery System (Java)

A simple object-oriented restaurant order processing system implemented in Java.
Covers menu items, order management, delivery options, and payment methods.

## UML Class Diagram

```mermaid
classDiagram
    class MenuItem {
        <<abstract>>
        -String name
        -double basePrice
        +MenuItem(name: String, basePrice: double)
        +getName() String
        +getBasePrice() double
        +getPrice()* double
    }

    class Meal {
        -int extraToppings
        -double TOPPING_PRICE$ = 1.5
        +Meal(name: String, basePrice: double, extraToppings: int)
        +getPrice() double
    }

    class Drink {
        -boolean large
        -double LARGE_SURCHARGE$ = 1.0
        +Drink(name: String, basePrice: double, large: boolean)
        +getPrice() double
    }

    class OrderLine {
        -MenuItem item
        -int quantity
        +OrderLine(item: MenuItem, quantity: int)
        +getItem() MenuItem
        +getQuantity() int
        +getLineTotal() double
    }

    class DeliveryOption {
        <<interface>>
        +getFee() double
    }

    class StandardDelivery {
        -double FEE$ = 2.0
        +getFee() double
    }

    class ExpressDelivery {
        -double FEE$ = 5.0
        +getFee() double
    }

    class PaymentMethod {
        <<interface>>
        +pay(amount: double) double
    }

    class CashPayment {
        +pay(amount: double) double
    }

    class CardPayment {
        -double FEE$ = 0.02
        +pay(amount: double) double
    }

    class OrderStatus {
        <<enumeration>>
        NEW
        PAID
        DELIVERED
        CANCELLED
    }

    class Order {
        -int counter$ = 1000
        -int orderNumber
        -String customerName
        -List~OrderLine~ lines
        -OrderStatus status
        -DeliveryOption delivery
        +Order(customerName: String, delivery: DeliveryOption)
        +addItem(item: MenuItem) void
        +addItem(item: MenuItem, qty: int) void
        +getSubtotal() double
        +getTotal() double
        +pay(method: PaymentMethod) void
        +deliver() void
        +cancel() void
        +getOrderNumber() int
        +getCustomerName() String
        +getStatus() OrderStatus
        +getDelivery() DeliveryOption
    }

    MenuItem <|-- Meal
    MenuItem <|-- Drink

    DeliveryOption <|.. StandardDelivery
    DeliveryOption <|.. ExpressDelivery

    PaymentMethod <|.. CashPayment
    PaymentMethod <|.. CardPayment

    Order "1" *-- "1..*" OrderLine : contains
    OrderLine "*" --> "1" MenuItem : item
    Order --> "1" DeliveryOption : delivery
    Order ..> PaymentMethod : uses
    Order --> OrderStatus : status
```

> **Note on multiplicity:** The UML shows `1..*` OrderLines per Order. In code, a NEW order
> starts with an empty list while items are being added. Payment is rejected until at least
> one item exists, so a PAID/DELIVERED order always satisfies `1..*`.

**Supporting members beyond the simplified UML:** constructors for MenuItem, Meal, Drink,
and OrderLine; accessors `getItem()`, `getQuantity()` on OrderLine; accessors
`getOrderNumber()`, `getCustomerName()`, `getStatus()`, `getDelivery()` on Order;
static final pricing constants `TOPPING_PRICE`, `LARGE_SURCHARGE`, and delivery `FEE` fields.

## Pricing Rules

- **Meal:** basePrice + extraToppings × 1.5
- **Drink:** basePrice + 1.0 if large, otherwise basePrice
- **OrderLine total:** item.getPrice() × quantity
- **Standard delivery:** 2.0
- **Express delivery:** 5.0
- **Cash payment:** returns amount (no fee)
- **Card payment:** returns amount × 1.02

## Order Lifecycle

- Status starts as `NEW`.
- Items can only be added while `NEW`.
- `pay()` requires at least one item and status `NEW` → changes to `PAID`.
- `deliver()` requires status `PAID` → changes to `DELIVERED`.
- `cancel()` requires status `NEW` → changes to `CANCELLED`.

## How to Compile and Run

### Using batch scripts (Windows)

```
compile.bat
run.bat
```

### Using javac/java directly

```bash
javac -d bin src/main/java/com/fooddelivery/*.java
java -cp bin com.fooddelivery.Main
```

### Using Maven

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.fooddelivery.Main"
```

## Example Output

```
Burger Meal unit price: 9.5
Juice unit price: 4.0
Order #1001 created for Sarah Connor
Status: NEW
Subtotal: 23.0
Delivery fee: 5.0
Total: 28.0
Card amount: 28.56
Status after payment: PAID
Status after delivery: DELIVERED

--- Validation checks ---
Cannot add items: order is not NEW (current status: DELIVERED).
Cannot deliver: order is not PAID (current status: DELIVERED).
Cannot cancel: order is not NEW (current status: DELIVERED).
Cannot pay: order is not NEW (current status: DELIVERED).
Order #1002 status: NEW
Order #1002 status after cancel: CANCELLED
Cannot deliver: order is not PAID (current status: NEW).
Cannot pay: order has no items.
```
