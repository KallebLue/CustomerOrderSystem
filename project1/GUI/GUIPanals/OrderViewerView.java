package project1.GUI.GUIPanals;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import project1.COS.Customer;
import project1.COS.OrderCreator;
import project1.COS.OrderFileStorage;
import project1.GUI.CustomerOrderSystemGUI;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Represents the graphical user interface panel for viewing a customer's past
 * orders.
 * This view displays a list of orders associated with the logged-in customer,
 * allows selection of an order to view its details, and provides navigation
 * back to the main menu.
 * It interacts with {@link OrderFileStorage} to retrieve order data and with
 * the main {@link CustomerOrderSystemGUI} for scene transitions.
 */
public class OrderViewerView {

    /** A reference to the main GUI application for scene switching and alerts. */
    private CustomerOrderSystemGUI mainApp;
    /** Manages the persistence and retrieval of order data. */
    private OrderFileStorage orderFileStorage;
    /** The currently logged-in customer, whose orders are to be displayed. */
    private Customer loggedInCustomer;

    /** The JavaFX scene managed by this view. */
    private Scene scene;
    /**
     * ListView to display a scrollable list of {@link OrderCreator} objects (past
     * orders).
     */
    private ListView<OrderCreator> ordersListView;
    /**
     * TextArea to display the detailed information of the currently selected order.
     */
    private TextArea orderDetailsTextArea;
    /**
     * Label to display messages or feedback to the user regarding order viewing.
     */
    private Label viewOrdersMessageLabel;
    /**
     * Label to display the title of the view, dynamically updated with the
     * customer's ID.
     */
    private Label titleLabel;

    /**
     * Constructs a new OrderViewerView.
     * Initializes the UI components and sets up necessary backend dependencies.
     *
     * @param mainApp          A reference to the main
     *                         {@link CustomerOrderSystemGUI} application.
     * @param orderFileStorage The {@link OrderFileStorage} for order data
     *                         operations.
     */
    public OrderViewerView(CustomerOrderSystemGUI mainApp, OrderFileStorage orderFileStorage) {
        this.mainApp = mainApp;
        this.orderFileStorage = orderFileStorage;
        initializeUI();
    }

    /**
     * Initializes the graphical user interface components for the order viewer.
     * This method sets up the layout (VBox), labels, ListView, TextArea, and
     * buttons,
     * and assigns their respective properties and event handlers.
     */
    private void initializeUI() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20));

        titleLabel = new Label();
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ordersListView = new ListView<>();
        orderDetailsTextArea = new TextArea();
        orderDetailsTextArea.setEditable(false); // Make text area read-only
        orderDetailsTextArea.setWrapText(true); // Enable text wrapping
        orderDetailsTextArea.setPrefHeight(200); // Set preferred height for the text area

        viewOrdersMessageLabel = new Label(""); // For displaying messages (e.g., no orders found)
        viewOrdersMessageLabel.setStyle("-fx-text-fill: blue;");

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> mainApp.showSessionManagerScene());

        vbox.getChildren().addAll(titleLabel, viewOrdersMessageLabel, ordersListView, new Label("Order Details:"),
                orderDetailsTextArea, backButton);

        scene = new Scene(vbox, 600, 600);
    }

    /**
     * Returns the JavaFX {@link Scene} object associated with this OrderViewerView.
     *
     * @return The scene of the order viewer view.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Sets the currently logged-in {@link Customer} for this view and updates the
     * title label.
     * This method must be called before {@link #loadAndDisplayOrders()} to ensure
     * orders for the correct customer are loaded.
     *
     * @param customer The {@link Customer} object whose orders are to be viewed.
     */
    public void setCustomer(Customer customer) {
        this.loggedInCustomer = customer;
        titleLabel.setText("--- Viewing Past Orders for " + loggedInCustomer.getId() + " ---");
    }

    /**
     * Loads and displays the past orders for the currently set {@link Customer}.
     * Orders are retrieved from {@link OrderFileStorage}, filtered by customer ID,
     * sorted by date (most recent first), and then displayed in the
     * {@link ListView}.
     * An appropriate message is displayed if no orders are found or if no customer
     * is logged in.
     */
    public void loadAndDisplayOrders() {
        if (loggedInCustomer == null) {
            viewOrdersMessageLabel.setText("No customer logged in to view orders.");
            ordersListView.setItems(FXCollections.emptyObservableList());
            orderDetailsTextArea.clear();
            return;
        }

        ArrayList<OrderCreator> allOrders = orderFileStorage.getAllOrders();
        ArrayList<OrderCreator> customerOrders = new ArrayList<>();
        // Filter orders to only include those belonging to the logged-in customer
        for (OrderCreator order : allOrders) {
            if (order.getCustomerId().equals(loggedInCustomer.getId())) {
                customerOrders.add(order);
            }
        }
        // Sort customer's orders by date in reverse chronological order (most recent
        // first)
        customerOrders.sort(Comparator.comparing(OrderCreator::getOrderDate).reversed());

        if (customerOrders.isEmpty()) {
            viewOrdersMessageLabel.setText("No past orders found for this customer.");
            ordersListView.setItems(FXCollections.emptyObservableList());
            orderDetailsTextArea.clear();
        } else {
            viewOrdersMessageLabel.setText(""); // Clear message if orders are found
            ordersListView.setItems(FXCollections.observableArrayList(customerOrders));
            // Add a listener to display order details in the TextArea when an item is
            // selected in the ListView
            ordersListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    orderDetailsTextArea.setText(newVal.toString());
                } else {
                    orderDetailsTextArea.clear();
                }
            });
            // Automatically select the first order if available, to display its details
            // immediately
            if (!customerOrders.isEmpty()) {
                ordersListView.getSelectionModel().selectFirst();
            }
        }
    }
}