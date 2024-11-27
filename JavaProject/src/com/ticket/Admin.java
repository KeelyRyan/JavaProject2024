package com.ticket;

public class Admin extends User implements EventManager{

	private String userId;
	private static int count;
		
	public Admin(String username,String emailAddress, String password) {
		this(username, emailAddress, "ADM" + String.format("%03d", count), password);
		count++;
	}
	
	public Admin(String username, String emailAddress, String userId, String password) {
		super(username, emailAddress, password);
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
            System.out.println("Enter '6' to logout");

            int choice = TicketQueen.getUserChoice();
            switch (choice) {
                case 1 ->
                	addNewOrganiser();
                case 2 ->
                	addNewAdmin();
                case 3 ->
                    TicketQueen.displayEvents();
                case 4 ->
                    cancelEvent();
                case 5 ->
                    viewAllBookings();
                case 6 -> {
                    adminActive = false;
                    System.out.println("Logging out...");}
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

	private void addNewAdmin() {
	    System.out.print("Enter username for the new Admin account: ");
	    String username = TicketQueen.getInput();

	    System.out.print("Enter email address for the new Admin account: ");
	    String emailAddress = TicketQueen.getInput();


	    System.out.print("Enter password for the new Admin account: ");
	    String password = TicketQueen.getInput();

	    Admin newAdmin = new Admin(username, emailAddress, password);
	    TicketQueen.addUser(newAdmin); 

	    System.out.println("Admin account successfully created with User ID: " + newAdmin.getUserId());
	}
	private void addNewOrganiser() {
	    System.out.print("Enter username for the new Organiser account: ");
	    String username = TicketQueen.getInput();

	    System.out.print("Enter email address for the new Organiser account: ");
	    String emailAddress = TicketQueen.getInput();

	    System.out.print("Enter password for the new Organiser account: ");
	    String password = TicketQueen.getInput();

	    Organiser newOrganiser = new Organiser(username, emailAddress, password);
	    TicketQueen.addUser(newOrganiser); 
	    
	    
	    System.out.println("Organiser account successfully created with User ID: " + newOrganiser.getUserId());
	}
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
	// This method has an example of LVTI for user and attendee.
	   private void viewAllBookings() {
	        System.out.println("\nAll Bookings:");
	        boolean bookingsFound = false;

	        for (var user : TicketQueen.getAllUsers()) {
	            if (user instanceof Attendee) {
	                var attendee = (Attendee) user;
	                if (!attendee.getAttendeeTickets().isEmpty()) {
	                    System.out.println("\nUser ID: " + attendee.getUserId() + " - " + attendee.getUserName());
	                    for (String ticket : attendee.getAttendeeTickets()) {
	                        System.out.println("  - " + ticket);
	                    }
	                    bookingsFound = true;
	                }
	            }
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
