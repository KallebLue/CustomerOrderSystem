package project1.COS;

import java.util.ArrayList;

/**
 * Manages the storage and retrieval of OrderCreator objects (completed orders).
 * It utilizes FileStorage for actual file operations.
 */
public class OrderFileStorage { 

    private ArrayList<OrderCreator> orders;
    private FileStorage<OrderCreator> storage;
    private static final String FILENAME = "orders.dat"; // File to store order data

    /**
     * Constructor for OrderFileStorage.
     * Initializes the FileStorage and loads any existing orders from the file.
     */
    public OrderFileStorage() { // Renamed constructor
        storage = new FileStorage<>(FILENAME);
        // Attempt to load existing orders. If the file doesn't exist or is empty,
        // FileStorage.load() returns a new empty ArrayList.
        orders = storage.load();
    }

    /**
     * Adds a new order to the collection and saves the updated list to the file.
     *
     * @param order The OrderCreator object to be added and saved.
     * @return true if the order was successfully added and saved, false otherwise.
     */
    public boolean addOrder(OrderCreator order) {
        if (order == null) {
            System.err.println("Cannot add a null order.");
            return false;
        }
        orders.add(order);
        storage.save(orders); // Save the entire list after adding
        return true;
    }

    /**
     * Retrieves all stored orders.
     *
     * @return An ArrayList containing all OrderCreator objects currently stored.
     */
    public ArrayList<OrderCreator> getAllOrders() {
        // Return a copy to prevent external modification of the internal list
        return new ArrayList<>(orders);
    }

    /**
     * Placeholder method to find an order by its ID.
     * (Currently not implemented for actual search but provides the signature).
     * @param orderId The ID of the order to find.
     * @return The OrderCreator object if found, or null.
     */
    public OrderCreator getOrderById(String orderId) {
        for (OrderCreator order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null; // Order not found
    }
}