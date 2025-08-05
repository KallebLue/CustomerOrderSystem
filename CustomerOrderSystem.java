import java.util.Scanner;

import project1.COS.*;

public class CustomerOrderSystem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        CustomerStorageCreator customerStorageCreator = new CustomerStorageCreator(); // Manages customer data persistence
        OrderFileStorage orderFileStorage = new OrderFileStorage(); // Manages order data persistence
        Cart sharedCart = new Cart(); // Create ONE cart object that will be used throughout the session

        // Start the pre-authentication session, passing the shared cart
        Customer loggedInCustomer = PreSessionManager.startPreAuthSession(input, customerStorageCreator, sharedCart);

        // If a customer successfully logged in during the pre-authentication session,
        // proceed to the main session, passing the now potentially populated shared cart.
        if (loggedInCustomer != null) {
            PostSessionManager.startSession(input, loggedInCustomer, customerStorageCreator, orderFileStorage, sharedCart);
        } else {
            // This path is typically reached if the user chose to exit from the pre-auth session.
            // If the user exited without logging in, they might still have items in their cart.
            // We could offer to save the cart, but for now, the cart just exists in memory
            // until the program ends.
            System.out.println("Application terminated.");
        }

        input.close(); // Close the scanner when the application exits
    }
}