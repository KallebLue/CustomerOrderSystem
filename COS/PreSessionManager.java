package project1.COS;

import java.util.Scanner;

public class PreSessionManager {
   /**
     * Handles the pre-authentication session, presenting options to create an account, log in, browse, or exit.
     *
     * @param input The Scanner object for user input.
     * @param storageCreator The CustomerStorageCreator for managing customer data.
     * @param currentCart The Cart object to be used for Browse and potentially carried over to the post-login session.
     * @return A Customer object if login is successful, null otherwise.
     */
    public static Customer startPreAuthSession(Scanner input, CustomerStorageCreator storageCreator, Cart currentCart) {
        boolean running = true;
        Customer loggedInCustomer = null;

        while (running) {
            System.out.println("\n--- Welcome to the Customer Ordering System ---");
            System.out.println("1. Create Account");
            System.out.println("2. Log In");
            System.out.println("3. Browse Merchandise "); // New option
            System.out.println("4. Exit"); // Option number changed
            System.out.print("Enter your choice: ");

            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    boolean accountCreated = AccountCreator.createNewAccount(input, storageCreator);
                    if (accountCreated) {
                        System.out.println("Returning to main menu.");
                    } else {
                        System.out.println("Account creation not completed.");
                    }
                    break;
                case "2":
                    loggedInCustomer = LoggingOn.performLogin(input, storageCreator);
                    if (loggedInCustomer != null) {
                        System.out.println("Login successful!");
                        running = false; // Exit pre-auth session loop, proceed to main session
                    } else {
                        System.out.println("Login failed. Please try again or create an account.");
                    }
                    break;
                case "3": // Handle Browse Merchandise
                    MerchSelect.selectMerchandise(input, currentCart);
                    // After Browse, the user returns to this pre-login menu.
                    // The cart 'currentCart' now holds any items they selected.
                    break;
                case "4": // Exit option
                    System.out.println("Exiting application. Goodbye!");
                    running = false;
                    System.exit(0); // Terminate the application
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
                    break;
            }
        }
        return loggedInCustomer;
    }
}