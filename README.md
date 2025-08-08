<h1> CustomerOrderSystem </h1>
The Customer Order System (COS) provides the customer with the following services described as use cases: Log On, Log Off, Create Account, Select Items, Make Order, and View Order. The COS is made possible by one main file and 16 files apart of the project1.COS package. 

The COS is separated into two sessions a logged out session where any user can use the system and a logged in session where only users logged into a known account can use. This is made possible by the PreSessionManager.java (PSM) and PostSessionManager.java (PoSM) files respectively. 

<h2>Logged out options and usability:</h2> 

Once the main file CustomerOrderSystem.java is ran the PSM is called and the user will be presented with a welcome screen. The welcome screen prompts the user giving the user the four options below. The user Is given a input box in the terminal to input the digit option to choose where to go. 

<h3>Option 1: Create account:</h3>

  In the case the user inputs 1, the user starts the account creation process. The user is then prompted for a unique ID, a password, customer Name, address,         credit card number, and a security question/answer.
  Once all the information is successfully entered a new customer object is created and saved in the customer.dat file for storage. 
  The User is then brought back to the welcome screen.

  

<h3>Option 2: Log In:</h3>

  In the case the user inputs 2, they will be prompted to enter their unique ID and password. The system then checks the provided credentials against the stored      customer data. A security verification step follows, where the user must correctly answer their security question. Upon a successful login, the PoSM takes over,    and the user is welcomed to their logged-in session. If the login fails (incorrect ID, password, or security answer), the user is returned to the main menu.

<h3>Option 3: Browse Merchandise:</h3>

  In the case the user inputs 3, the MerchCatalog is displayed, showing a list of all available products with their IDs and prices. The user can enter a product ID   and a quantity to add items to a temporary Cart. The cart is stored in memory for the duration of the pre-login session. If the user decides to log in later, the   items in this cart will be carried over to their authenticated session.

<h3> Option 4: Exit </h3>

  In the case the user inputs the digit 4, the user is told "Exiting application. Goodbye!" and the program is ended. 

<h3> Option 5: Invalid Choices </h3>

  In the case of Invalid characters inputted, the user is given the error: "Invalid choice. Please enter 1, 2, 3, or 4." and the welcome screen is called again. 

<h2>logged in options and usability:</h2>

After a successful login, the PoSM takes control, and the user is presented with a new menu that offers a wider range of options, tailored for an authenticated user.

<h3>Option 1: Browse Merchandise & Add to Cart: </h3>

  In the case the user inputs 1, this option displays the MerchCatalog, similar to the logged-out session. Any items added to the cart will now be associated with    the logged-in customer's session.

<h3>Option 2: View Cart:</h3>

  In the case the user inputs 2, this option displays the current contents of the user's shopping Cart. It provides a formatted summary, including each item, its     quantity, individual price, and a final total with tax calculated.

<h3>Option 3: Proceed to Checkout:</h3>

  In the case the user inputs 3, this option initiates the checkout process, handled by the OrderCreator class. The system prompts for the user's credit card         information. The payment is simulated via the BankSimulator.java file. If the payment is approved, a new OrderCreator object is created with a unique ID and        saved to orders.dat. An order confirmation is displayed, and the customer's cart is cleared.

<h3>Option 4: View Past Orders:</h3>

  In the case the user inputs 4, this option calls the OrderViewer class, which retrieves all orders associated with the logged-in user's ID from orders.dat. The     orders are displayed in chronological order (most recent first), providing a history of the customer's purchases.

<h3>Option 5: Log Out:</h3>



  In the case the user inputs 5, the LogOff class is called, and the user is informed that they have been successfully logged out. The application then returns to    the initial logged-out welcome screen.

<h1>CustomerOrderSystemGUI</h1>

The CustomerOrderSystemGUI offers the same services as the console version but uses a graphical interface for interaction, replacing text-based menus with clickable buttons and input fields. This is made possible by one main CustomerOrderSystemGUI file and 7 background javafx files that represent different scenese.

When you run the GUI, it opens to the Welcome Screen (LoginView), acting as the pre-login session. You can Log In with existing credentials, Create Account, Browse Merchandise to add items to a temporary cart, or Exit the application after a "Goodbye!" message.

<h2>Logged Out Options and Usability (from Welcome Screen)</h2>

<h3>Option 1: Create Account </h3>
Click "Create Account" on the Welcome Screen to go to the Create Account View. Fill in required details (ID, password, name, address, credit card, security question/answer). Input validation occurs, and on successful creation, an "Account Created" alert appears. You are then taken back to the Welcome Screen.

