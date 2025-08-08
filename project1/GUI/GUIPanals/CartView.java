package project1.GUI.GUIPanals;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import project1.COS.Cart;
import project1.COS.Merchandise;
import project1.GUI.CustomerOrderSystemGUI;

import java.util.Map;

/**
 * Represents the graphical user interface panel for displaying the customer's
 * shopping cart.
 * This view allows users to see items in their cart, view subtotals and totals,
 * clear the cart, and proceed to checkout. It interacts with the {@link Cart}
 * backend object and the main {@link CustomerOrderSystemGUI} for scene
 * transitions and alerts.
 */
public class CartView {

    /** A reference to the main GUI application for scene switching and alerts. */
    private CustomerOrderSystemGUI mainApp;
    /** The shopping cart instance being displayed and managed by this view. */
    private Cart currentCart;

    /** The JavaFX scene managed by this view. */
    private Scene scene;
    /** TableView to display the list of items in the cart. */
    private TableView<CartItemDisplay> cartTableView;
    /** Label to display the subtotal of the cart. */
    private Label cartSubtotalLabel;
    /** Label to display the tax amount of the cart. */
    private Label cartTaxLabel;
    /** Label to display the grand total of the cart. */
    private Label cartTotalLabel;
    /** Label to display messages or feedback to the user regarding cart actions. */
    private Label cartMessageLabel;

    /**
     * Constructs a new CartView.
     * Initializes the UI components and sets up event handlers.
     *
     * @param mainApp     A reference to the main {@link CustomerOrderSystemGUI}
     *                    application.
     * @param currentCart The {@link Cart} instance to be displayed and managed.
     */
    public CartView(CustomerOrderSystemGUI mainApp, Cart currentCart) {
        this.mainApp = mainApp;
        this.currentCart = currentCart;
        initializeUI();
    }

    /**
     * Initializes the graphical user interface components for the cart view.
     * This method sets up the layout, table columns, labels, and buttons,
     * and assigns their respective properties and event handlers.
     */
    private void initializeUI() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20));

        Label title = new Label("--- Your Cart ---");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        cartTableView = new TableView<>();
        cartTableView.setPlaceholder(new Label("Your cart is empty.")); // Message when cart is empty in table

        // Define table columns for displaying cart item details
        TableColumn<CartItemDisplay, String> nameCol = new TableColumn<>("Item");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);

        TableColumn<CartItemDisplay, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityCol.setPrefWidth(80);

        TableColumn<CartItemDisplay, String> priceCol = new TableColumn<>("Price Each");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("priceEach"));
        priceCol.setPrefWidth(100);

        TableColumn<CartItemDisplay, String> lineTotalCol = new TableColumn<>("Line Total");
        lineTotalCol.setCellValueFactory(new PropertyValueFactory<>("lineTotal"));
        lineTotalCol.setPrefWidth(100);

        cartTableView.getColumns().addAll(nameCol, quantityCol, priceCol, lineTotalCol);

        // Labels for cart summary totals
        cartSubtotalLabel = new Label();
        cartTaxLabel = new Label();
        cartTotalLabel = new Label();
        cartMessageLabel = new Label(""); // For displaying transient messages (e.g., "Cart cleared.")
        cartMessageLabel.setStyle("-fx-text-fill: blue;");

        // Buttons for user actions
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> mainApp.showSessionManagerScene());

        Button clearCartButton = new Button("Clear Cart");
        clearCartButton.setOnAction(e -> handleClearCart());

        Button checkoutButton = new Button("Proceed to Checkout");
        checkoutButton.setOnAction(e -> mainApp.showCheckoutScene());

        // Layout for buttons
        HBox buttons = new HBox(10, backButton, clearCartButton, checkoutButton);
        buttons.setAlignment(Pos.CENTER);

        // Add all UI elements to the main VBox
        vbox.getChildren().addAll(title, cartTableView, cartSubtotalLabel, cartTaxLabel, cartTotalLabel,
                cartMessageLabel, buttons);

        // Create the scene
        scene = new Scene(vbox, 600, 500);
    }

    /**
     * Returns the JavaFX {@link Scene} object associated with this CartView.
     *
     * @return The scene of the cart view.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Updates the display of the cart table and the total summary labels.
     * This method should be called whenever the cart's contents or totals change
     * to ensure the UI reflects the most current state.
     */
    public void updateCartDisplay() {
        ObservableList<CartItemDisplay> cartItems = FXCollections.observableArrayList();
        for (Map.Entry<Merchandise, Integer> entry : currentCart.getItems().entrySet()) {
            Merchandise item = entry.getKey();
            int quantity = entry.getValue();
            // Create CartItemDisplay objects for the TableView
            cartItems.add(new CartItemDisplay(item.getName(), quantity, item.getCurrentPrice(),
                    (item.getCurrentPrice() * quantity)));
        }
        cartTableView.setItems(cartItems); // Update the TableView with new items

        // Update summary labels
        cartSubtotalLabel.setText(String.format("Subtotal: $%.2f", currentCart.getSubtotal()));
        cartTaxLabel.setText(String.format("Taxes (8%%): $%.2f", currentCart.getTaxAmount()));
        cartTotalLabel.setText(String.format("Total: $%.2f", currentCart.getTotal()));
        cartMessageLabel.setText(""); // Clear previous messages to avoid stale feedback
    }

    /**
     * Handles the action of clearing the shopping cart.
     * It calls the clear method on the {@link Cart} object, updates the display,
     * and shows a confirmation message to the user.
     */
    private void handleClearCart() {
        currentCart.clear(); // Clear all items from the cart
        updateCartDisplay(); // Refresh the display to show an empty cart
        cartMessageLabel.setText("Cart cleared."); // Display a confirmation message
    }

    /**
     * Helper class for formatting and displaying individual cart items in the
     * {@link TableView}.
     * This class uses JavaFX Properties to enable data binding with
     * {@link PropertyValueFactory}.
     */
    public static class CartItemDisplay {
        private final String name;
        private final int quantity;
        private final String priceEach;
        private final String lineTotal;

        /**
         * Constructs a new CartItemDisplay object.
         *
         * @param name      The name of the merchandise item.
         * @param quantity  The quantity of the item in the cart.
         * @param priceEach The price of a single unit of the item.
         * @param lineTotal The total price for this specific item (quantity *
         *                  priceEach).
         */
        public CartItemDisplay(String name, int quantity, double priceEach, double lineTotal) {
            this.name = name;
            this.quantity = quantity;
            this.priceEach = String.format("$%.2f", priceEach); // Format price as currency string
            this.lineTotal = String.format("$%.2f", lineTotal); // Format total as currency string
        }

        /**
         * Returns the name of the item.
         * 
         * @return The item name.
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the quantity of the item.
         * 
         * @return The item quantity.
         */
        public int getQuantity() {
            return quantity;
        }

        /**
         * Returns the price of each item, formatted as a string.
         * 
         * @return The formatted price per item.
         */
        public String getPriceEach() {
            return priceEach;
        }

        /**
         * Returns the total price for this line item, formatted as a string.
         * 
         * @return The formatted line total.
         */
        public String getLineTotal() {
            return lineTotal;
        }
    }
}