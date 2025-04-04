
package com.ticket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TicketQueen {
	
	// Array Lists to store users and events
	private static ArrayList<User> allUsers = new ArrayList<>();
	private static ArrayList<Event> allEvents = new ArrayList<>();
	private static ResourceBundle messages;
	

	public static void main(String[] args) {
		
	    // Load users from file at startup
	    allUsers.addAll(LoggerUtil.loadUsers());

	    // Generate an initial Admin user if no users exist
	    if (allUsers.isEmpty()) {
	        Admin admin = new Admin("admin@example.com", "ADM001", "adminPass");
	        allUsers.add(admin);
	        LoggerUtil.saveUsers(allUsers);  // Save the newly created admin
	    }
       
	    // Language selection
        selectLanguage();
	    System.out.println("Welcome to TicketQueen.");

	    boolean appOpen = true;
	    while (appOpen) {
	        mainMenu();
	        int choice = getUserChoice();

	        switch (choice) {
	            case 1 -> userLogin();
	            case 2 -> registerUser();
	            case 3 -> displayEvents();
	            case 4 -> {
	                System.out.println("Thank you for visiting TicketQueen, see you again.");
	                appOpen = false;
	            }
	            default -> System.out.println("Invalid choice. Please try again.");
	        }
	    }


	    // Shut down the executor when exiting
	    shutdownExecutor();
	    if (bookingExecutor.isTerminated()) {
	        System.out.println("Booking service terminated successfully. Goodbye!");
	    } else {
	        System.out.println("Booking service is still shutting down. Goodbye!");
	    }

	    System.exit(0);

	    // Save users when exiting
	    LoggerUtil.saveUsers(allUsers);
	
	}
		
	// Method to get user input 
	static String getInput() {
	    return scanner.nextLine();
	}
    private static void selectLanguage() {
        System.out.println("Select language (1 for English, 2 for French): ");
        int langChoice = scanner.nextInt();
        Locale locale = switch (langChoice) {
            case 1 -> Locale.ENGLISH;
            case 2 -> Locale.FRENCH;
            default -> {
                System.out.println("Invalid choice. Defaulting to English.");
                yield Locale.ENGLISH;
            }
        };

        
        messages = ResourceBundle.getBundle("resources.messages", locale);
    }

	static void mainMenu() {
		
        System.out.println(messages.getString("menu.login"));
        System.out.println(messages.getString("menu.register"));
        System.out.println(messages.getString("menu.browse"));
        System.out.println(messages.getString("menu.quit"));
	}
	private static final Scanner scanner = new Scanner(System.in);

	static int getUserChoice() {
	    try {
	        int choice = scanner.nextInt();
	        scanner.nextLine();  
	        return choice;
	    } catch (Exception e) {
	        scanner.nextLine();  
	        return -1;  
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
            System.out.println("Statistics:");
            System.out.println("Total available events: " + TicketQueen.countAvailableEvents());
            TicketQueen.findEventWithMostTickets();

            currentUser.postLoginMenu();
        } else {
            System.out.println("Invalid userId or password. Please try again.");
            userLogin();
        }
    }

    
 // Authenticate user by checking userId and password
    private static User authenticateUser(String userId, String password) {
        for (User user : allUsers) {
            if (user instanceof Admin admin && admin.getUserId().equals(userId) && admin.getPassword().equals(password)) {
                return admin;
            } else if (user instanceof Attendee attendee && attendee.getUserId().equals(userId) && attendee.getPassword().equals(password)) {
                return attendee;
            } else if (user instanceof Organiser organiser && organiser.getUserId().equals(userId) && organiser.getPassword().equals(password)) {
                return organiser;
            }
        }
        return null; // User not found
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = getInput();
        System.out.print("Enter email Address: ");
        String emailAddress = getInput();
        System.out.print("Enter password: ");
        String password = getInput();

        Attendee newUser = new Attendee(username, emailAddress, password);
        String userID = newUser.getUserId();
        addUser(newUser);
        System.out.println("Your account has been created. Your userId is " + userID);


        userLogin();
    }

 // I use predicate to filter here also streams: forEach, collect, also unnamed variable
    public static void displayEventsWithAvailableTickets(TicketType ticketType) {
        Predicate<Event> hasAvailableTickets = event -> event.getTicketAvailability(ticketType) > 0;
        List<Event> availableEvents = allEvents.stream()
            .filter(hasAvailableTickets)  // Using Predicate here
            .toList();
        if (availableEvents.isEmpty()) {
            System.out.println("No events with " + ticketType + " tickets available.");
        } else {
            availableEvents.forEach(_ -> System.out.println("Event with " + ticketType + " tickets available."));  // Using unnamed variable
        }
    }


//Use to display tickets on startup, includes streams count(), filter()
    public static long countAvailableEvents() {
        return allEvents.stream()
                .filter(event -> event.getTicketAvailability(TicketType.GENERAL) > 0)
                .count();
    }


    public static void findEventWithMostTickets() {
        allEvents.stream()
//Find max available tickets using max() also Comparator.comparing()
            .max(Comparator.comparingInt(event -> event.getTicketAvailability(TicketType.GENERAL))) 
            .ifPresentOrElse(
                event -> System.out.println("Event with the most available tickets: " + event.getEventDetails()),
                () -> System.out.println("No events available")
            );
    }
    
// I use supplier here to show a default message
    static void displayEvents() {
        if (allEvents.isEmpty()) {
        	Supplier<String> noEventsMessage = () -> "No events are currently available.";
        	System.out.println(noEventsMessage.get());

        } else {
            System.out.println("Available Events:");
            for (Event event : allEvents) {
                System.out.println(event.getEventDetails() + " - Type: " + event.getEventType());
            }
        }
    }
    public static void groupEventsByAvailability() {
        Map<Boolean, List<Event>> eventGroups = allEvents.stream()
                .collect(Collectors.groupingBy(event -> event.getTicketAvailability(TicketType.GENERAL) > 0));  

        System.out.println("Available Events: " + eventGroups.get(true));
        System.out.println("Sold Out Events: " + eventGroups.get(false));
    }
//Generics used here as user type doesn't matter
    public static <T extends User> void addUser(T user) {
        allUsers.add(user);
        LoggerUtil.saveUsers(allUsers);  
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
    public static Event getEventById(String eventId) {
        return allEvents.stream()
            .filter(event -> event.getEventId().equals(eventId))
            .findFirst()
            .orElse(null);
    }
    
    private static final ExecutorService bookingExecutor = Executors.newFixedThreadPool(5);
    public static void shutdownExecutor() {
        try {
            bookingExecutor.shutdown();
            if (!bookingExecutor.isTerminated()) {
                System.out.println("Waiting for ongoing tasks to complete...");
            }
        } catch (Exception e) {
            System.err.println("Error while shutting down booking executor: " + e.getMessage());
        }
    }



}
