package project1.COS;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID; // To generate unique order IDs

/**
 * Represents a customer order within the Customer Ordering System.
 * This class stores all relevant details of a placed order, including the items ordered,
 * total amount, delivery method, and bank authorization. It also contains static methods
 * to facilitate the order creation process.
 * <p>
 * Implements {@link Serializable} to allow order objects to be saved to and loaded from files.
 */
public class OrderCreator implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Represents a single item within an order, storing its merchandise ID and the quantity ordered.
     * This nested class ensures that individual order items can be easily managed and serialized.
     */
    public static class OrderItem implements Serializable {
        private static final long serialVersionUID = 1L;
        private String merchandiseId;
        private int quantity;

        /**
         * Constructs a new OrderItem with the specified merchandise ID and quantity.
         *
         * @param merchandiseId The unique identifier of the merchandise.
         * @param quantity      The quantity of the merchandise ordered.
         */
        public OrderItem(String merchandiseId, int quantity) {
            this.merchandiseId = merchandiseId;
            this.quantity = quantity;
        }

        /**
         * Returns the merchandise ID of this order item.
         *
         * @return The merchandise ID.
         */
        public String getMerchandiseId() {
            return merchandiseId;
        }

        /**
         * Returns the quantity of this merchandise item in the order.
         *
         * @return The quantity.
         */
        public int getQuantity() {
            return quantity;
        }

        /**
         * Returns a string representation of the OrderItem,
         * typically used for displaying order details.
         *
         * @return A formatted string showing the merchandise ID and quantity.
         */
        @Override
        public String toString() {
            return String.format("ID: %s (x%d)", merchandiseId, quantity);
        }
    }

    // --- Order Data Fields ---
    private String orderId; // Unique ID for the order
    private LocalDateTime orderDate;
    private String customerId;
    // Stores a list of OrderItem objects, each representing a merchandise item and its quantity in the order.
    private ArrayList<OrderItem> orderedItems;
    private double totalAmount;
    private String bankAuthorizationNumber;
    private String deliveryMethod; // "Mail" or "In-store Pickup"
    private double deliveryFee;

    private static final double MAIL_DELIVERY_FEE = 3.00; // Constant for mail delivery fee

    /**
     * Constructs a new {@code OrderCreator} instance representing a completed customer order.
     * This constructor is used to create the data object that gets saved and provides a snapshot
     * of the order details at the time of creation.
     *
     * @param orderId       The unique identifier generated for this order.
     * @param customerId    The ID of the customer who placed the order.
     * @param cartItems     A Map containing {@link Merchandise} objects and their quantities from the customer's cart.
     * These are converted into {@link OrderItem} objects for persistence.
     * @param totalAmount   The final total amount of the order, including merchandise costs, taxes, and delivery fees.
     * @param deliveryMethod The chosen delivery method (e.g., "Mail Delivery", "In-store Pickup").
     * @param deliveryFee   The fee associated with the chosen delivery method (0.0 if free).
     */
    public OrderCreator(String orderId, String customerId, Map<Merchandise, Integer> cartItems, double totalAmount,
                        String deliveryMethod, double deliveryFee) {
        this.orderId = orderId;
        this.orderDate = LocalDateTime.now(); // Sets the order date and time to the current moment
        this.customerId = customerId;
        this.orderedItems = new ArrayList<>(); // Initialize ArrayList to store order items
        // Convert Merchandise objects from the cart (Map) into OrderItem objects (ArrayList) for efficient storage
        for (Map.Entry<Merchandise, Integer> entry : cartItems.entrySet()) {
            this.orderedItems.add(new OrderItem(entry.getKey().getId(), entry.getValue()));
        }
        this.totalAmount = totalAmount;
        this.bankAuthorizationNumber = null; // Initially null, set after successful bank approval
        this.deliveryMethod = deliveryMethod;
        this.deliveryFee = deliveryFee;
    }

    // --- Getters for Order Data Fields ---
    /**
     * Returns the unique identifier for this order.
     * @return The order ID.
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Returns the date and time when this order was placed.
     * @return The order date and time.
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * Returns the ID of the customer who placed this order.
     * @return The customer ID.
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Returns a copy of the list of {@link OrderItem} objects contained in this order.
     * This ensures that the original list cannot be modified externally.
     * @return An {@link ArrayList} of {@link OrderItem}s representing the merchandise in the order.
     */
    public ArrayList<OrderItem> getOrderedItems() {
        return new ArrayList<>(orderedItems); // Return a copy to prevent external modification
    }

    /**
     * Returns the total monetary amount of this order, including items, taxes, and delivery fees.
     * @return The total amount.
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Returns the bank authorization number for the payment, if the payment was approved.
     * @return The bank authorization number, or null if not yet set or payment failed.
     */
    public String getBankAuthorizationNumber() {
        return bankAuthorizationNumber;
    }

    /**
     * Returns the delivery method chosen for this order (e.g., "Mail Delivery", "In-store Pickup").
     * @return The delivery method.
     */
    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    /**
     * Returns the delivery fee applied to this order.
     * @return The delivery fee.
     */
    public double getDeliveryFee() {
        return deliveryFee;
    }

    // --- Setters (for properties updated after order creation, like auth number) ---
    /**
     * Sets the bank authorization number for this order. This is typically called
     * after a successful payment transaction.
     * @param bankAuthorizationNumber The authorization number provided by the bank.
     */
    public void setBankAuthorizationNumber(String bankAuthorizationNumber) {
        this.bankAuthorizationNumber = bankAuthorizationNumber;
    }

    /**
     * Provides a comprehensive string representation of the order,
     * suitable for displaying order confirmation to the user.
     * It includes order ID, date, customer ID, a detailed list of items,
     * delivery method, total amount, and bank authorization number.
     *
     * @return A formatted string detailing the order.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Order Details (ID: ").append(orderId).append(") ---\n");
        sb.append("Date: ").append(orderDate.toLocalDate()).append("\n");
        sb.append("Time: ").append(orderDate.toLocalTime().withNano(0)).append("\n"); // Trim nanos for cleaner display
        sb.append("Customer ID: ").append(customerId).append("\n");
        sb.append("Items:\n");
        // Iterate through the ArrayList of OrderItem objects to display each item
        MerchCatalog catalog = new MerchCatalog(); // Re-instantiate to get merchandise details for display
        for (OrderItem item : orderedItems) {
            Merchandise merch = catalog.getMerchandiseById(item.getMerchandiseId());
            if (merch != null) {
                sb.append(String.format("   - %d x %s ($%.2f each) = $%.2f\n",
                                        item.getQuantity(), merch.getName(), merch.getCurrentPrice(),
                                        (item.getQuantity() * merch.getCurrentPrice())));
            } else {
                sb.append(String.format("   - %d x Unknown Merchandise (ID: %s)\n", item.getQuantity(), item.getMerchandiseId()));
            }
        }
        sb.append("Delivery Method: ").append(deliveryMethod);
        if (deliveryFee > 0) {
            sb.append(String.format(" ($%.2f fee)", deliveryFee));
        }
        sb.append("\nTotal Amount: $").append(String.format("%.2f", totalAmount)).append("\n");
        if (bankAuthorizationNumber != null && !bankAuthorizationNumber.isEmpty()) {
            sb.append("Authorization #: ").append(bankAuthorizationNumber).append("\n");
        } else {
            sb.append("Authorization #: Pending\n"); // Indicate if authorization is not yet available
        }
        sb.append("--------------------------");
        return sb.toString();
    }

    // --- Static Method for Order Creation Logic ---
    /**
     * Guides the customer through the complete process of placing an order.
     * This includes displaying the cart, selecting a delivery method, processing payment
     * via a simulated bank, and finally saving the order and clearing the cart.
     * <p>
     * Precondition: The customer has items in their cart and is logged in.
     *
     * @param input                The {@link Scanner} object for reading user input.
     * @param customer             The logged-in {@link Customer} object, whose details (e.g., credit card) might be updated.
     * @param cart                 The {@link Cart} object containing the merchandise to be ordered. It will be cleared upon successful order.
     * @param customerStorageCreator The {@link CustomerStorageCreator} used to save updated customer information (e.g., new credit card).
     * @param orderFileStorage     The {@link OrderFileStorage} used to persist the newly created order.
     */
    public static void processOrder(Scanner input, Customer customer, Cart cart,
                                    CustomerStorageCreator customerStorageCreator, OrderFileStorage orderFileStorage) {

        System.out.println("\n--- Proceeding to Make Order ---");

        // Step 1: Check if the cart is empty. An order cannot be placed without items.
        if (cart.isEmpty()) {
            System.out.println("Error: Your cart is empty. Please add items before making an order.");
            return; // Exit the order process if cart is empty
        }

        cart.displayCart(); // Display the current cart summary to the user

        // Step 2 & 3: Display and select delivery method
        double currentTotal = cart.getTotal(); // Calculate initial total from cart
        String deliveryMethod = null;
        double deliveryFee = 0.0;
        boolean deliverySelected = false; // Flag to control the delivery selection loop

        while (!deliverySelected) {
            System.out.println("\n--- Select Delivery Method ---");
            System.out.printf("1. Mail Delivery (Fee: $%.2f)\n", MAIL_DELIVERY_FEE);
            System.out.println("2. In-store Pickup (Free)");
            System.out.println("3. Exit Order Process");
            System.out.print("Enter your choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    deliveryMethod = "Mail Delivery";
                    deliveryFee = MAIL_DELIVERY_FEE;
                    currentTotal += deliveryFee; // Add mail delivery fee to the total
                    deliverySelected = true;
                    break;
                case "2":
                    deliveryMethod = "In-store Pickup";
                    deliveryFee = 0.0; // In-store pickup is free
                    currentTotal += deliveryFee; // Ensure total is updated for pickup even if fee is 0
                    deliverySelected = true;
                    break;
                case "3":
                    System.out.println("Order process exited. Your cart has not been cleared.");
                    return; // Exit the entire order process
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }

        System.out.println("\n--- Order Summary with Delivery ---");
        cart.displayCart(); // Re-display cart items with updated total
        System.out.printf("Delivery Method: %s (Fee: $%.2f)\n", deliveryMethod, deliveryFee);
        System.out.printf("Final Total: $%.2f\n", currentTotal);
        System.out.println("-----------------------------------");

        // Step 5 & 6 & 7: Credit card retrieval and bank authorization
        String creditCardToUse = customer.getCreditCard(); // Get the customer's default credit card
        boolean bankApproved = false;
        String authorizationNumber = null;
        int maxAttempts = 3; // Maximum number of attempts for credit card payment
        int attempts = 0;

        while (!bankApproved && attempts < maxAttempts) {
            System.out.println("\nAttempting to charge your default credit card: " + creditCardToUse + " for $" + String.format("%.2f", currentTotal) + "...");
            // Simulate charging the card using the BankSimulator
            authorizationNumber = BankSimulator.chargeCard(creditCardToUse, currentTotal);

            if (authorizationNumber != null) {
                bankApproved = true; // Payment successful
            } else {
                attempts++;
                System.out.println("Bank charge denied. Attempts left: " + (maxAttempts - attempts));
                if (attempts < maxAttempts) {
                    System.out.print("Enter a new credit card number (or 'exit' to cancel order): ");
                    String newCard = input.nextLine();
                    if (newCard.equalsIgnoreCase("exit")) {
                        System.out.println("Order process cancelled. Your cart has not been cleared.");
                        return; // Exit if user cancels after failed attempts
                    }
                    customer.setCreditCard(newCard); // Update customer's credit card with the new one
                    customerStorageCreator.addCustomer(customer); // Save updated customer info to persistence
                    creditCardToUse = newCard; // Use the new card for the next attempt
                } else {
                    System.out.println("Maximum credit card attempts reached. Order cancelled. Your cart has not been cleared.");
                    return; // Exit if maximum attempts are reached
                }
            }
        }

        // Final check for bank approval after all attempts
        if (!bankApproved) {
            System.out.println("Order could not be completed due to bank authorization failure.");
            return; // Exit if bank approval was not achieved
        }

        // Step 8: Store the customer order upon successful payment
        // Generate a unique order ID using UUID
        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Create a new OrderCreator object to represent the completed order's data
        OrderCreator newOrder = new OrderCreator(
            orderId,
            customer.getId(),
            cart.getItems(), // Pass the cart items (Map<Merchandise, Integer>) for conversion to OrderItem list
            currentTotal,
            deliveryMethod,
            deliveryFee
        );
        newOrder.setBankAuthorizationNumber(authorizationNumber); // Set the obtained authorization number

        orderFileStorage.addOrder(newOrder); // Save the new OrderCreator object to file storage

        // Step 9: Display order confirmation to the user
        System.out.println("\n" + newOrder); // Uses OrderCreator's toString() for a nicely formatted confirmation
        System.out.println("\nOrder placed successfully! Thank you for your purchase.");

        cart.clear(); // Clear the cart after a successful order
    }
}