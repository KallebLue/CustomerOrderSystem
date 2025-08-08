package project1.COS;

import java.util.Scanner;

/**
 * Provides functionality for customers to browse available merchandise and add
 * selected items to their shopping cart.
 * This class facilitates interaction with the {@link MerchCatalog} and updates
 * the customer's {@link Cart}.
 */
public class MerchSelect {

    /**
     * Guides the user through browsing the merchandise catalog and adding items to
     * their cart.
     * The process allows the user to repeatedly add items until they choose to
     * finish.
     * Input validation is performed for both merchandise ID and quantity.
     * This method can be called both before and after a user logs in.
     *
     * @param input       The {@link Scanner} object for reading user input from the
     *                    console.
     * @param currentCart The {@link Cart} object to which selected merchandise
     *                    items will be added.
     *                    This cart instance is updated directly by this method.
     */
    public static void selectMerchandise(Scanner input, Cart currentCart) {
        MerchCatalog merchCatalog = new MerchCatalog();

        System.out.println("\n--- Browse Merchandise ---");
        merchCatalog.displayCatalog(); // Display the entire catalog to the user

        boolean addingToCart = true; // Control flag for the merchandise selection loop
        while (addingToCart) {
            System.out.print("Enter merchandise ID to add to cart (or 'done' to finish shopping): ");
            String merchandiseId = input.nextLine(); // Read the user's input for merchandise ID

            // Check if the user wants to finish shopping
            if (merchandiseId.equalsIgnoreCase("done")) {
                addingToCart = false; // Set flag to exit the loop
                System.out.println("Finished browsing merchandise.");
                break; // Exit the adding loop
            }

            // Attempt to retrieve the merchandise item by the entered ID
            Merchandise selectedMerchandise = merchCatalog.getMerchandiseById(merchandiseId);
            if (selectedMerchandise != null) {
                // If merchandise is found, prompt for quantity
                boolean validQuantityEntered = false; // Flag for the new quantity input loop
                while (!validQuantityEntered) { // Loop until a valid quantity is entered
                    System.out.print("Enter quantity for " + selectedMerchandise.getName() + ": ");
                    try {
                        int quantity = Integer.parseInt(input.nextLine()); // Parse the quantity input
                        if (quantity > 0) {
                            currentCart.addItem(selectedMerchandise, quantity); // Add item to the cart
                            System.out.println(quantity + "x " + selectedMerchandise.getName() + " added to cart.");
                            validQuantityEntered = true; // Set flag to exit the inner loop
                        } else {
                            System.out.println("Quantity must be greater than 0. Please try again."); // Inform about
                                                                                                      // invalid
                                                                                                      // quantity
                        }
                    } catch (NumberFormatException e) {
                        // Handle cases where the quantity entered is not a valid number
                        System.out.println("Invalid quantity. Please enter a numerical value (e.g., 1, 5).");
                    }
                }
            } else {
                // Inform the user if the entered merchandise ID was not found
                System.out.println("Merchandise not found. Please enter a valid merchandise ID.");
            }
        }
    }
}