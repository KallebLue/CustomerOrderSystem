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
     * If the quantity to add is 0 or less, no change occurs.
     *
     * @param item     The {@link Merchandise} item to add to the cart.
     * @param quantity The amount of the item to add.
     */
    public void addItem(Merchandise item, int quantity) {
        if (quantity <= 0) {
            // No action for non-positive quantities.
            // Feedback for this case is handled in MerchSelect or GUI.
            return;
        }
        items.put(item, items.getOrDefault(item, 0) + quantity);
        // Removed: System.out.println(quantity + "x " + item.getName() + " added to
        // cart.");
        // This message should be displayed by the calling class (MerchSelect or GUI)
        // to avoid duplicate outputs.
    }

    /**
     * Removes a specified quantity of a given merchandise item from the cart.
     * If the quantity to remove is greater than or equal to the current quantity,
     * the item is completely removed from the cart.
     *
     * @param item     The {@link Merchandise} item to remove.
     * @param quantity The amount of the item to remove.
     */
    public void removeItem(Merchandise item, int quantity) {
        if (!items.containsKey(item)) {
            System.out.println(item.getName() + " is not in your cart.");
            return;
        }

        int currentQuantity = items.get(item);
        if (quantity >= currentQuantity) {
            items.remove(item);
            System.out.println(item.getName() + " completely removed from cart.");
        } else {
            items.put(item, currentQuantity - quantity);
            System.out.println(quantity + "x " + item.getName() + " removed from cart.");
        }
    }

    /**
     * Returns the current items in the cart with their quantities.
     *
     * @return A {@link Map} where keys are {@link Merchandise} objects and values
     *         are their quantities.
     */
    public Map<Merchandise, Integer> getItems() {
        return items;
    }

    /**
     * Calculates the subtotal of all items in the cart before taxes.
     *
     * @return The subtotal as a double.
     */
    public double getSubtotal() {
        double subtotal = 0.0;
        for (Map.Entry<Merchandise, Integer> entry : items.entrySet()) {
            subtotal += entry.getKey().getCurrentPrice() * entry.getValue();
        }
        return subtotal;
    }

    /**
     * Calculates the sales tax amount based on the subtotal and a predefined tax
     * rate.
     *
     * @return The tax amount as a double.
     */
    public double getTaxAmount() {
        return getSubtotal() * TAX_RATE;
    }

    /**
     * Calculates the grand total of the cart, which includes the subtotal and the
     * tax amount.
     *
     * @return The grand total as a double.
     */
    public double getTotal() {
        return getSubtotal() + getTaxAmount();
    }

    /**
     * Checks if the cart currently contains any items.
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