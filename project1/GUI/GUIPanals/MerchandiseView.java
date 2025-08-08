package project1.GUI.GUIPanals;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import project1.COS.Cart;
import project1.COS.MerchCatalog;
import project1.COS.Merchandise;
import project1.GUI.CustomerOrderSystemGUI;

/**
 * Represents the graphical user interface panel for browsing merchandise and
 * adding items to the shopping cart.
 * This view displays the available products from the {@link MerchCatalog} in a
 * table,
 * allows users to select items, specify quantities, and add them to the
 * {@link Cart}.
 * It interacts with the main {@link CustomerOrderSystemGUI} for scene
 * transitions and alerts.
 */
public class MerchandiseView {

    /** A reference to the main GUI application for scene switching and alerts. */
    private CustomerOrderSystemGUI mainApp;
    /** The merchandise catalog containing all available products. */
    private MerchCatalog merchCatalog;
    /** The current shopping cart instance to which items are added. */
    private Cart currentCart;

    /** The JavaFX scene managed by this view. */
    private Scene scene;
    /** TableView to display the list of merchandise items. */
    private TableView<Merchandise> merchTableView;
    /**
     * Text field for entering the quantity of a selected item to add to the cart.
     */
    private TextField quantityField;
    /**
     * Label to display messages or feedback to the user regarding browsing or
     * adding items.
     */
    private Label browseMessageLabel;

    /**
     * Constructs a new MerchandiseView.
     * Initializes the UI components and sets up necessary backend dependencies.
     *
     * @param mainApp      A reference to the main {@link CustomerOrderSystemGUI}
     *                     application.
     * @param merchCatalog The {@link MerchCatalog} containing product information.
     * @param currentCart  The {@link Cart} instance to which items will be added.
     */
    public MerchandiseView(CustomerOrderSystemGUI mainApp, MerchCatalog merchCatalog, Cart currentCart) {
        this.mainApp = mainApp;
        this.merchCatalog = merchCatalog;
        this.currentCart = currentCart;
        initializeUI();
    }

    /**
     * Initializes the graphical user interface components for the merchandise view.
     * This method sets up the layout, table columns, input fields, and buttons,
     * and assigns their respective properties and event handlers.
     */
    private void initializeUI() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20));

        Label title = new Label("--- Browse Merchandise ---");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        merchTableView = new TableView<>();
        merchTableView.setPlaceholder(new Label("No merchandise available.")); // Message when table is empty

        // Define table columns for displaying merchandise details
        TableColumn<Merchandise, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(70);

        TableColumn<Merchandise, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(150);

        TableColumn<Merchandise, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descCol.setPrefWidth(200);

        TableColumn<Merchandise, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("currentPrice"));
        priceCol.setPrefWidth(100);

        merchTableView.getColumns().addAll(idCol, nameCol, descCol, priceCol);
        // Items will be populated dynamically by refreshMerchandise()

        // Controls for adding items to cart
        HBox addControls = new HBox(10);
        addControls.setAlignment(Pos.CENTER);
        Label quantityLabel = new Label("Quantity:");
        quantityField = new TextField("1"); // Default quantity to 1
        quantityField.setPrefWidth(50);
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setOnAction(e -> handleAddToCart());

        addControls.getChildren().addAll(quantityLabel, quantityField, addToCartButton);

        browseMessageLabel = new Label(""); // For displaying messages (e.g., item added, error)
        browseMessageLabel.setStyle("-fx-text-fill: blue;");

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> mainApp.showSessionManagerScene());

        // Add all UI elements to the main VBox
        vbox.getChildren().addAll(title, merchTableView, addControls, browseMessageLabel, backButton);

        // Create the scene
        scene = new Scene(vbox, 600, 500);
    }

    /**
     * Returns the JavaFX {@link Scene} object associated with this MerchandiseView.
     *
     * @return The scene of the merchandise view.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Refreshes the display of the merchandise table with the latest data from the
     * catalog.
     * This method also clears any previous messages and resets the quantity field
     * to its default.
     */
    public void refreshMerchandise() {
        merchTableView.setItems(FXCollections.observableArrayList(merchCatalog.getAllMerchandise()));
        browseMessageLabel.setText(""); // Clear previous messages
        quantityField.setText("1"); // Reset quantity field to default
    }

    /**
     * Handles the action of adding a selected merchandise item to the cart.
     * This method performs validation for item selection and quantity input.
     * If valid, it adds the specified quantity of the item to the {@link Cart}
     * and provides feedback to the user.
     */
    private void handleAddToCart() {
        Merchandise selectedMerchandise = merchTableView.getSelectionModel().getSelectedItem();
        if (selectedMerchandise == null) {
            browseMessageLabel.setText("Please select an item to add.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
            if (quantity <= 0) {
                browseMessageLabel.setText("Quantity must be greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            browseMessageLabel.setText("Invalid quantity. Please enter a number.");
            return;
        }

        // Add item to the cart and confirm
        currentCart.addItem(selectedMerchandise, quantity);
        browseMessageLabel.setText(quantity + "x " + selectedMerchandise.getName() + " added to cart.");
        quantityField.setText("1"); // Reset quantity field after successful addition
    }
}