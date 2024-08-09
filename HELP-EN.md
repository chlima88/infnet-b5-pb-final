# MyFood Delivery 🥗🌭🍰

## Index
- [Main Subdomains](#main-subdomains)
    - [Customer](#customer)
    - [Catalog](#catalog)
    - [Cart](#cart)
    - [Order](#order)
    - [Delivery](#delivery)
- [Auxiliary Subdomains](#auxiliary-subdomains)
    - [Payment](#payment)
    - [Notification](#notification)
- [Interactions Between Subdomains](#interactions-between-subdomains)
    - [Order Flow](#order-flow)
- [Bounded Contexts](#bounded-contexts)
- [Final Considerations](#final-considerations)

## Main Subdomains

### Customer 🙋

This subdomain manages customer data and relationships.

- Entities:
    - Customer: Represents the customer, with information such as name, email, address, and order history.
    - Address: Value Object that encapsulates the customer's address data.
    - PaymentMethod: Entity representing the payment methods associated with the customer.


- Business Rules:
    - Validation and updating of customer data.
    - Management of payment methods.

### Catalog 📖

The Catalog subdomain manages the products offered by restaurants, including menus and menu items.

- Entities:
    - Restaurant: Entity representing a restaurant in the system.
    - Menu: Aggregate that contains a list of menu items available at a restaurant.
    - MenuItem: Value Object representing a menu item, with information such as name, description, price, and availability.


- Business Rules:
    - Management and updating of menus and menu items.
    - Relationship between restaurants and their respective menus.

### Cart 🛒

This subdomain manages the customer's shopping cart, where selected items are stored before placing the order.

- Entities:
    - Cart: Aggregate that encapsulates the items the customer wants to buy. It is associated with a specific customer and contains the selected menu items.
    - CartItem: Value Object representing an item in the cart, related to a menu item from the catalog.


- Business Rules:
    - Add, remove, and update items in the cart.
    - Calculate the total cart amount based on item prices.
    - Validate the availability of items at checkout.

### Order ✍️

This subdomain manages the lifecycle of orders placed by customers.

- Entities:
    - Order: Aggregate representing a confirmed order, created from the cart. It contains information such as order items, total, status, and associated customer.
    - OrderItem: Value Object representing an item in the order, derived from the CartItem.


- Business Rules:
    - Create orders from the cart.
    - Calculate the total order amount (including fees and discounts).
    - Manage the status of the order (e.g., pending, preparing, on the way, delivered).

### Delivery 📦

The Delivery subdomain manages the order delivery process.

- Entities:
    - Delivery: Aggregate representing a specific delivery, containing information about the associated order, delivery person, and delivery status.
    - Driver: Entity representing the delivery person.


- Business Rules:
    - Assignment of delivery personnel to orders.
    - Routing and optimization of deliveries.
    - Tracking and updating delivery status.

## Auxiliary Subdomains

### Notification ✉️

Responsible for sending notifications to customers and delivery personnel about the status of orders and deliveries.

- Entities:
    - Notification: Entity that encapsulates the details of a notification sent.
    - NotificationChannel: Value Object representing the communication channel (e.g., SMS, email, push notification).


- Business Rules:
    - Automatic event-based notification sending.
    - Configuration of notification preferences.

## Interactions Between Subdomains

### Order Flow

- Customer selects desired items from the Catalog and adds them to the Cart.
- The Cart calculates the total and holds the items until the customer is ready to check out.
- When the customer confirms the order, an Order is created from the Cart.
- Payment is processed, and if successful, the order is confirmed.
- The order is passed to the Delivery subdomain, where it is assigned to a Driver for delivery.
- Notification sends updates to the customer and delivery person throughout the different stages of the order.

## Bounded Contexts

- Customer Context: Manages customer information and preferences.
- Catalog Context: Centralizes logic related to products and menus.
- Cart Context: Focused on managing the shopping cart and validating items.
- Order Context: Controls the lifecycle of orders.
- Delivery Context: Manages delivery logistics.
- Notification Context: Manages communication with customers and delivery personnel.
