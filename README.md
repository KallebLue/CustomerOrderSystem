# CustomerOrderSystem
The Customer Order System (COS) provides the customer with the following services described as use cases: Log On, Log Off, Create Account, Select Items, Make Order, and View Order. The COS is made possible by one main file and 16 files apart of the project1.COS package. 

The COS is separated into two sessions a logged out session where any user can use the system and a logged in session where only users logged into a known account can use. This is made possible by the PreSessionManager.java (PSM) and PostSessionManager.java (PoSM) files respectively. 

# Logged out options and usability:

Once the main file CustomerOrderSystem.java is ran the PSM is called and the user will be presented with a welcome screen. The welcome screen prompts the user giving the user the four options below. The user Is given a input box in the terminal to input the digit option to choose where to go. 

Option 1: Create account:

  

Option 2: Log In:

Option 3: Browse Merchandise:

  

Option 4: Exit

  In the case the user inputs the digit 4 user is told "Exiting application. Goodbye!" and the program is ended. 

Option 5: Invalid Choices

  In the case of Invalid characters inputted the user is given the error: "Invalid choice. Please enter 1, 2, 3, or 4." and the welcome screen is called again. 

# logged in options and usability:
