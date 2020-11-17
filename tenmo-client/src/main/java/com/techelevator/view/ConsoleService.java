package com.techelevator.view;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;

public class ConsoleService {

	private PrintWriter out;
	private Scanner in;
	private Scanner scanner = new Scanner(System.in);

	public ConsoleService(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output, true);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		out.println();
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

	public String getUserInput(String prompt) {
		out.print(prompt+": ");
		out.flush();
		return in.nextLine();
	}

	public Integer getUserInputInteger(String prompt) {
		Integer result = null;
		do {
			out.print(prompt+": ");
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Integer.parseInt(userInput);
			} catch(NumberFormatException e) {
				out.println("\n*** " + userInput + " is not valid ***\n");
			}
		} while(result == null);
		return result;
	}
	
	public void printUsers(User[] users) {
		
		System.out.println("----------------------------------------------");
		String heading1 = "User ID";
		String heading2 = "Username";
		System.out.printf( "%-15s %15s %n", heading1, heading2);
		System.out.println("----------------------------------------------");
		for (User user : users) {
			System.out.printf("%-15s %15s %n",user.getId(), user.getUsername());
		}
		/*
		System.out.println("--------------------------------");
		System.out.println("User/tUsername");
		for (User user : users) {
			System.out.println(user.getId() + " : " + user.getUsername());
		}
		*/
	}
	/*
	public String promptForTransferData(Long currentUserId) {
//		String transferString;
		
		Long toUser;
		
		System.out.println("----------------------------------------");
		System.out.println("Enter user ID: ");
		toUser = scanner.nextLong();
		
		
//		System.out.println("Enter user ID and amount to send in comma separated list:");
//		if (transfer != null) {
//			System.out.println(transfer.toString());
//		} else {
//			System.out.println("Example: 1, 50");
//		}
		System.out.println("----------------------------------------");
		transferString = scanner.nextLine();
		transferString = currentUserId + ", " + transferString;
//		if (transfer != null) {
//			transferString = currentUserId + transferString;
//		}
		return transferString;
	}
	*/
}