<h3>Option 2: Log In </h3>
Enter your Customer ID and Password on the Welcome Screen, then click "Log In". If credentials are correct, a Security Question appears; answer it. On successful login, an "Login Success" alert confirms, and you move to the Logged-In Menu (SessionManagerView). If login fails, an error message appears, and you remain on the Welcome Screen.

<h3>Option 3: Browse Merchandise </h3>
Click "Browse Merchandise" on the Welcome Screen to open the Merchandise View, which displays a product table. Select items and use the quantity field and "Add to Cart" button to add them to your temporary cart. Click "Back to Main Menu" to return to the Welcome Screen. Items in this cart carry over if you log in later.

<h3>Option 4: Exit </h3>
Click "Exit" on the Welcome Screen. A "Goodbye!" alert displays, and the application closes completely.

<h2>Logged In Options and Usability (from Logged-In Menu)</h2>

After a successful login, you'll see the Logged-In Menu (SessionManagerView), tailored for authenticated users.

<h3>Option 1: Browse Merchandise & Add to Cart </h3>

Click " Browse Merchandise & Add to Cart" to open the Merchandise View, similar to the pre-login browse. Items added are now associated with your logged-in account. Click "Back to Main Menu" to return to the Logged-In Menu.

<h3>Option 2: View Cart </h3>

Click " View Cart" to open the Cart View, showing your shopping cart's contents, including quantities, prices, subtotal, taxes, and total. Options to "Clear Cart" or "Proceed to Checkout" are available. Click "Back to Main Menu" to return to the Logged-In Menu.

<h3> Option 3: Proceed to Checkout </h3>

Click " Proceed to Checkout" from the Logged-In Menu or Cart View to open the Checkout View. This view summarizes your cart, lets you choose a Delivery Method (Mail Delivery with fee or In-store Pickup free), and shows the final total. Your credit card field is pre-filled based on customer saved information. If your cart's empty, a warning alerts you. If you're not logged in after pre-login browsing, you'll be prompted to log in/create an account before processing. Click "Process Order" to simulate payment. If approved, an "Order Placed" alert confirms, your cart clears, and the order saves. If denied, an error message appears. After completing or canceling, you're returned to the Logged-In Menu.

<h3> Option 4: View Past Orders </h3>
Click " View Past Orders" to open the Order Viewer View, displaying a list of your past orders, sorted by date. Select an order to see its details. Click "Back to Main Menu" to return to the Logged-In Menu.

<h3> Option 5: Log Out </h3>
Click " Log Out". A "Logged Out" alert confirms that you have been logged out. Your session data and cart clear, and you're returned to the initial Welcome Screen.

<h1>ChangeLog</h1>

<h2>08/08/2025</h2>

<h3>GUI additions</h3>

CustomerOrderSystemGUI.java, CartView.java, CheckoutView.java, CreateAccountView.java, LoginView.java, MerchandiseView,java, OrderViewerView, and SessionManagerView are created and added to create a GUI interface as a alternative to using the console as the user interface.

<h3>OrderCreator.java</h3>

Variable MAIL_DELIVERY_FEE is changed from private to public in order to be used by CheckoutView.java.

<h3>BankSimulator.java</h3>

The BankSimulator.java no longer uses a simplistic random approval/denial based on null returns. It now provides specific reasons for transaction denials, improving realism and user feedback.

<h3>Ordercreator.java</h3>

Adapted to ChargeResult: The processOrder method now calls BankSimulator.chargeCard() and receives a ChargeResult object. It checks result.isApproved() to determine transaction success and retrieves the authorizationNumber via result.getAuthorizationNumber() if approved. If a transaction is denied, the console output now includes the specific denialReason from the ChargeResult (e.g., "Bank charge denied: Insufficient funds or general bank error.").

<h3>MerchSelect.java</h3>

Improved Quantity Input Handling:The selectMerchandise method has been enhanced to more gracefully handle invalid input when a user enters the quantity of merchandise.

Previously, entering non-numeric values or quantities less than or equal to zero would result in an error message and immediately prompt for a new merchandise ID.

Now, if invalid input (non-numeric, zero, or negative) is detected for the quantity, the system will display a specific error message and re-prompt the user for the quantity for the same item until valid input is provided. This ensures a smoother and more focused user experience during merchandise selection.

<h3>Cart.java</h3>

Removed Duplicate Console Output for Item Additions: The addItem method in Cart.java no longer prints a confirmation message to the console when an item is added. This led to a duplicate confirmation message being sent when adding items to the cart.

<h3>CustomerOrderSystemUML.drawio</h3>

Completly reworked to showcase the above changes and inclusion of the GUI files. 

