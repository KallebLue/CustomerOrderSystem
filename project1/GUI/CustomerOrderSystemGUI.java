package project1.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import project1.GUI.GUIPanals.*;
import project1.COS.Cart;
import project1.COS.Customer;
import project1.COS.CustomerStorageCreator;
import project1.COS.MerchCatalog;
import project1.COS.OrderFileStorage;

/**
 * The main entry point for the Customer Ordering System GUI application.
 * This class extends {@link javafx.application.Application} and is responsible
 * for
 * setting up the primary stage, managing application-wide state (like the
 * current customer and cart),
 * and orchestrating scene transitions between different GUI panels.
 * It integrates the backend logic (COS package) with the JavaFX user interface.
 */
public class CustomerOrderSystemGUI extends Application {

    /** The primary window (stage) for this application. */
    private Stage primaryStage;
    /** Manages the persistence of customer data. */
    private CustomerStorageCreator customerStorageCreator;
    /** Manages the persistence of order data. */
    private OrderFileStorage orderFileStorage;
    /** Provides access to the merchandise catalog. */
    private MerchCatalog merchCatalog;
    /** Represents the current shopping cart for the user. */
    private Cart currentCart;
    /** Stores the currently logged-in customer; null if no user is logged in. */
    private Customer loggedInCustomer;

    // References to view panels
    /** The view panel for user login and initial welcome options. */
    private LoginView loginView;
    /** The view panel for creating new customer accounts. */
    private CreateAccountView createAccountView;
    /** The main menu view for logged-in customers. */
    private SessionManagerView sessionManagerView;
    /** The view panel for browsing and adding merchandise to the cart. */
    private MerchandiseView merchandiseView;
    /** The view panel for displaying and managing the shopping cart. */
    private CartView cartView;
    /** The view panel for the checkout process. */
    private CheckoutView checkoutView;
    /** The view panel for viewing a customer's past orders. */
    private OrderViewerView orderViewerView;

