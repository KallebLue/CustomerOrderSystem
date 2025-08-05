package project1.COS;

import java.util.ArrayList;

/**
 * Manages the storage and retrieval of Customer objects using a file-based
 * persistence mechanism.
 * This class acts as an intermediary between the application's customer
 * management logic
 * and the {@link FileStorage} utility, ensuring that customer data can be
 * saved to and loaded from a specified file.
 */
public class CustomerStorageCreator {
    /**
     * An {@link ArrayList} to hold all {@link Customer} objects currently loaded in
     * memory.
     */
    private ArrayList<Customer> customers;
    /**
     * An instance of {@link FileStorage} parameterized for {@link Customer}
     * objects,
     * responsible for handling the actual file I/O operations (saving and loading).
     */
    private FileStorage<Customer> storage;
    /**
     * The name of the file where customer data will be persistently stored.
     */
    private static final String FILENAME = "customers.dat";

    /**
     * Constructs a new CustomerStorageCreator.
     * Initializes the {@link FileStorage} with the predefined filename and
     * attempts to load existing customer data into the {@code customers} ArrayList.
     */
    public CustomerStorageCreator() {
        storage = new FileStorage<>(FILENAME);
        customers = storage.load(); // Load existing customers when the object is created
    }

    /**
     * Checks if a given customer ID is available (i.e., not already in use by an
     * existing customer).
     *
     * @param id The customer ID to check for availability.
     * @return {@code true} if the ID is available, {@code false} otherwise (if an
     *         account with this ID already exists).
     */
    public boolean isIDAvailable(String id) {
        for (Customer c : customers) {
            if (c.getId().equals(id)) {
                return false; // ID is not available
            }
        }
        return true; // ID is available
    }

    /**
     * Adds a new {@link Customer} object to the collection and saves the updated
     * list
     * of customers to the persistent file.
     *
     * @param customer The {@link Customer} object to be added.
     * @return {@code true} if the customer was successfully added and saved.
     *         (Note: In a more complex system, this might return false if saving
     *         fails,
     *         but currently, it always returns true after attempting to add and
     *         save.)
     */
    public boolean addCustomer(Customer customer) {
        customers.add(customer);
        storage.save(customers); // Save the updated list to file
        return true;
    }

    /**
     * Returns the complete list of all {@link Customer} objects currently managed
     * by this storage creator.
     *
     * @return An {@link ArrayList} containing all stored {@link Customer} objects.
     */
    public ArrayList<Customer> getAllCustomers() {
        return customers;
    }
}