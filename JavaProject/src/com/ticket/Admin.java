package com.ticket;

public class Admin extends User implements EventManager{

	private String userId;
	// Start at one to ensure the userid generation works.
	private static int count =1;
	
	// Example of this. and this()
	public Admin(String emailAddress, String password) {
		this(emailAddress, "ADM" + String.format("%03d", count), password);
		count++;
	}
	
	public Admin(String emailAddress, String userId, String password) {
		super(emailAddress, password);
		this.userId = userId;
		count++;

	}
	
	public String getUserId() {
		return userId;
	}

	@Override
	public String displayUserRole() {
		return "Admin";

	}
	//Override postLoginMenu method to display secondary menu with Admin options.
	@Override
    public void postLoginMenu() {
        boolean adminActive = true;
        while (adminActive) {
            System.out.println("\nAdmin Menu:");
            System.out.println("Enter '1' to add new Organiser user,");
            System.out.println("Enter '2' to add new Admin account,");
            System.out.println("Enter '3' to view events,");
            System.out.println("Enter '4' to cancel an event,");
            System.out.println("Enter '5' to view bookings,");
            System.out.println("Enter '6' to view events with tickets available");
            System.out.println("Enter '7' to logout");

            int choice = TicketQueen.getUserChoice();
            switch (choice) {
                case 1 ->
                	addNewUser("Organiser");
                case 2 ->
                	addNewUser("Admin");
                case 3 ->
                    TicketQueen.displayEvents();
                case 4 ->
                    cancelEvent();
                case 5 ->
                    viewAllBookings();
                case 6 -> {
                    System.out.print("Enter ticket type (VIP/GENERAL/BALCONY): ");
                    String ticketTypeStr = TicketQueen.getInput().toUpperCase();
                    try {
                        TicketType ticketType = TicketType.valueOf(ticketTypeStr);
                        TicketQueen.displayEventsWithAvailableTickets(ticketType);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid ticket type. Please try again.");
                    }
                }

                case 7 -> {
                    adminActive = false;
                    System.out.println("Logging out...");}
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }


	private void addNewUser(String role) {
	    System.out.print("Enter username for the new " + role + " account: ");
	    String username = TicketQueen.getInput();

	    System.out.print("Enter email address for the new " + role + " account: ");
	    String emailAddress = TicketQueen.getInput();

	    System.out.print("Enter password for the new " + role + " account: ");
	    String password = TicketQueen.getInput();
	    
	    if(role.equals("Admin")){
	    	addNewUser(emailAddress, password);
	    }else if(role.equals("Organiser")) {
	    	addNewUser(username, emailAddress, password);
	    }
	}
	
	// These two methods are an example of method overloading.
	private void addNewUser(String emailAddress, String password) {
	        Admin newAdmin = new Admin(emailAddress, password);
	        TicketQueen.addUser(newAdmin);
	        System.out.println("Admin account successfully created with User ID: " + newAdmin.getUserId());
	}

	private void addNewUser(String username, String emailAddress, String password) {
	    Organiser newOrganiser = new Organiser(username, emailAddress, password);
	    TicketQueen.addUser(newOrganiser);
	    System.out.println("Organiser account successfully created with User ID: " + newOrganiser.getUserId());
	}
	
	//Method overriding/polymorphism example.
	@Override 
	public void cancelEvent() {
	    System.out.print("Enter Event ID to cancel: ");
	    String eventId = TicketQueen.getInput();

	    Event eventToDelete = null;
	    for (Event event : TicketQueen.getAllEvents()) {
	        if (event.getEventId().equals(eventId)) {
	            eventToDelete = event;
	            break;
	        }
	    }

	    if (eventToDelete != null) {
	        TicketQueen.removeEvent(eventToDelete);
	        System.out.println("Event deleted successfully.");
	    } else {
	        System.out.println("Event not found.");
	    }
	}
	//This method uses LVTI 
	   private void viewAllBookings() {
	        System.out.println("\nAll Bookings:");
	        boolean bookingsFound = false;
	        
//I use pattern matching here 
	        for (var user : TicketQueen.getAllUsers()) {
	            if (user instanceof Attendee attendee && !attendee.getAttendeeTickets().isEmpty()) { 
	                System.out.println("\nUser ID: " + attendee.getUserId() + " - " + attendee.getUserName());
	                attendee.getAttendeeTickets().forEach(ticket -> 
	                    System.out.println("  - Event ID: " + ticket.eventId() + " | Ticket Type: " + ticket.ticketType())
	                );
	                
	            } bookingsFound = true;
	        }

	        if (!bookingsFound) {
	            System.out.println("No bookings found.");
	        }
	    }

	@Override
	public void addEvent() {
		// TODO Auto-generated method stub
		
	}
}
