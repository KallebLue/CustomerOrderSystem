package project1.COS;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a catalog of {@link Merchandise} items available in the Customer Ordering System.
 * This class provides methods to access and display the list of products.
 * Currently, it initializes with a predefined set of sample merchandise.
 */
public class MerchCatalog {
    /**
     * A {@link List} of {@link Merchandise} objects representing the available items in the catalog.
     */
    private List<Merchandise> merchandise;

    /**
     * Constructs a new MerchCatalog and initializes it with a predefined set of sample merchandise items.
     * This serves as a static catalog for demonstration purposes.
     */
    public MerchCatalog() {
        merchandise = new ArrayList<>();
        // Initialize with some sample merchandise
        merchandise.add(new Merchandise("M001", "Laptop Pro", "High-performance laptop", 1200.00, 1000.00));
        merchandise.add(new Merchandise("M002", "Mechanical Keyboard", "RGB gaming keyboard", 80.00, 0.00));
        merchandise.add(new Merchandise("M003", "Wireless Mouse", "Ergonomic wireless mouse", 35.00, 25.00));
        merchandise.add(new Merchandise("M004", "USB-C Hub", "Multi-port adapter", 50.00, 0.00));
        merchandise.add(new Merchandise("M005", "External SSD 1TB", "Portable solid state drive", 150.00, 130.00));
    }

    /**
     * Returns a new {@link ArrayList} containing all {@link Merchandise} items in the catalog.
     * This method returns a copy to prevent external modification of the internal list.
     *
     * @return A {@link List} of all available merchandise.
     */
    public List<Merchandise> getAllMerchandise() {
        return new ArrayList<>(merchandise);
    }

    /**
     * Retrieves a specific {@link Merchandise} item from the catalog by its ID.
     * The comparison is case-insensitive.
     *
     * @param id The unique identifier of the merchandise item to retrieve.
     * @return The {@link Merchandise} object if found, or {@code null} if no item with the given ID exists.
     */
    public Merchandise getMerchandiseById(String id) {
        for (Merchandise item : merchandise) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null; // Merchandise not found
    }

    /**
     * Prints the entire merchandise catalog to the console.
     * Each item's details are displayed using its {@code toString()} method.
     * If the catalog is empty, a corresponding message is displayed.
     */
    public void displayCatalog() {
        System.out.println("\n--- Merchandise Catalog ---");
        if (merchandise.isEmpty()) {
            System.out.println("No merchandise available at the moment.");
            return;
        }
        for (Merchandise item : merchandise) { // Loop through merchandise
            System.out.println(item); // Prints merchandise details using its toString() method
        }
        System.out.println("---------------------------");
    }
}