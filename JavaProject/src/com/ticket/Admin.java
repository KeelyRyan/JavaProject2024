package com.ticket;

public class Admin extends User implements EventManager{

	private String userId;
	private static int count;
		
	public Admin(String emailAddress, String username, String password) {
		super(emailAddress, username, password);
		count++;
		this.emailAddress = emailAddress;
		this.username =username;
		this.password = password;
		this.userId = "ADM" + String.format("%03d", count);
	}
	
	public Admin(String emailAddress, String username, String userID, String password) {
		super(emailAddress, username, password);
		count++;
		this.emailAddress = emailAddress;
		this.username =username;
		this.password = password;
		this.userId = userID;
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
            System.out.println("Enter '4' to view bookings,");
            System.out.println("Enter '5' to logout");

            int choice = TicketMistress.getUserChoice();
            switch (choice) {
                case 1:
                	addNewOrganiser();
                    break;
                case 2:
                	addNewAdmin();
                    break;
                case 3:
                    TicketMistress.displayEvents();
                    break;
                case 4:
                    System.out.println("View bookings (to be implemented)");
                    break;
                case 5:
                    adminActive = false;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
	private void addNewAdmin() {

	    System.out.print("Enter email address for the new Admin account: ");
	    String emailAddress = TicketMistress.getInput();

	    System.out.print("Enter username for the new Admin account: ");
	    String username = TicketMistress.getInput();

	    System.out.print("Enter password for the new Admin account: ");
	    String password = TicketMistress.getInput();

	    Admin newAdmin = new Admin(emailAddress, username, password);
	    TicketMistress.addUser(newAdmin); 

	    System.out.println("Admin account successfully created with User ID: " + newAdmin.getUserId());
	}
	private void addNewOrganiser() {

	    System.out.print("Enter email address for the new Organiser account: ");
	    String emailAddress = TicketMistress.getInput();

	    System.out.print("Enter username for the new Organiser account: ");
	    String username = TicketMistress.getInput();

	    System.out.print("Enter password for the new Organiser account: ");
	    String password = TicketMistress.getInput();

	    Organiser newOrganiser = new Organiser(emailAddress, username, password);
	    TicketMistress.addUser(newOrganiser); 
	    
	    
	    System.out.println("Organiser account successfully created with User ID: " + newOrganiser.getUserId());
	}
	@Override 
	public void cancelEvent() {
	    System.out.print("Enter Event ID to cancel: ");
	    String eventId = TicketMistress.getInput();

	    Event eventToDelete = null;
	    for (Event event : TicketMistress.getAllEvents()) {
	        if (event.eventId.equals(eventId)) {
	            eventToDelete = event;
	            break;
	        }
	    }

	    if (eventToDelete != null) {
	        TicketMistress.removeEvent(eventToDelete);
	        System.out.println("Event deleted successfully.");
	    } else {
	        System.out.println("Event not found.");
	    }
	}

	@Override
	public void addEvent() {
		// TODO Auto-generated method stub
		
	}
}
