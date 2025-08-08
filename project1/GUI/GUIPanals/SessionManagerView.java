package project1.GUI.GUIPanals;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import project1.COS.Customer;
import project1.GUI.CustomerOrderSystemGUI;
import javafx.scene.control.Alert;

/**
 * Represents the graphical user interface panel for the main menu accessible
 * after a successful customer login.
 * This view provides various options for authenticated users, such as browsing
 * merchandise,
 * viewing the cart, proceeding to checkout, viewing past orders, and logging
 * out.
 * It interacts with the main {@link CustomerOrderSystemGUI} for navigating
 * between different application scenes.
 */
public class SessionManagerView {

    /** A reference to the main GUI application for scene switching and alerts. */
    private CustomerOrderSystemGUI mainApp;
    /** The JavaFX scene managed by this view. */
    private Scene scene;
    /**
     * Label to display a personalized welcome message to the logged-in customer.
     */
    private Label welcomeLabel;
    /**
     * Stores the currently logged-in customer, used to personalize the welcome
     * message.
     */
    private Customer loggedInCustomer;

    /**
     * Constructs a new SessionManagerView.
     * Initializes the UI components and sets up necessary dependencies.
     *
     * @param mainApp A reference to the main {@link CustomerOrderSystemGUI}
     *                application.
     */
    public SessionManagerView(CustomerOrderSystemGUI mainApp) {
        this.mainApp = mainApp;
        initializeUI();
    }

    /**
     * Initializes the graphical user interface components for the session manager
     * view.
     * This method sets up the layout (VBox), the welcome label, and action buttons
     * for
     * various logged-in functionalities, assigning their event handlers.
     */
    private void initializeUI() {
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(25));

        welcomeLabel = new Label();
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button browseMerchandiseButton = new Button("1. Browse Merchandise & Add to Cart");
        Button viewCartButton = new Button("2. View Cart");
        Button proceedToCheckoutButton = new Button("3. Proceed to Checkout");
        Button viewPastOrdersButton = new Button("4. View Past Orders");
        Button logOutButton = new Button("5. Log Out");

        browseMerchandiseButton.setOnAction(e -> mainApp.showMerchandiseViewScene());
        viewCartButton.setOnAction(e -> mainApp.showViewCartScene());
        proceedToCheckoutButton.setOnAction(e -> mainApp.showCheckoutScene());
        viewPastOrdersButton.setOnAction(e -> mainApp.showViewOrdersScene());
        logOutButton.setOnAction(e -> handleLogOut());

        vbox.getChildren().addAll(welcomeLabel, browseMerchandiseButton, viewCartButton,
                proceedToCheckoutButton, viewPastOrdersButton, logOutButton);

        scene = new Scene(vbox, 400, 350);
    }

    /**
     * Returns the JavaFX {@link Scene} object associated with this
     * SessionManagerView.
     *
     * @return The scene of the session manager view.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Sets the currently logged-in {@link Customer} and updates the welcome message
     * displayed in the view.
     * This method is called by the main application after a successful login.
     *
     * @param customer The {@link Customer} object who has just logged in.
     */
    public void setLoggedInCustomer(Customer customer) {
        this.loggedInCustomer = customer;
        welcomeLabel.setText("Welcome, " + loggedInCustomer.getId() + "! You are now logged in.");
    }

    /**
     * Handles the log out action when the "Log Out" button is clicked.
     * It displays a log out confirmation alert and then navigates the user back
     * to the login scene, which also handles clearing the session state in the main
     * application.
     */
    private void handleLogOut() {
        mainApp.showAlert(Alert.AlertType.INFORMATION, "Logged Out", "You have been successfully logged out.");
        mainApp.showLoginScene(); // This will clear the cart and loggedInCustomer in mainApp
    }
}