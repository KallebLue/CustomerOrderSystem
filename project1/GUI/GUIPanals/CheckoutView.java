package project1.GUI.GUIPanals;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import project1.COS.BankSimulator;
import project1.COS.Cart;
import project1.COS.Customer;
import project1.COS.CustomerStorageCreator;
import project1.COS.Merchandise;
import project1.COS.OrderCreator;
import project1.COS.OrderFileStorage;
import project1.GUI.CustomerOrderSystemGUI;

import java.util.Map;

/**
 * Represents the graphical user interface panel for the checkout process in the
 * Customer Ordering System.
 * This view allows authenticated users to review their cart, select a delivery
 * method,
 * input or confirm credit card details, process payment via the
 * {@link BankSimulator},
 * and finalize their order. It interacts with various backend components and
 * the main GUI application.
 */
public class CheckoutView {

    /** A reference to the main GUI application for scene switching and alerts. */
    private CustomerOrderSystemGUI mainApp;
    /** The current shopping cart instance for the user. */
    private Cart currentCart;
    /**
     * Manages the persistence of customer data, used for saving updated customer
     * info.
     */
    private CustomerStorageCreator customerStorageCreator;
    /** Manages the persistence of order data, used for saving new orders. */
    private OrderFileStorage orderFileStorage;
    /** The currently logged-in customer; null if no user is logged in. */
    private Customer loggedInCustomer;

    /** The JavaFX scene managed by this view. */
    private Scene scene;
    /** Label to display a summary of items in the cart before checkout. */
    private Label checkoutCartSummaryLabel;
    /** Radio button for selecting mail delivery. */
    private RadioButton mailDeliveryRadio;
    /** Radio button for selecting in-store pickup. */
    private RadioButton inStorePickupRadio;
    /** Label to display the calculated delivery fee. */
    private Label checkoutDeliveryFeeLabel;
    /**
     * Label to display the final total amount of the order, including fees and
     * taxes.
     */
    private Label checkoutFinalTotalLabel;
    /** Text field for entering or confirming the credit card number. */
    private TextField checkoutCreditCardField;
    /**
     * Label to display messages or feedback to the user during checkout (e.g.,
     * errors, warnings).
     */
    private Label checkoutMessageLabel;

    /**
     * Constructs a new CheckoutView.
     * Initializes the UI components and sets up necessary backend dependencies.
     *
     * @param mainApp                A reference to the main
     *                               {@link CustomerOrderSystemGUI} application.
     * @param currentCart            The {@link Cart} instance to be checked out.
     * @param customerStorageCreator The {@link CustomerStorageCreator} for customer
     *                               data operations.
     * @param orderFileStorage       The {@link OrderFileStorage} for saving order
     *                               data.
     */
    public CheckoutView(CustomerOrderSystemGUI mainApp, Cart currentCart,
            CustomerStorageCreator customerStorageCreator, OrderFileStorage orderFileStorage) {
        this.mainApp = mainApp;
        this.currentCart = currentCart;
        this.customerStorageCreator = customerStorageCreator;
        this.orderFileStorage = orderFileStorage;
        initializeUI();
    }

