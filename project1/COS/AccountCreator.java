package project1.COS;

import java.util.Scanner;

public class AccountCreator {

    /**
     * Guides the user through the process of creating a new customer account,
     * including input validation for customer ID, password, and credit card number.
     *
     * @param input          The Scanner object for user input.
     * @param storageCreator The CustomerStorageCreator for managing customer data
     *                       persistence.
     * @return true if the account is successfully created, false if cancelled or an
     *         error occurs.
     */
    public static boolean createNewAccount(Scanner input, CustomerStorageCreator storageCreator) {
        String customerId;
        String password;
        String name;
        String address;
        String creditCard;
        String securityQuestion;
        String securityAnswer;

        // Step 1 & 2 & Alternative Step 3: Customer ID entry and validation
        while (true) {
            System.out.println("Enter customer ID (or 'exit' to cancel account creation):");
            customerId = input.nextLine();

            if (customerId.equalsIgnoreCase("exit")) {
                System.out.println("Account creation cancelled.");
                return false; // Indicate cancellation
            } else if (storageCreator.isIDAvailable(customerId)) {
                break;
            } else {
                System.out.println("Error: This ID already exists. Please choose a different ID.");
            }
        }

        // Step 3 & 4 & Alternative Step 5: Password entry and validation
        while (true) {
            System.out.println(
                    "Enter password (minimum 6 characters, must include a digit, a special character (@, #, $, %, &, *), and an uppercase letter):");
            password = input.nextLine();
            if (isValidPassword(password)) {
                break;
            } else {
                System.out.println("Password does not meet complexity requirements. Please try again.");
            }
        }

        // Step 5: Customer name
        System.out.println("Enter customer name:");
        name = input.nextLine();

        // Step 6: Customer address
        System.out.println("Enter customer address:");
        address = input.nextLine();

        // Step 7: Credit Card Number with validation
        while (true) { // Loop for credit card validation
            System.out.println("Enter credit card number:");
            creditCard = input.nextLine();
            // Validate if credit card contains only numbers using regex
            if (creditCard.matches("\\d+")) { // "\\d+" matches one or more digits
                break; // Valid, exit loop
            } else {
                System.out.println("Invalid credit card number. It must contain only numbers. Please re-enter.");
            }
        }

        // Step 8: Security Question
        System.out.println("Choose a security question:");
        System.out.println("1. What is your mother's maiden name?");
        System.out.println("2. What was your first pet's name?");
        System.out.println("3. What is your favorite book?");
        System.out.print("Enter your choice (1-3): ");
        String securityChoice = input.nextLine();

        switch (securityChoice) {
            case "1":
                securityQuestion = "What is your mother's maiden name?";
                break;
            case "2":
                securityQuestion = "What was your first pet's name?";
                break;
            case "3":
                securityQuestion = "What is your favorite book?";
                break;
            default:
                securityQuestion = "What is your mother's maiden name?"; // Default
                System.out.println("Invalid choice, defaulting to 'What is your mother's maiden name?'.");
                break;
        }

        System.out.println("Enter the answer to your security question:");
        securityAnswer = input.nextLine();

        // Create and save the new customer
        Customer newCustomer = new Customer(customerId, password, name, address, creditCard, securityQuestion,
                securityAnswer);
        storageCreator.addCustomer(newCustomer);
        System.out.println("Account created successfully!");
        return true; // Indicate success
    }

    /**
     * Helper method to validate password complexity based on predefined rules.
     * 
     * @param password The password string to validate.
     * @return true if the password meets complexity requirements, false otherwise.
     */
    private static boolean isValidPassword(String password) {
        if (password.length() < 6) {
            return false;
        }

        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        boolean hasUpperCase = false;

        String specialChars = "@#$%&*"; // Allowed special characters

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (specialChars.contains(String.valueOf(c))) {
                hasSpecialChar = true;
            } else if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            }
        }
        return hasDigit && hasSpecialChar && hasUpperCase;
    }
}