
package com.ticket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TicketQueen {
	
	// Array Lists to store users and events
	private static ArrayList<User> allUsers = new ArrayList<>();
	private static ArrayList<Event> allEvents = new ArrayList<>();
	
	public static void main(String[] args) {
		
		// Generate initial Admin user.
		Admin admin = new Admin("admin@example.com", "ADM001", "adminPass");
		allUsers.add(admin);
		
		System.out.println("Welcome to TicketQueen.");
		
		// Show main menu. Shows enhanced Switch in use.
        boolean appOpen = true;
        while (appOpen) {
	        mainMenu();
	        int choice = getUserChoice();
	
	        switch (choice) {
	            case 1 -> userLogin();
	            case 2 -> registerUser();
	            case 3 -> displayEvents();
	            case 4 -> {System.out.println("Thank you for visiting TicketQueen, see you again.");
	            	appOpen = false;
	                System.exit(0);}
	            default -> System.out.println("Invalid choice. Please try again.");
        }
    }
}
	private static Scanner scanner = new Scanner(System.in);
		
	// Method to get user input 
	static String getInput() {
	    return scanner.nextLine();
	}

	static void mainMenu() {
		
		System.out.println("Enter '1' to login,");
		System.out.println("Enter '2' to register a new account,");
		System.out.println("Enter '3' to browse event,");
		System.out.println("Enter '4' to quit...");
	}
    static int getUserChoice() {
        try {
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            return choice;
        } catch (Exception e) {
            return -1; // Invalid input
        }

    }
    
    private static void userLogin() {
        System.out.print("Enter userId: ");
        String userId = getInput();
        System.out.print("Enter password: ");
        String password = getInput();

 
        User currentUser = null;
   
        if (userId.equals("admin") || userId.startsWith("ADM")) {
            currentUser = authenticateUser(userId, password);
        } else if (userId.startsWith("ATT")) {
            currentUser = authenticateUser(userId, password);
        } else if (userId.startsWith("ORG")) {

            currentUser = authenticateUser(userId, password);
        }


        // Check if the user was successfully authenticated
        if (currentUser != null) {
            System.out.println("Welcome " + currentUser.getUserName() + ", you are logged in as " + currentUser.displayUserRole());
            currentUser.postLoginMenu();
        } else {
            System.out.println("Invalid userId or password. Please try again.");
            userLogin();
        }
    }

    
 // Authenticate user by checking userId and password
    private static User authenticateUser(String userId, String password) {
        for (User user : allUsers) {
            if (user instanceof Admin && user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                return user;
            } else if (user instanceof Attendee && user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                return user;
            } else if (user instanceof Organiser && user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Return null if user not found
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = getInput();
        System.out.print("Enter email Address: ");
        String emailAddress = getInput();
        System.out.print("Enter password: ");
        String password = getInput();
        // Only Attendee users allowed to register
        Attendee newUser = new Attendee(username, emailAddress, password);
        String userID = newUser.getUserId();
        System.out.println("Your account has been created.");
        System.out.println("Your userId is " + userID);
        allUsers.add(newUser);
        
        userLogin();   
        
    }

    // Filter to see available tickets. Show Predicate Lambda in use
    public static void displayEventsWithAvailableTickets(TicketType ticketType) {
        List<Event> availableEvents = allEvents.stream()
                .filter(event -> event.getTicketAvailability(ticketType) > 0)
                .collect(Collectors.toList());

        if (availableEvents.isEmpty()) {
            System.out.println("No events available with tickets for type: " + ticketType);
        } else {
            System.out.println("\nEvents with available tickets for " + ticketType + ":");
            for (Event event : availableEvents) {
                System.out.println(event.getEventDetails() + " - " + ticketType + " Tickets Available: " + event.getTicketAvailability(ticketType));
            }
        }
    }
    
    static void displayEvents() {
        if (allEvents.isEmpty()) {
            System.out.println("No events available.");
        } else {
            System.out.println("Available Events:");
            for (Event event : allEvents) {
                System.out.println(event.getEventDetails() + " - Type: " + event.getEventType());
            }
        }
    }

    public static void addUser(User user) {
        allUsers.add(user);
    }
    
    public static void addEvent(Event event) {
        allEvents.add(event);
    }

	public static void removeEvent(Event eventToDelete) {
		allEvents.remove(eventToDelete);
		
	}
	// For user to see all events registered by Organisers. 
    public static ArrayList<Event> getAllEvents() {
        return allEvents;
    }
    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }



}
