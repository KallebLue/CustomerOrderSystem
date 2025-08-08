package project1.GUI.GUIPanals;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import project1.COS.Customer;
import project1.COS.CustomerStorageCreator;
import project1.GUI.CustomerOrderSystemGUI;

/**
 * Represents the graphical user interface panel for creating a new customer
 * account.
 * This view allows users to input their personal details, choose a unique ID
 * and password,
 * and set up a security question and answer. It interacts with the
 * {@link CustomerStorageCreator}
 * to save new customer data and with the main {@link CustomerOrderSystemGUI}
 * for navigation and alerts.
 */
public class CreateAccountView {

    /** A reference to the main GUI application for scene switching and alerts. */
    private CustomerOrderSystemGUI mainApp;
    /** Manages the persistence of customer data, used for adding new customers. */
    private CustomerStorageCreator customerStorageCreator;

    /** The JavaFX scene managed by this view. */
    private Scene scene;
    /** Text field for the new customer's chosen ID. */
    private TextField newIdField;
    /** Password field for the new customer's chosen password. */
    private PasswordField newPasswordField;
    /** Text field for the new customer's full name. */
    private TextField newNameField;
    /** Text field for the new customer's address. */
    private TextField newAddressField;
    /** Text field for the new customer's credit card number. */
    private TextField newCreditCardField;
    /** ComboBox for selecting a security question. */
    private ComboBox<String> securityQuestionComboBox;
    /** Text field for the new customer's security answer. */
    private TextField newSecurityAnswerField;
    /**
     * Label to display messages or feedback to the user during account creation.
     */
    private Label createAccountMessageLabel;

    /**
     * Constructs a new CreateAccountView.
     * Initializes the UI components and sets up necessary backend dependencies.
     *
     * @param mainApp                A reference to the main
     *                               {@link CustomerOrderSystemGUI} application.
     * @param customerStorageCreator The {@link CustomerStorageCreator} for customer
     *                               data operations.
     */
    public CreateAccountView(CustomerOrderSystemGUI mainApp, CustomerStorageCreator customerStorageCreator) {
        this.mainApp = mainApp;
        this.customerStorageCreator = customerStorageCreator;
        initializeUI();
    }

    /**
     * Initializes the graphical user interface components for the account creation
     * view.
     * This method sets up the layout, labels, input fields, and buttons,
     * and assigns their respective properties and event handlers.
     */
    private void initializeUI() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label title = new Label("--- Create New Account ---");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        GridPane.setConstraints(title, 0, 0, 2, 1);
        grid.getChildren().add(title);

        Label newIdLabel = new Label("Customer ID:");
        GridPane.setConstraints(newIdLabel, 0, 1);
        newIdField = new TextField();
        newIdField.setPromptText("Choose a unique ID");
        GridPane.setConstraints(newIdField, 1, 1);
        grid.getChildren().addAll(newIdLabel, newIdField);

