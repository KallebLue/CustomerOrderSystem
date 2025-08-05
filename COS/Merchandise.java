package project1.COS;

import java.io.Serializable;

/**
 * Represents a single merchandise item available for purchase in the Customer
 * Ordering System.
 * This class stores details about a product including its unique identifier,
 * name, description, and pricing information (regular and sale prices).
 * It implements {@link Serializable} to allow merchandise data to be persisted
 * (saved to a file).
 */
public class Merchandise implements Serializable {
    /**
     * Serial version UID for serialization compatibility.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the merchandise item (e.g., "M001").
     */
    private String id;
    /**
     * The name of the merchandise item (e.g., "Laptop Pro").
     */
    private String name;
    /**
     * A brief description of the merchandise item.
     */
    private String description;
    /**
     * The standard selling price of the merchandise item.
     */
    private double regularPrice;
    /**
     * The discounted price of the merchandise item, if it's on sale.
     * A value of 0.0 or a value greater than or equal to {@code regularPrice}
     * indicates no active sale.
     */
    private double salePrice; // Could be 0.0 if no sale

    /**
     * Constructs a new Merchandise object with specified details.
     *
     * @param id           The unique identifier of the merchandise.
     * @param name         The name of the merchandise.
     * @param description  A brief description of the merchandise.
     * @param regularPrice The regular selling price of the merchandise.
     * @param salePrice    The sale price of the merchandise. If no sale, typically
     *                     set to 0.0 or {@code regularPrice}.
     */
    public Merchandise(String id, String name, String description, double regularPrice, double salePrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.regularPrice = regularPrice;
        this.salePrice = salePrice;
    }

    // Getters

    /**
     * Returns the unique identifier of the merchandise item.
     *
     * @return The merchandise ID as a String.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the name of the merchandise item.
     *
     * @return The merchandise name as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the merchandise item.
     *
     * @return The merchandise description as a String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the regular selling price of the merchandise item.
     *
     * @return The regular price as a double.
     */
    public double getRegularPrice() {
        return regularPrice;
    }

    /**
     * Returns the sale price of the merchandise item.
     * A value of 0.0 or a value greater than or equal to {@code regularPrice}
     * indicates no active sale.
     *
     * @return The sale price as a double.
     */
    public double getSalePrice() {
        return salePrice;
    }

    /**
     * Returns the current effective price of the merchandise item.
     * If a valid sale price is set (greater than 0 and less than the regular
     * price),
     * the sale price is returned; otherwise, the regular price is returned.
     *
     * @return The current effective price (either sale price or regular price) as a
     *         double.
     */
    public double getCurrentPrice() {
        return (salePrice > 0 && salePrice < regularPrice) ? salePrice : regularPrice;
    }

    /**
     * Returns a string representation of the Merchandise object.
     * This string includes the ID, name, description, and pricing.
     * If the item is on sale, both regular and sale prices are displayed;
     * otherwise, only the regular price is displayed.
     *
     * @return A formatted String containing merchandise details.
     */
    @Override
    public String toString() {
        if (salePrice > 0 && salePrice < regularPrice) {
            return String.format("ID: %s, Name: %s (%s) - Reg Price: $%.2f, SALE Price: $%.2f",
                    id, name, description, regularPrice, salePrice);
        } else {
            return String.format("ID: %s, Name: %s (%s) - Price: $%.2f",
                    id, name, description, regularPrice);
        }
    }
}