package project1.GUI.GUIPanals;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.application.Platform;
import project1.COS.Customer;
import project1.COS.CustomerStorageCreator;
import project1.GUI.CustomerOrderSystemGUI;

/**
 * Represents the graphical user interface panel for user login and initial
 * application access.
 * This view allows existing customers to log in, new users to create an
 * account,
 * or allows any user to browse merchandise before logging in. It interacts with
 * {@link CustomerStorageCreator} for authentication and with the main
 * {@link CustomerOrderSystemGUI}
 * for scene transitions and alerts.
 */
public class LoginView {

    /** A reference to the main GUI application for scene switching and alerts. */
    private CustomerOrderSystemGUI mainApp;
    /**
     * Manages the persistence and retrieval of customer data for authentication.
     */
    private CustomerStorageCreator customerStorageCreator;

    /** The JavaFX scene managed by this view. */
    private Scene scene;
    /** Text field for entering the customer's ID during login. */
    private TextField loginIdField;
    /** Password field for entering the customer's password during login. */
    private PasswordField loginPasswordField;
    /**
     * Text field for entering the security answer during the second step of login.
     */
    private TextField loginSecurityAnswerField;
    /**
     * Label to display messages or feedback to the user (e.g., errors, login
     * status).
     */
    private Label loginMessageLabel;
    /** Label to display the security question after ID and password validation. */
    private Label loginSecurityQuestionLabel;

    /**
     * Constructs a new LoginView.
     * Initializes the UI components and sets up necessary dependencies.
     *
     * @param mainApp                A reference to the main
     *                               {@link CustomerOrderSystemGUI} application.
     * @param customerStorageCreator The {@link CustomerStorageCreator} for customer
     *                               data operations.
     */
    public LoginView(CustomerOrderSystemGUI mainApp, CustomerStorageCreator customerStorageCreator) {
        this.mainApp = mainApp;
        this.customerStorageCreator = customerStorageCreator;
        initializeUI();
    }

    /**
     * Initializes the graphical user interface components for the login view.
     * This method sets up the layout (GridPane), labels, input fields, and buttons,
     * and assigns their respective properties and event handlers.
     */
    private void initializeUI() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label welcomeTitle = new Label("--- Welcome to the Customer Ordering System ---");
        welcomeTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        GridPane.setConstraints(welcomeTitle, 0, 0, 2, 1);
        grid.getChildren().add(welcomeTitle);

        Label loginSectionTitle = new Label("Existing User Login:");
        loginSectionTitle.setStyle("-fx-font-weight: bold;");
        GridPane.setConstraints(loginSectionTitle, 0, 1, 2, 1);
        grid.getChildren().add(loginSectionTitle);

