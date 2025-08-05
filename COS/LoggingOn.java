package project1.COS;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Handles the customer login process for the Customer Ordering System.
 * This class provides a static method to authenticate a customer by verifying
 * their ID, password, and security question answer against stored customer
 * data.
 */
public class LoggingOn {

    /**
     * Attempts to log in a customer by prompting for their ID, password, and
     * a security question answer. The login process allows for a limited number
     * of password attempts before returning to the main menu.
     *
     * @param input          The {@link Scanner} object used for reading user input
     *                       from the console.
     * @param storageCreator The {@link CustomerStorageCreator} instance used to
     *                       access
     *                       the stored list of customer accounts.
     * @return The {@link Customer} object if login is successful (ID, password, and
     *         security answer are all valid); otherwise, returns {@code null}.
     */
    public static Customer performLogin(Scanner input, CustomerStorageCreator storageCreator) {
        Customer loggedInCustomer = null;
        int loginAttempts = 0;
        final int MAX_ATTEMPTS = 3; // Maximum number of password attempts allowed

        // Loop for password attempts
        while (loginAttempts < MAX_ATTEMPTS) {
            System.out.println("--- Customer Log On ---");
            System.out.print("Enter Customer ID: ");
            String enteredId = input.nextLine();

            System.out.print("Enter Password: ");
            String enteredPassword = input.nextLine();

            // Retrieve all customers from storage
            ArrayList<Customer> allCustomers = storageCreator.getAllCustomers();
            boolean idFound = false; // Flag to track if the entered ID exists

            // Iterate through customers to find a match for the entered ID
            for (Customer customer : allCustomers) {
                if (customer.getId().equals(enteredId)) {
                    idFound = true; // ID found
                    if (customer.validatePassword(enteredPassword)) {
                        loggedInCustomer = customer; // Password is valid, set the logged-in customer
                        System.out.println("ID and password are valid.");
                        break; // Exit the for loop as customer is found and password is correct
                    } else {
                        System.out.println("Error: Incorrect password.");
                        loginAttempts++; // Increment attempt counter
                        if (loginAttempts == MAX_ATTEMPTS) {
                            System.out.println("Maximum login attempts reached. Returning to main menu.");
                            return null; // All attempts exhausted, return null
                        }
                        break; // Exit the for loop to re-prompt for ID/password
                    }
                }
            }

            // If a customer was successfully logged in (ID and password matched), exit the
            // while loop
            if (loggedInCustomer != null) {
                break;
            }

            // If the ID was not found after checking all customers
            if (!idFound) {
                System.out.println("Error: No account found with that ID. Returning to main menu.");
                return null; // ID doesn't exist, return null
            }
        }

        // Safety check: if for some reason loggedInCustomer is still null here, return
        // null
        // This case should ideally be covered by the MAX_ATTEMPTS and !idFound checks.
        if (loggedInCustomer == null) {
            return null;
        }

        // Security question verification after successful ID and password validation
        System.out.println("\n--- Security Verification ---");
        System.out.println("Security Question: " + loggedInCustomer.getSecurityQuestion());
        System.out.print("Enter your answer: ");
        String enteredSecurityAnswer = input.nextLine();

        // Validate the security answer
        if (loggedInCustomer.validateSecurityAnswer(enteredSecurityAnswer)) {
            return loggedInCustomer; // Login fully successful, return the authenticated customer object
        } else {
            System.out.println("Error: Incorrect security answer. Returning to main menu.");
            return null; // Security answer incorrect, return null
        }
    }
}