    /**
     * Initializes the graphical user interface components for the checkout view.
     * This method sets up the layout, labels, radio buttons, text fields, and
     * buttons,
     * and assigns their respective properties and event handlers.
     */
    private void initializeUI() {
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20));

        Label title = new Label("--- Proceed to Checkout ---");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        checkoutCartSummaryLabel = new Label();
        checkoutCartSummaryLabel.setStyle("-fx-font-weight: bold;");

        ToggleGroup deliveryGroup = new ToggleGroup();
        mailDeliveryRadio = new RadioButton(
                String.format("Mail Delivery (Fee: $%.2f)", OrderCreator.MAIL_DELIVERY_FEE));
        mailDeliveryRadio.setToggleGroup(deliveryGroup);
        mailDeliveryRadio.setSelected(true);
        inStorePickupRadio = new RadioButton("In-store Pickup (Free)");
        inStorePickupRadio.setToggleGroup(deliveryGroup);

        checkoutDeliveryFeeLabel = new Label();
        checkoutFinalTotalLabel = new Label();

        // Listener to update totals when delivery method changes
        deliveryGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> updateCheckoutTotals());

        Label creditCardLabel = new Label("Credit Card Number:");
        checkoutCreditCardField = new TextField();
        checkoutCreditCardField.setPromptText("Enter new credit card if needed");

        Button processOrderButton = new Button("Process Order");
        processOrderButton.setId("processOrderButton"); // Assign an ID for easy lookup
        processOrderButton.setOnAction(e -> handleProcessOrder());

        checkoutMessageLabel = new Label("");
        checkoutMessageLabel.setStyle("-fx-text-fill: red;");

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> mainApp.showSessionManagerScene());

        vbox.getChildren().addAll(title, checkoutCartSummaryLabel,
                new Separator(),
                new Label("Select Delivery Method:"),
                mailDeliveryRadio, inStorePickupRadio,
                checkoutDeliveryFeeLabel, checkoutFinalTotalLabel,
                new Separator(),
                creditCardLabel, checkoutCreditCardField,
                processOrderButton, checkoutMessageLabel, backButton);

        scene = new Scene(vbox, 500, 600);
    }

    /**
     * Returns the JavaFX {@link Scene} object associated with this CheckoutView.
     *
     * @return The scene of the checkout view.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Sets the current customer and cart for this checkout view.
     * This method should be called before displaying the checkout view to ensure
     * it operates on the correct data.
     *
     * @param customer The {@link Customer} object currently logged in.
     * @param cart     The {@link Cart} object containing items for checkout.
     */
    public void setCustomerAndCart(Customer customer, Cart cart) {
        this.loggedInCustomer = customer;
        this.currentCart = cart;
    }

    /**
     * Updates the display of the checkout summary, including cart items,
     * and enables/disables the credit card field and process button based on login
     * status.
     * This method should be called before showing the checkout scene or whenever
     * cart/login
     * status changes.
     */
    public void updateCheckoutDisplay() {
        if (currentCart.isEmpty()) {
            checkoutMessageLabel.setText("Your cart is empty. Cannot proceed to checkout.");
            return;
        }

        // Lookup the process order button to enable/disable it
        Button processButton = (Button) scene.lookup("#processOrderButton");

        if (loggedInCustomer == null) {
            // If not logged in, disable credit card field and process button, show login
            // prompt
            checkoutCreditCardField.setDisable(true);
            checkoutCreditCardField.setText("Please log in to proceed.");
            checkoutMessageLabel.setText("You must be logged in to complete your order.");
            if (processButton != null) {
                processButton.setDisable(true);
            }
        } else {
            // If logged in, enable fields and set default credit card
            checkoutCreditCardField.setDisable(false);
            checkoutCreditCardField.setText(loggedInCustomer.getCreditCard());
            checkoutMessageLabel.setText("");
            if (processButton != null) {
                processButton.setDisable(false);
            }
        }

        checkoutCartSummaryLabel.setText("Cart Summary:\n" + getCartSummaryText());
        updateCheckoutTotals(); // Recalculate and display totals
    }

    /**
     * Generates a formatted text summary of the items currently in the shopping
     * cart.
     *
     * @return A {@link String} containing a detailed list of cart items, their
     *         quantities, and prices.
     */
    private String getCartSummaryText() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Merchandise, Integer> entry : currentCart.getItems().entrySet()) {
            Merchandise item = entry.getKey();
            int quantity = entry.getValue();
            sb.append(String.format("%d x %s ($%.2f each) = $%.2f\n",
                    quantity, item.getName(), item.getCurrentPrice(),
                    (quantity * item.getCurrentPrice())));
        }
        sb.append(String.format("\nSubtotal: $%.2f\n", currentCart.getSubtotal()));
        sb.append(String.format("Taxes (8%%): $%.2f\n", currentCart.getTaxAmount()));
        return sb.toString();
    }

    /**
     * Updates the displayed delivery fee and final total based on the selected
     * delivery method
     * and the current cart total.
     */
    private void updateCheckoutTotals() {
        double deliveryFee = mailDeliveryRadio.isSelected() ? OrderCreator.MAIL_DELIVERY_FEE : 0.0;
        double currentTotal = currentCart.getTotal() + deliveryFee;

        checkoutDeliveryFeeLabel.setText(String.format("Delivery Fee: $%.2f", deliveryFee));
        checkoutFinalTotalLabel.setText(String.format("Final Total: $%.2f", currentTotal));
    }

    /**
     * Handles the process order action when the "Process Order" button is clicked.
     * This method performs several validations (empty cart, login status, credit
     * card format),
     * simulates the bank charge, and if successful, creates and saves the order,
     * then clears the cart and navigates back to the main session menu.
     */
    private void handleProcessOrder() {
        if (currentCart.isEmpty()) {
            checkoutMessageLabel.setText("Error: Your cart is empty.");
            return;
        }

        // Ensure user is logged in before processing order
        if (loggedInCustomer == null) {
            mainApp.showAlert(Alert.AlertType.INFORMATION, "Login Required",
                    "Please log in or create an account to complete your order.");
            mainApp.showLoginScene(); // Redirect to login
            return;
        }

        String deliveryMethod = mailDeliveryRadio.isSelected() ? "Mail Delivery" : "In-store Pickup";
        double deliveryFee = mailDeliveryRadio.isSelected() ? OrderCreator.MAIL_DELIVERY_FEE : 0.0;
        double finalTotal = currentCart.getTotal() + deliveryFee;

        String creditCardToUse = checkoutCreditCardField.getText();
        if (creditCardToUse.isEmpty()) {
            checkoutMessageLabel.setText("Please enter a credit card number.");
            return;
        }

        // Basic validation for credit card number (numeric only)
        if (!creditCardToUse.matches("\\d+")) {
            checkoutMessageLabel.setText("Invalid credit card number. It must contain only numbers.");
            return;
        }

        // Update customer's credit card if it was changed in the field
        if (!creditCardToUse.equals(loggedInCustomer.getCreditCard())) {
            loggedInCustomer.setCreditCard(creditCardToUse);
            customerStorageCreator.addCustomer(loggedInCustomer); // Save updated customer info
        }

        // Call the BankSimulator and get the ChargeResult with specific details
        BankSimulator.ChargeResult result = BankSimulator.chargeCard(creditCardToUse, finalTotal);

        if (result.isApproved()) {
            // If bank approves, finalize the order
            String orderId = "ORD-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            OrderCreator newOrder = new OrderCreator(
                    orderId,
                    loggedInCustomer.getId(),
                    currentCart.getItems(),
                    finalTotal,
                    deliveryMethod,
                    deliveryFee);
            newOrder.setBankAuthorizationNumber(result.getAuthorizationNumber()); // Use auth number from result

            orderFileStorage.addOrder(newOrder); // Save order to persistence
            currentCart.clear(); // Clear the cart after successful order

            // Show confirmation alert and navigate back to main session
            mainApp.showAlert(Alert.AlertType.INFORMATION, "Order Placed",
                    "Order placed successfully!\n" + newOrder.toString());
            mainApp.showSessionManagerScene();
        } else {
            // Display the specific denial reason from the ChargeResult
            checkoutMessageLabel.setText(
                    "Bank charge denied: " + result.getDenialReason() + " Please try again or use a different card.");
        }
    }
}