    /**
     * The main entry point for all JavaFX applications. The start method is called
     * after the init method has returned,
     * and after the system is ready for the application to begin running.
     * This method sets up the primary stage, initializes backend components,
     * initializes all GUI view panels, and displays the initial login scene.
     *
     * @param primaryStage The primary stage for this application, onto which the
     *                     application scene can be set.
     *                     The first stage is constructed by the platform.
     * @throws Exception if something goes wrong.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Customer Ordering System");

        // Initialize backend components
        customerStorageCreator = new CustomerStorageCreator();
        orderFileStorage = new OrderFileStorage();
        merchCatalog = new MerchCatalog();
        currentCart = new Cart(); // Initialize a shared cart

        // Initialize all view panels, passing necessary dependencies and a reference to
        // this main app
        loginView = new LoginView(this, customerStorageCreator);
        createAccountView = new CreateAccountView(this, customerStorageCreator);
        sessionManagerView = new SessionManagerView(this);
        merchandiseView = new MerchandiseView(this, merchCatalog, currentCart);
        cartView = new CartView(this, currentCart);
        checkoutView = new CheckoutView(this, currentCart, customerStorageCreator, orderFileStorage);
        orderViewerView = new OrderViewerView(this, orderFileStorage);

        // Set up initial scene
        showLoginScene();
    }

    /**
     * Sets the current scene on the primary stage and makes the stage visible.
     *
     * @param scene The {@link Scene} object to display.
     */
    public void setScene(Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- Scene Switching Methods ---
    /**
     * Displays the {@link LoginView} scene, which serves as the application's
     * welcome screen.
     * It also clears any previous login state and the current cart, and resets the
     * login fields.
     */
    public void showLoginScene() {
        // Clear any previous login state
        loggedInCustomer = null;
        currentCart.clear(); // Clear cart on returning to login
        loginView.resetFields(); // Reset fields on login view
        setScene(loginView.getScene());
    }

    /**
     * Displays the {@link CreateAccountView} scene, allowing a user to create a new
     * customer account.
     * Resets the fields in the Create Account view before displaying.
     */
    public void showCreateAccountScene() {
        createAccountView.resetFields();
        setScene(createAccountView.getScene());
    }

    /**
     * Displays the {@link SessionManagerView} scene, which serves as the main menu
     * for a logged-in customer.
     * This method ensures that a customer is actually logged in before attempting
     * to show the session manager.
     * It also sets the logged-in customer in the session manager view to display a
     * personalized welcome.
     */
    public void showSessionManagerScene() {
        if (loggedInCustomer == null) {
            // Should not happen if flow is correct, but as a safeguard
            showLoginScene();
            return;
        }
        sessionManagerView.setLoggedInCustomer(loggedInCustomer);
        setScene(sessionManagerView.getScene());
    }

    /**
     * Displays the {@link MerchandiseView} scene, allowing users to browse
     * available products and add them to the cart.
     * Refreshes the merchandise display to ensure the latest catalog is shown.
     */
    public void showMerchandiseViewScene() {
        merchandiseView.refreshMerchandise(); // Ensure latest catalog is displayed
        setScene(merchandiseView.getScene());
    }

    /**
     * Displays the {@link CartView} scene, showing the current contents of the
     * user's shopping cart.
     * Refreshes the cart display to show up-to-date items and totals.
     */
    public void showViewCartScene() {
        cartView.updateCartDisplay(); // Refresh cart display
        setScene(cartView.getScene());
    }

    /**
     * Displays the {@link CheckoutView} scene, guiding the user through the order
     * placement process.
     * If the cart is empty, it shows a warning and redirects to the merchandise
     * view.
     * It passes the current customer and cart to the checkout view and updates its
     * display.
     */
    public void showCheckoutScene() {
        if (currentCart.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty Cart",
                    "Your cart is empty. Please add items before checking out.");
            showMerchandiseViewScene(); // Redirect to browse if cart is empty
            return;
        }
        checkoutView.setCustomerAndCart(loggedInCustomer, currentCart); // Pass current customer and cart
        checkoutView.updateCheckoutDisplay(); // Refresh checkout details
        setScene(checkoutView.getScene());
    }

    /**
     * Displays the {@link OrderViewerView} scene, allowing a logged-in customer to
     * view their past orders.
     * Sets the current customer in the order viewer and loads their orders for
     * display.
     */
    public void showViewOrdersScene() {
        orderViewerView.setCustomer(loggedInCustomer); // Pass current customer
        orderViewerView.loadAndDisplayOrders(); // Load and display orders for this customer
        setScene(orderViewerView.getScene());
    }

    // --- State Management Methods (called by views to update main app state) ---
    /**
     * Retrieves the currently logged-in {@link Customer} object.
     *
     * @return The {@link Customer} object if logged in, otherwise null.
     */
    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    /**
     * Sets the currently logged-in {@link Customer} object. This method is
     * typically called
     * after a successful login.
     *
     * @param customer The {@link Customer} object to set as logged in.
     */
    public void setLoggedInCustomer(Customer customer) {
        this.loggedInCustomer = customer;
    }

    /**
     * Retrieves the current shopping {@link Cart} object, which is shared across
     * various views.
     *
     * @return The {@link Cart} object.
     */
    public Cart getCurrentCart() {
        return currentCart;
    }

    /**
     * Displays a JavaFX Alert dialog to the user.
     * This is a utility method used consistently across different GUI panels for
     * feedback.
     *
     * @param type    The type of alert (e.g., {@link Alert.AlertType#INFORMATION},
     *                {@link Alert.AlertType#WARNING}).
     * @param title   The title of the alert window.
     * @param message The main content text of the alert.
     */
    public void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text
        alert.setContentText(message);
        alert.showAndWait(); // Show the alert and wait for user to dismiss it
    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}