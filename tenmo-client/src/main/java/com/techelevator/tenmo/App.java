package com.techelevator.tenmo;

import java.math.BigDecimal;
import java.util.Scanner;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AccountServiceException;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.tenmo.services.TransferServiceException;
import com.techelevator.tenmo.services.UserService;
import com.techelevator.tenmo.services.UserServiceException;
import com.techelevator.view.ConsoleService;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks (Feature Coming Soon!)";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests (Feature Coming Soon!)";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String VIEW_CURRENT_BALANCE = "Your current account balance is: $";
	private static final String VIEW_TRANSFER_DETAILS = "Please enter transfer ID to view details (0 to cancel)";
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    
    AccountService accountService = new AccountService(API_BASE_URL);
    TransferService transferService = new TransferService(API_BASE_URL);
    UserService userService = new UserService(API_BASE_URL);
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				System.out.println("Feature coming soon!");
				//viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				System.out.println("Feature coming soon!");
				//requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		try {
			System.out.println(VIEW_CURRENT_BALANCE + 
					accountService.viewCurrentBalance(currentUser.getUser().getId().longValue()).getAccountBalance());
		} catch (AccountServiceException e) {
			e.printStackTrace();
		}
	}

	private void viewTransferHistory() {
		try {
			boolean hasHistory = transferService.viewTransferHistory(currentUser.getUser().getId().longValue());
			
			if (hasHistory) {
				int choice = console.getUserInputInteger(VIEW_TRANSFER_DETAILS);
				Long transferId = (long)choice;
				
				if (choice != 0) {
					Transfer transfer = transferService.viewTransferDetails(currentUser.getUser().getId().longValue(), transferId);
					
					if (transfer == null) {
						System.out.println("\nInvalid selection. Please enter a valid transfer ID from your history.\n");
						this.viewTransferHistory();
					
					} else {
						System.out.println(transfer);
					}
				}
			} else {
				System.out.println("It looks like you don't have any transfers in your account history.");
				mainMenu();
			}
			
		} catch (TransferServiceException e) {
			e.printStackTrace();
		}
		
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		// Optional
		
	}

	private void sendBucks() {
		
		User[] users = null;
		try {
			users = userService.findAll();
		} catch (UserServiceException e) {
			e.printStackTrace();
		}
		console.printUsers(users);
		
		Long fromUser = currentUser.getUser().getId().longValue();
		Long toUser;
		BigDecimal amount;
		
		System.out.print("\nEnter ID of user you are sending to: ");
		toUser = scanner.nextLong();
		
		System.out.print("\nEnter amount to send (without the dollar sign): ");
		amount = scanner.nextBigDecimal();
		
		try {
			BigDecimal currentUserBalance = accountService.viewCurrentBalance(fromUser).getAccountBalance();
			BigDecimal newFromUserBalance = currentUserBalance.subtract(amount);
			BigDecimal newToUserBalance = accountService.viewCurrentBalance(toUser).getAccountBalance().add(amount);
			
			if(currentUserBalance.compareTo(amount) >= 0 && fromUser != toUser) {
				transferService.sendBucks(fromUser, toUser, amount);
				accountService.updateBalance(fromUser, newFromUserBalance);
				accountService.updateBalance(toUser, newToUserBalance);
			} else {
				System.out.println("Send unsuccessful. You cannot send money to yourself, and you must have sufficient balance to send money.");
			}

		} catch (TransferServiceException | AccountServiceException e) {
			e.printStackTrace();
		}
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		//Optional
		
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}

}
