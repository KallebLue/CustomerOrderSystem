package project1.COS;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a shopping cart in the Customer Ordering System.
 * This class allows customers to add, remove, and manage merchandise items
 * before proceeding to checkout. It calculates subtotal, taxes, and total
 * amounts.
 * Implements Serializable to allow its state to be saved if needed.
 */
public class Cart implements Serializable {
    /**
     * Serial version UID for serialization compatibility.
     */
    private static final long serialVersionUID = 1L;

    /**
     * A map to store the merchandise items in the cart, where the key is the
     * Merchandise object and the value is the quantity of that merchandise.
     * LinkedHashMap is used to maintain the insertion order of items for display
     * purposes.
     */
    private Map<Merchandise, Integer> items;
    /**
     * The sales tax rate applied to the subtotal.
     * Set as a constant (8%).
     */
    private static final double TAX_RATE = 0.08; // 8% sales tax

    /**
     * Constructs a new, empty Cart.
     * Initializes the internal LinkedHashMap to store items.
     */
    public Cart() {
        this.items = new LinkedHashMap<>();
    }

    /**
     * Adds a specified quantity of a given merchandise item to the cart.
     * If the merchandise already exists in the cart, its quantity is updated.
     * Prints a message indicating the item and quantity added, or an error if input
     * is invalid.
     *
     * @param item     The Merchandise object to add.
     * @param quantity The number of units of the merchandise to add. Must be
     *                 greater than 0.
     */
    public void addItem(Merchandise item, int quantity) {
        if (item == null || quantity <= 0) {
            System.out.println("Invalid merchandise or quantity.");
            return;
        }
        // If merchandise already in cart, update quantity, otherwise add new
        items.put(item, items.getOrDefault(item, 0) + quantity);
        System.out.println(quantity + "x " + item.getName() + " added to cart.");
    }

    /**
     * Removes a specified quantity of a given merchandise item from the cart.
     * If the quantity to remove is greater than or equal to the current quantity,
     * the item is completely removed from the cart. Otherwise, its quantity is
     * reduced.
     * Prints a message indicating the action, or an error if input is invalid or
     * item not found.
     *
     * @param item     The Merchandise object to remove.
     * @param quantity The number of units of the merchandise to remove. Must be
     *                 greater than 0.
     */
    public void removeItem(Merchandise item, int quantity) {
        if (item == null || quantity <= 0) {
            System.out.println("Invalid merchandise or quantity.");
            return;
        }
        if (!items.containsKey(item)) {
            System.out.println("Merchandise not found in cart.");
            return;
        }

        int currentQuantity = items.get(item);
        if (quantity >= currentQuantity) {
            items.remove(item); // Remove completely if quantity is enough or more
            System.out.println(item.getName() + " removed from cart.");
        } else {
            items.put(item, currentQuantity - quantity);
            System.out.println(quantity + "x " + item.getName() + " removed from cart.");
        }
    }

    /**
     * Calculates the subtotal of all items currently in the cart before taxes.
     * The subtotal is the sum of (item's current price * item's quantity) for all
     * items.
     *
     * @return The calculated subtotal as a double.
     */
    public double getSubtotal() {
        double subtotal = 0;
        for (Map.Entry<Merchandise, Integer> entry : items.entrySet()) {
            subtotal += entry.getKey().getCurrentPrice() * entry.getValue();
        }
        return subtotal;
    }

    /**
     * Calculates the tax amount for the current cart items based on the predefined
     * TAX_RATE.
     *
     * @return The calculated tax amount as a double.
     */
    public double getTaxAmount() {
        return getSubtotal() * TAX_RATE;
    }

    /**
     * Calculates the total cost of all items in the cart, including subtotal and
     * taxes.
     *
     * @return The calculated total amount as a double.
     */
    public double getTotal() {
        return getSubtotal() + getTaxAmount();
    }

    /**
     * Returns a copy of the map containing items in the cart and their quantities.
     * This ensures that the internal map cannot be modified directly from outside
     * the class.
     *
     * @return A new LinkedHashMap containing Merchandise objects mapped to their
     *         quantities.
     */
    public Map<Merchandise, Integer> getItems() {
        return new LinkedHashMap<>(items); // Return a copy
    }

    /**
     * Checks if the cart is empty.
     *
     * @return true if the cart contains no items, false otherwise.
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Clears all items from the cart.
     * Prints a message indicating that the cart has been cleared.
     */
    public void clear() {
        items.clear();
        System.out.println("Cart cleared.");
    }

    /**
     * Displays a formatted summary of the cart's contents to the console.
     * Includes each item, its quantity, individual price, line total, subtotal,
     * tax amount, and the grand total.
     * If the cart is empty, it prints a message indicating so.
     */
    public void displayCart() {
        System.out.println("\n--- Your Cart ---");
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        for (Map.Entry<Merchandise, Integer> entry : items.entrySet()) {
            Merchandise item = entry.getKey();
            int quantity = entry.getValue();
            System.out.printf("%d x %s ($%.2f each) = $%.2f\n",
                    quantity, item.getName(), item.getCurrentPrice(),
                    (quantity * item.getCurrentPrice()));
        }
        System.out.printf("\nSubtotal: $%.2f\n", getSubtotal());
        System.out.printf("Taxes (%.0f%%): $%.2f\n", TAX_RATE * 100, getTaxAmount());
        System.out.printf("Total: $%.2f\n", getTotal());
        System.out.println("-------------------");
    }
}