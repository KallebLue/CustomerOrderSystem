# CustomerOrderSystem
The Customer Order System (COS) provides the customer with the following services described as use cases: Log On, Log Off, Create Account, Select Items, Make Order, and View Order. The COS is made possible by one main file and 16 files apart of the project1.COS package. 

The COS is separated into two sessions a logged out session where any user can use the system and a logged in session where only users logged into a known account can use. This is made possible by the PreSessionManager.java (PSM) and PostSessionManager.java (PoSM) files respectively. 

# Logged out options and usability:

Once the main file CustomerOrderSystem.java is ran the PSM is called and the user will be presented with a welcome screen. The welcome screen prompts the user giving the user the four options below. The user Is given a input box in the terminal to input the digit option to choose where to go. 

Option 1: Create account:

  In the case the user inputs 1, the user starts the account creation process. The user is then prompted for a unique ID, a password, customer Name, address, credit card number, and a security question/answer.
  Once all the information is successfully entered a new customer object is created and saved in the customer.dat file for storage. 
  The User is then brought back to the welcome screen.

  

Option 2: Log In:
In the case the user inputs 2. 

In the case the user inputs 2, they will be prompted to enter their unique ID and password. The system then checks the provided credentials against the stored customer data. A security verification step follows, where the user must correctly answer their security question. Upon a successful login, the PoSM takes over, and the user is welcomed to their logged-in session. If the login fails (incorrect ID, password, or security answer), the user is returned to the main menu.

Option 3: Browse Merchandise:

  In the case the user inputs 3, the MerchCatalog is displayed, showing a list of all available products with their IDs and prices. The user can enter a product ID and a quantity to add items to a temporary Cart. The cart is stored in memory for the duration of the pre-login session. If the user decides to log in later, the items in this cart will be carried over to their authenticated session.

Option 4: Exit

  In the case the user inputs the digit 4, the user is told "Exiting application. Goodbye!" and the program is ended. 

Option 5: Invalid Choices

  In the case of Invalid characters inputted, the user is given the error: "Invalid choice. Please enter 1, 2, 3, or 4." and the welcome screen is called again. 

# logged in options and usability:

After a successful login, the PoSM takes control, and the user is presented with a new menu that offers a wider range of options, tailored for an authenticated user.

Option 1: Browse Merchandise & Add to Cart:

In the case the user inputs 1, this option displays the MerchCatalog, similar to the logged-out session. Any items added to the cart will now be associated with the logged-in customer's session.

Option 2: View Cart:

In the case the user inputs 2, this option displays the current contents of the user's shopping Cart. It provides a formatted summary, including each item, its quantity, individual price, and a final total with tax calculated.

Option 3: Proceed to Checkout:

In the case the user inputs 3, this option initiates the checkout process, handled by the OrderCreator class. The system prompts for the user's credit card information. The payment is simulated via the BankSimulator.java file. If the payment is approved, a new OrderCreator object is created with a unique ID and saved to orders.dat. An order confirmation is displayed, and the customer's cart is cleared.

Option 4: View Past Orders:

In the case the user inputs 4, this option calls the OrderViewer class, which retrieves all orders associated with the logged-in user's ID from orders.dat. The orders are displayed in chronological order (most recent first), providing a history of the customer's purchases.

Option 5: Log Out:

In the case the user inputs 5, the LogOff class is called, and the user is informed that they have been successfully logged out. The application then returns to the initial logged-out welcome screen.
