package project1.COS;

import java.util.Scanner;

/**
 * Manages the session for a customer who has successfully logged in.
 * Provides options like logging out.
 */
public class PostSessionManager {

    /**
     * Handles the interactive session for a logged-in customer.
     * Presents a menu and processes user choices until the customer logs out.
     *
     * @param input The Scanner object for user input.
     * @param customer The Customer object representing the logged-in user.
     * @param customerStorageCreator The CustomerStorageCreator for managing customer data (needed for OrderCreator).
     * @param orderFileStorage The OrderFileStorage for managing order data.
     * @param currentCart The Cart object, potentially pre-populated from pre-login Browse.
     */
    public static void startSession(Scanner input, Customer customer,
                                    CustomerStorageCreator customerStorageCreator,
                                    OrderFileStorage orderFileStorage,
                                    Cart currentCart) {
        System.out.println("\nWelcome, " + customer.getId() + "! You are now logged in.");

        // Cart is now passed in, not initialized here

        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- Main Menu (Logged In) ---");
            System.out.println("1. Browse Merchandise & Add to Cart");
            System.out.println("2. View Cart");
            System.out.println("3. Proceed to Checkout");
            System.out.println("4. View Past Orders");
            System.out.println("5. Log Out");
            System.out.print("Enter your choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    MerchSelect.selectMerchandise(input, currentCart);
                    break;
                case "2":
                    currentCart.displayCart();
                    break;
                case "3":
                    OrderCreator.processOrder(input, customer, currentCart, customerStorageCreator, orderFileStorage);
                    break;
                case "4":
                    OrderViewer.viewOrders(input, customer.getId(), orderFileStorage);
                    break;
                case "5": // Log out option
                    LogOff.performLogoff();
                    loggedIn = false; // Exit the session loop
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}