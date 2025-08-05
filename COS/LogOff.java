package project1.COS;

/**
 * This class handles the logoff functionality for the customer.
 * It is designed to be called by other classes, such as SessionManager,
 * to perform the logoff action.
 */
public class LogOff { // Renamed class from LogOut to LogOff

    /**
     * Performs the customer logoff action.
     * In a more complex system, this method might also handle
     * session invalidation, clearing user data from memory, etc.
     */
    public static void performLogoff() { // Renamed method from performLogout to performLogoff
        System.out.println("Logging out...");
        System.out.println("You have been successfully logged out.");
        // In a real application, this might involve more complex actions
        // like clearing session tokens or redirecting to a login screen.
    }
}