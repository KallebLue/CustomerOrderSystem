package project1.COS;

import java.util.Random;

public class BankSimulator {

    private static final Random random = new Random();

    /**
     * Represents the result of a bank charge attempt, including approval status,
     * authorization number (if approved), and a denial reason (if not approved).
     */
    public static class ChargeResult {
        private final boolean approved;
        private final String authorizationNumber; // Will be non-null if approved
        private final String denialReason;        // Will be non-null if denied

        /**
         * Constructs a ChargeResult object.
         *
         * @param approved           True if the charge was approved, false otherwise.
         * @param authorizationNumber The authorization code if approved; null otherwise.
         * @param denialReason       The reason for denial if not approved; null otherwise.
         */
        public ChargeResult(boolean approved, String authorizationNumber, String denialReason) {
            this.approved = approved;
            this.authorizationNumber = authorizationNumber;
            this.denialReason = denialReason;
        }

        public boolean isApproved() {
            return approved;
        }

        public String getAuthorizationNumber() {
            return authorizationNumber;
        }

        public String getDenialReason() {
            return denialReason;
        }
    }

    /**
     * Simulates a bank charge attempt.
     * It now provides specific reasons for denial instead of just returning null.
     *
     * @param creditCardNumber The credit card number to charge.
     * @param amount           The amount to charge.
     * @return A {@link ChargeResult} object indicating approval status and details.
     */
    public static ChargeResult chargeCard(String creditCardNumber, double amount) {
        System.out.println(
                "Contacting bank to charge $" + String.format("%.2f", amount) + " to card " + creditCardNumber + "...");

        // Simulate invalid card number
        if (creditCardNumber == null || creditCardNumber.trim().isEmpty() || creditCardNumber.contains("invalid")) {
            System.out.println("Bank Denied: Invalid credit card format/number.");
            // Use the single constructor: approved=false, auth=null, reason="..."
            return new ChargeResult(false, null, "Invalid credit card format or number.");
        }

        // Simulate insufficient funds/random denial (20% chance)
        if (random.nextDouble() < 0.2) {
            System.out.println("Bank Denied: Insufficient funds or general error.");
            // Use the single constructor: approved=false, auth=null, reason="..."
            return new ChargeResult(false, null, "Insufficient funds or general bank error.");
        }

        // Simulate success
        // Generate a random 4-digit authorization number
        String authNumber = String.format("%04d", random.nextInt(10000));
        System.out.println("Bank Approved! Authorization Number: " + authNumber);
        // Use the single constructor: approved=true, auth="...", reason=null
        return new ChargeResult(true, authNumber, null);
    }
}