        Label idLabel = new Label("Customer ID:");
        GridPane.setConstraints(idLabel, 0, 2);
        loginIdField = new TextField();
        loginIdField.setPromptText("Enter Customer ID");
        GridPane.setConstraints(loginIdField, 1, 2);
        grid.getChildren().addAll(idLabel, loginIdField);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 3);
        loginPasswordField = new PasswordField();
        loginPasswordField.setPromptText("Enter Password");
        GridPane.setConstraints(loginPasswordField, 1, 3);
        grid.getChildren().addAll(passwordLabel, loginPasswordField);

        loginSecurityQuestionLabel = new Label("Security Question:");
        GridPane.setConstraints(loginSecurityQuestionLabel, 0, 4);
        loginSecurityAnswerField = new TextField();
        loginSecurityAnswerField.setPromptText("Enter Security Answer");
        GridPane.setConstraints(loginSecurityAnswerField, 1, 4);
        grid.getChildren().addAll(loginSecurityQuestionLabel, loginSecurityAnswerField);

        loginSecurityQuestionLabel.setVisible(false);
        loginSecurityAnswerField.setVisible(false);

        Button loginButton = new Button("Log In");
        GridPane.setConstraints(loginButton, 1, 5);
        loginButton.setOnAction(e -> handleLogin());
        grid.getChildren().add(loginButton);

        loginMessageLabel = new Label("");
        loginMessageLabel.setStyle("-fx-text-fill: red;");
        GridPane.setConstraints(loginMessageLabel, 0, 6, 2, 1);
        grid.getChildren().add(loginMessageLabel);

        Label otherOptionsTitle = new Label("If you're new!!:"); // Corrected typo
        otherOptionsTitle.setStyle("-fx-font-weight: bold;");
        GridPane.setConstraints(otherOptionsTitle, 0, 7, 2, 1);
        grid.getChildren().add(otherOptionsTitle);

        Button createAccountButton = new Button("Create Account");
        GridPane.setConstraints(createAccountButton, 0, 8, 2, 1);
        createAccountButton.setMaxWidth(Double.MAX_VALUE);
        createAccountButton.setOnAction(e -> mainApp.showCreateAccountScene());
        grid.getChildren().add(createAccountButton);

        Button browseMerchandiseButton = new Button("Browse Merchandise");
        GridPane.setConstraints(browseMerchandiseButton, 0, 9, 2, 1);
        browseMerchandiseButton.setMaxWidth(Double.MAX_VALUE);
        browseMerchandiseButton.setOnAction(e -> mainApp.showMerchandiseViewScene());
        grid.getChildren().add(browseMerchandiseButton);

        Button exitButton = new Button("Exit");
        GridPane.setConstraints(exitButton, 0, 10, 2, 1);
        exitButton.setMaxWidth(Double.MAX_VALUE);
        exitButton.setOnAction(e -> {
            mainApp.showAlert(Alert.AlertType.INFORMATION, "Goodbye!", "Exiting application. Goodbye!");
            Platform.exit(); // Correctly closes the JavaFX application
        });
        grid.getChildren().add(exitButton);

        scene = new Scene(grid, 550, 650);
    }

    /**
     * Returns the JavaFX {@link Scene} object associated with this LoginView.
     *
     * @return The scene of the login view.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Resets all input fields and the message label in the login view to their
     * default states.
     * This method also hides the security question fields and clears the logged-in
     * customer state
     * in the main application. It is typically called when navigating back to this
     * view.
     */
    public void resetFields() {
        loginIdField.clear();
        loginPasswordField.clear();
        loginSecurityAnswerField.clear();
        loginSecurityQuestionLabel.setVisible(false);
        loginSecurityAnswerField.setVisible(false);
        loginMessageLabel.setText("");
        mainApp.setLoggedInCustomer(null); // Ensure no customer is logged in
    }

    /**
     * Handles the login process when the "Log In" button is clicked.
     * This method performs a two-step authentication:
     * 1. Validates the entered customer ID and password against stored customer
     * data.
     * 2. If valid, it reveals the security question and prompts for the answer.
     * Upon successful security answer, the user is logged in and navigates to the
     * session manager.
     * Provides appropriate feedback messages for each step.
     */
    private void handleLogin() {
        String id = loginIdField.getText();
        String password = loginPasswordField.getText();
        String securityAnswer = loginSecurityAnswerField.getText();

        // First step of login: validate ID and password
        if (!loginSecurityAnswerField.isVisible()) {
            if (id.isEmpty() || password.isEmpty()) {
                loginMessageLabel.setText("Please enter ID and password.");
                return;
            }

            Customer foundCustomer = null;
            for (Customer c : customerStorageCreator.getAllCustomers()) {
                if (c.getId().equals(id)) {
                    foundCustomer = c;
                    break;
                }
            }

            if (foundCustomer == null) {
                loginMessageLabel.setText("Error: No account found with that ID.");
                return;
            }

            if (!foundCustomer.validatePassword(password)) {
                loginMessageLabel.setText("Error: Incorrect password.");
                return;
            }

            // If ID and password are correct, proceed to security question
            mainApp.setLoggedInCustomer(foundCustomer);
            loginSecurityQuestionLabel
                    .setText("Security Question: " + mainApp.getLoggedInCustomer().getSecurityQuestion());
            loginSecurityQuestionLabel.setVisible(true);
            loginSecurityAnswerField.setVisible(true);
            loginMessageLabel.setText("ID and password are valid. Please answer your security question.");
            return;
        }

        // Second step of login: validate security answer
        if (securityAnswer.isEmpty()) {
            loginMessageLabel.setText("Please enter your security answer.");
            return;
        }

        if (mainApp.getLoggedInCustomer().validateSecurityAnswer(securityAnswer)) {
            // Security answer is correct, login successful
            loginMessageLabel.setText("Login successful! Welcome, " + mainApp.getLoggedInCustomer().getId() + "!");
            mainApp.showAlert(Alert.AlertType.INFORMATION, "Login Success",
                    "Welcome, " + mainApp.getLoggedInCustomer().getId() + "!");
            mainApp.showSessionManagerScene(); // Navigate to the logged-in main menu
        } else {
            // Incorrect security answer
            loginMessageLabel.setText("Error: Incorrect security answer.");
            mainApp.setLoggedInCustomer(null); // Clear logged-in customer on failed security answer
            loginSecurityQuestionLabel.setVisible(false);
            loginSecurityAnswerField.setVisible(false);
            loginSecurityAnswerField.clear();
        }
    }
}