        Label newPasswordLabel = new Label("Password:");
        GridPane.setConstraints(newPasswordLabel, 0, 2);
        newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Min 6 chars, digit, special (@#$%&*), uppercase");
        GridPane.setConstraints(newPasswordField, 1, 2);
        grid.getChildren().addAll(newPasswordLabel, newPasswordField);

        Label newNameLabel = new Label("Full Name:");
        GridPane.setConstraints(newNameLabel, 0, 3);
        newNameField = new TextField();
        GridPane.setConstraints(newNameField, 1, 3);
        grid.getChildren().addAll(newNameLabel, newNameField);

        Label newAddressLabel = new Label("Address:");
        GridPane.setConstraints(newAddressLabel, 0, 4);
        newAddressField = new TextField();
        GridPane.setConstraints(newAddressField, 1, 4);
        grid.getChildren().addAll(newAddressLabel, newAddressField);

        Label newCreditCardLabel = new Label("Credit Card #:");
        GridPane.setConstraints(newCreditCardLabel, 0, 5);
        newCreditCardField = new TextField();
        newCreditCardField.setPromptText("Numbers only");
        GridPane.setConstraints(newCreditCardField, 1, 5);
        grid.getChildren().addAll(newCreditCardLabel, newCreditCardField);

        Label securityQLabel = new Label("Security Question:");
        GridPane.setConstraints(securityQLabel, 0, 6);
        securityQuestionComboBox = new ComboBox<>();
        securityQuestionComboBox.getItems().addAll(
                "What is your mother's maiden name?",
                "What was your first pet's name?",
                "What is your favorite book?");
        securityQuestionComboBox.setValue("What is your mother's maiden name?"); // Default
        GridPane.setConstraints(securityQuestionComboBox, 1, 6);
        grid.getChildren().addAll(securityQLabel, securityQuestionComboBox);

        Label securityALabel = new Label("Answer:");
        GridPane.setConstraints(securityALabel, 0, 7);
        newSecurityAnswerField = new TextField();
        GridPane.setConstraints(newSecurityAnswerField, 1, 7);
        grid.getChildren().addAll(securityALabel, newSecurityAnswerField);

        Button createButton = new Button("Create Account");
        GridPane.setConstraints(createButton, 1, 8);
        createButton.setOnAction(e -> handleCreateAccount());
        grid.getChildren().add(createButton);

        Button backButton = new Button("Back to Main Menu");
        GridPane.setConstraints(backButton, 0, 8);
        backButton.setOnAction(e -> mainApp.showLoginScene());
        grid.getChildren().add(backButton);

        createAccountMessageLabel = new Label("");
        createAccountMessageLabel.setStyle("-fx-text-fill: red;");
        GridPane.setConstraints(createAccountMessageLabel, 0, 9, 2, 1);
        grid.getChildren().add(createAccountMessageLabel);

        scene = new Scene(grid, 500, 450);
    }

    /**
     * Returns the JavaFX {@link Scene} object associated with this
     * CreateAccountView.
     *
     * @return The scene of the create account view.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Resets all input fields and the message label in the create account view to
     * their default states.
     * This method is typically called when navigating to this view to ensure a
     * clean form.
     */
    public void resetFields() {
        newIdField.clear();
        newPasswordField.clear();
        newNameField.clear();
        newAddressField.clear();
        newCreditCardField.clear();
        securityQuestionComboBox.setValue("What is your mother's maiden name?");
        newSecurityAnswerField.clear();
        createAccountMessageLabel.setText("");
    }

    /**
     * Handles the account creation process when the "Create Account" button is
     * clicked.
     * This method performs input validation for all fields, checks for unique
     * customer ID,
     * validates password complexity and credit card format, creates a new
     * {@link Customer} object,
     * saves it, and provides feedback to the user.
     * On successful creation, it navigates back to the login scene.
     */
    private void handleCreateAccount() {
        String id = newIdField.getText();
        String password = newPasswordField.getText();
        String name = newNameField.getText();
        String address = newAddressField.getText();
        String creditCard = newCreditCardField.getText();
        String securityQuestion = securityQuestionComboBox.getValue();
        String securityAnswer = newSecurityAnswerField.getText();

        // Validate that all fields are filled
        if (id.isEmpty() || password.isEmpty() || name.isEmpty() || address.isEmpty() ||
                creditCard.isEmpty() || securityQuestion.isEmpty() || securityAnswer.isEmpty()) {
            createAccountMessageLabel.setText("All fields are required.");
            return;
        }

        // Validate unique customer ID
        if (!customerStorageCreator.isIDAvailable(id)) {
            createAccountMessageLabel.setText("Error: This ID already exists. Please choose a different ID.");
            return;
        }

        // Validate password complexity
        if (password.length() < 6 || !password.matches(".*\\d.*") || !password.matches(".*[@#$%&*].*")
                || !password.matches(".*[A-Z].*")) {
            createAccountMessageLabel.setText(
                    "Password must be min 6 chars, include a digit, a special character (@#$%&*), and an uppercase letter.");
            return;
        }

        // Validate credit card format (numbers only)
        if (!creditCard.matches("\\d+")) {
            createAccountMessageLabel.setText("Invalid credit card number. It must contain only numbers.");
            return;
        }

        // Create and save new customer if all validations pass
        Customer newCustomer = new Customer(id, password, name, address, creditCard, securityQuestion, securityAnswer);
        customerStorageCreator.addCustomer(newCustomer);
        createAccountMessageLabel.setText("Account created successfully!");
        mainApp.showAlert(Alert.AlertType.INFORMATION, "Account Created",
                "Your account has been successfully created!");
        mainApp.showLoginScene(); // Navigate back to the login screen
    }
}