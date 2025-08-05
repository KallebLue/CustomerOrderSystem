package project1.COS;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Comparator; // For sorting orders

/**
 * Handles the viewing of past orders for a logged-in customer.
 */
public class OrderViewer {

    /**
     * Displays all past orders for a given customer ID.
     *
     * @param input The Scanner object for user input (can be used for future filtering/sorting options).
     * @param customerId The ID of the customer whose orders are to be viewed.
     * @param orderFileStorage The OrderFileStorage instance to retrieve order data from.
     */
    public static void viewOrders(Scanner input, String customerId, OrderFileStorage orderFileStorage) {
        System.out.println("\n--- Viewing Past Orders for Customer ID: " + customerId + " ---");

        // Retrieve all orders from storage
        ArrayList<OrderCreator> allOrders = orderFileStorage.getAllOrders();
        ArrayList<OrderCreator> customerOrders = new ArrayList<>();

        // Filter orders to only include those belonging to the current customer
        for (OrderCreator order : allOrders) {
            if (order.getCustomerId().equals(customerId)) {
                customerOrders.add(order);
            }
        }

        if (customerOrders.isEmpty()) {
            System.out.println("No past orders found for this customer.");
        } else {
            // Sort orders by date, most recent first, to display them chronologically
            customerOrders.sort(Comparator.comparing(OrderCreator::getOrderDate).reversed());

            // Display each order's details
            for (OrderCreator order : customerOrders) {
                System.out.println(order.toString()); 
                System.out.println(); 
            }
        }
        System.out.println("------------------------------------------");
    }
}