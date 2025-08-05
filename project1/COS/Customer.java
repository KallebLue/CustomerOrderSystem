package project1.COS;

import java.io.Serializable;

/**
 * Represents a customer within the Customer Ordering System.
 * This class stores essential customer information such as login credentials,
 * personal details, credit card information, and security question/answer for
 * verification.
 * It implements Serializable to allow customer data to be saved to and loaded
 * from files.
 */
public class Customer implements Serializable {
    /**
     * Serial version UID for serialization compatibility.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the customer, used for logging in.
     */
    private String id;
    /**
     * The customer's password, used for authentication.
     */
    private String password;
    /**
     * The full name of the customer.
     */
    private String name;
    /**
     * The physical address of the customer.
     */
    private String address;
    /**
     * The customer's credit card number.
     */
    private String creditCard;
    /**
     * The security question chosen by the customer for account recovery or
     * verification.
     */
    private String securityQuestion;
    /**
     * The answer to the customer's security question.
     */
    private String securityAnswer;

    /**
     * Constructs a new Customer object with all required details.
     * This constructor is typically used when a new account is created.
     *
     * @param id               The unique customer ID.
     * @param password         The customer's password.
     * @param name             The customer's full name.
     * @param address          The customer's address.
     * @param creditCard       The customer's credit card number.
     * @param securityQuestion The chosen security question.
     * @param securityAnswer   The answer to the security question.
     */
    public Customer(String id, String password, String name, String address, String creditCard,
            String securityQuestion, String securityAnswer) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.address = address;
        this.creditCard = creditCard;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    /**
     * Constructs a new Customer object with only the customer ID.
     * This constructor might be used for lookup purposes where only the ID is
     * initially known.
     *
     * @param id The unique customer ID.
     */
    public Customer(String id) {
        this.id = id;
    }

    /*
     * Validator methods return boolean value when verifying user credentials or
     * security answers.
     */

    /**
     * Validates if the provided input password matches the customer's stored
     * password.
     *
     * @param inputPassword The password string entered by the user.
     * @return true if the input password matches the stored password, false
     *         otherwise.
     */
    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    /**
     * Validates if the provided input security answer matches the customer's stored
     * security answer.
     * The comparison is case-insensitive.
     *
     * @param inputAnswer The security answer string entered by the user.
     * @return true if the input answer matches the stored answer (ignoring case),
     *         false otherwise.
     */
    public boolean validateSecurityAnswer(String inputAnswer) {
        return this.securityAnswer.equalsIgnoreCase(inputAnswer);
    }

    /*
     * Getter methods to retrieve customer attributes.
     */

    /**
     * Returns the customer's security question.
     *
     * @return The security question as a String.
     */
    public String getSecurityQuestion() {
        return this.securityQuestion;
    }

    /**
     * Returns the customer's unique ID.
     *
     * @return The customer ID as a String.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns the customer's credit card number.
     *
     * @return The credit card number as a String.
     */
    public String getCreditCard() {
        return creditCard;
    }

    /*
     * Setter methods to modify customer attributes.
     */

    /**
     * Sets a new name for the customer.
     *
     * @param nameInput The new name to set.
     */
    public void setName(String nameInput) {
        this.name = nameInput;
    }

    /**
     * Sets a new address for the customer.
     *
     * @param addressInput The new address to set.
     */
    public void setAddress(String addressInput) {
        this.address = addressInput;
    }

    /**
     * Sets a new password for the customer.
     * In a real application, password changes would typically involve
     * re-authentication
     * and password complexity checks.
     *
     * @param passwordInput The new password to set.
     */
    public void setPassword(String passwordInput) {
        this.password = passwordInput;
    }

    /**
     * Sets a new security question for the customer.
     *
     * @param securityQuestionInput The new security question to set.
     */
    public void setSecurityQuestion(String securityQuestionInput) {
        this.securityQuestion = securityQuestionInput;
    }

    /**
     * Sets a new answer for the customer's security question.
     *
     * @param securityAnswerInput The new security answer to set.
     */
    public void setSecurityAnswer(String securityAnswerInput) {
        this.securityAnswer = securityAnswerInput;
    }

    /**
     * Sets a new credit card number for the customer.
     *
     * @param newCard The new credit card number to set.
     */
    public void setCreditCard(String newCard) {
        this.creditCard = newCard;
    }
}