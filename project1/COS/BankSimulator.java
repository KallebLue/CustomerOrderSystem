package project1.COS;

import java.util.Random;

public class BankSimulator {

    private static final Random random = new Random();

    /**
     * Simulates a bank charge attempt.
     * For demonstration, it randomly approves 80% of the time,
     * or denies if the card number is "invalid".
     *
     * @param creditCardNumber The credit card number to charge.
     * @param amount           The amount to charge.
     * @return A 4-digit authorization number if approved, or null if denied.
     */
    public static String chargeCard(String creditCardNumber, double amount) {
        System.out.println(
                "Contacting bank to charge $" + String.format("%.2f", amount) + " to card " + creditCardNumber + "...");

        // Simulate invalid card number
        if (creditCardNumber == null || creditCardNumber.trim().isEmpty() || creditCardNumber.contains("invalid")) {
            System.out.println("Bank Denied: Invalid credit card format/number.");
            return null;
        }

        // Simulate insufficient funds/random denial (20% chance)
        if (random.nextDouble() < 0.2) {
            System.out.println("Bank Denied: Insufficient funds or general error.");
            return null;
        }

        // Simulate success
        // Generate a random 4-digit authorization number
        String authNumber = String.format("%04d", random.nextInt(10000));
        System.out.println("Bank Approved! Authorization Number: " + authNumber);
        return authNumber;
    }
}